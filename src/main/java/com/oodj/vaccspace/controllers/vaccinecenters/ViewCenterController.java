package com.oodj.vaccspace.controllers.vaccinecenters;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.people.PeopleController;
import com.oodj.vaccspace.controllers.vaccinetypes.VaccineTypesController;
import com.oodj.vaccspace.models.CenterStatus;
import com.oodj.vaccspace.models.VaccinationCenter;
import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCenterController extends BaseController {

    VaccineCentersController vaccineCentersController = null;
    VaccinationCenter vaccinationCenter = new VaccinationCenter();

    @FXML
    private MFXPillButton btnClose;

    @FXML
    private MFXPillButton btnEditCenter;

    @FXML
    private MFXPillButton btnSaveCenter;

    @FXML
    private MFXPillButton btnDeleteCenter;

    @FXML
    private ComboBox<String> cbState;

    @FXML
    private ComboBox<CenterStatus> cbStatus;

    @FXML
    private MFXTextField tfAddress;

    @FXML
    private MFXTextField tfCenterName;

    @FXML
    private MFXTextField tfPostcode;

    @FXML
    void onClosePressed(ActionEvent event) {
        getStageDialog().close();
    }

    @FXML
    void onDeleteCenterPressed(ActionEvent event) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                btnDeleteCenter.getScene().getWindow(),
                "Deletion of Vaccination Center",
                "You are about to delete the center '" + vaccinationCenter.getVaccinationCenterName() + "'.",
                "Do you want to proceed?"
        );
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vaccinationCenter.setVoided();
        }

        vaccineCentersController.refresh();
        getStageDialog().close();
    }

    @FXML
    void onEditCenterPressed(ActionEvent event) {
        btnEditCenter.setManaged(false);
        btnSaveCenter.setManaged(true);

        tfCenterName.setDisable(false);
        tfAddress.setDisable(false);
        tfPostcode.setDisable(false);
        cbState.setDisable(false);
        cbStatus.setDisable(false);

        tfCenterName.setStyle("-fx-opacity: 0.99");
        tfAddress.setStyle("-fx-opacity: 0.99");
        tfPostcode.setStyle("-fx-opacity: 0.99");
    }

    @FXML
    void onSaveCenterPressed(ActionEvent event) {
        String centerName = tfCenterName.getText();
        String centerAddress = tfAddress.getText();
        String centerPostcode = tfPostcode.getText();

        //Empty Fields Validation
        if (centerName.equals("") ||
                centerAddress.equals("") ||
                centerPostcode.equals("")
        ) {
            Page.showDialog(
                    btnSaveCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return;
        }

        if (cbState.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(
                    cbState.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Vaccination Center Selected",
                    "Please ensure you select a vaccination center!"
            );
            return;
        }


        // Center Name Validation
        if (centerName.matches(".*\\d.*")) {
            Page.showDialog(
                    btnSaveCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Vaccine Name",
                    "The name should not contain any numbers!"
            );
            return;
        }

        //Identification Number Validation
        if (!centerPostcode.matches("^[0-9]*$")) {
            Page.showDialog(
                    btnSaveCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Postcode",
                    "Postcode must only have numbers!"
            );
            return;
        }

        VaccinationCenter editedCenter = vaccinationCenter;

        editedCenter.setVaccinationCenterName(centerName);
        editedCenter.setCenterAddress(centerAddress);
        editedCenter.setCenterPostcode(centerPostcode);
        editedCenter.setCenterState(cbState.getSelectionModel().getSelectedItem());
        editedCenter.setCenterStatus(cbStatus.getSelectionModel().getSelectedItem());

        editedCenter.save();

        Page.showDialog(btnSaveCenter.getScene().getWindow(), DialogType.INFO, "Success", "Successfully added new center.");
        getStageDialog().close();
        vaccineCentersController.refresh();
    }

    @Override
    public void onLoaded() {
        tfPostcode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfPostcode.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        if(!Global.isCommittee()) {
            btnEditCenter.setManaged(false);
            btnDeleteCenter.setManaged(false);
        }

        btnSaveCenter.setManaged(false);

        vaccineCentersController = (VaccineCentersController) getUserData();
        vaccinationCenter = vaccineCentersController.selectedVaccinationCenter;

        tfCenterName.setText(vaccinationCenter.getVaccinationCenterName());
        tfAddress.setText(vaccinationCenter.getCenterAddress());
        tfPostcode.setText(vaccinationCenter.getCenterPostcode());

        ObservableList<String> states =
                FXCollections.observableArrayList(
                        "Kuala Lumpur",
                        "Labuan",
                        "Putrajaya",
                        "Terengganu",
                        "Selangor",
                        "Sarawak",
                        "Sabah",
                        "Perlis",
                        "Perak",
                        "Penang",
                        "Pahang",
                        "Negeri Sembilan",
                        "Malacca",
                        "Kelantan",
                        "Kedah",
                        "Johor"
                );

        cbState.setItems(states);
        cbState.getSelectionModel().select(vaccinationCenter.getCenterState());

        cbStatus.getItems().setAll(CenterStatus.values());
        cbStatus.getSelectionModel().select(vaccinationCenter.getCenterStatus());

        cbStatus.setConverter(new StringConverter<CenterStatus>() {
            @Override
            public String toString(CenterStatus centerStatus) {
                return centerStatus.getValue();
            }

            @Override
            public CenterStatus fromString(String string) {
                return CenterStatus.valueOf(string);
            }
        });
    }
}
