package com.oodj.vaccspace.controllers.vaccinecenters;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.vaccinetypes.NewOrEditVaccineTypeViewModel;
import com.oodj.vaccspace.models.CenterStatus;
import com.oodj.vaccspace.models.VaccinationCenter;
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
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewCenterController extends BaseController implements Initializable {

    @FXML
    private MFXPillButton btnClose;

    @FXML
    private MFXPillButton btnSaveCenter;

    @FXML
    private ComboBox<String> cbState;

    @FXML
    private MFXTextField tfAddress;

    @FXML
    private MFXTextField tfCenterName;

    @FXML
    private MFXTextField tfPostcode;

    @FXML
    void onClosePressed(ActionEvent event)  {
        getStageDialog().close();
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

        VaccinationCenter newCenter = new VaccinationCenter();

        newCenter.setVaccinationCenterName(centerName);
        newCenter.setCenterAddress(centerAddress);
        newCenter.setCenterPostcode(centerPostcode);
        newCenter.setCenterState(cbState.getSelectionModel().getSelectedItem());
        newCenter.setCenterStatus(CenterStatus.OPEN);

        newCenter.save();

        Page.showDialog(btnSaveCenter.getScene().getWindow(), DialogType.INFO, "Success", "Successfully added new center.");
        getStageDialog().close();
        ((VaccineCentersController) getUserData()).refresh();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfPostcode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfPostcode.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

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
    }
}
