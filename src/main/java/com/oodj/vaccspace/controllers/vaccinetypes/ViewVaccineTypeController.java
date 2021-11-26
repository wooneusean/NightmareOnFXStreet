package com.oodj.vaccspace.controllers.vaccinetypes;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewVaccineTypeController extends BaseController implements Initializable {
    NewOrEditVaccineTypeViewModel vm = null;
    VaccineTypesController vaccineTypesController = null;
    VaccineType vaccineType = new VaccineType();

    @FXML
    private Label lblClose;

    @FXML
    private MFXPillButton btnSaveVaccineType;

    @FXML
    private MFXPillButton btnDeleteVaccineType;

    @FXML
    private MFXPillButton btnEditVaccineType;

    @FXML
    private MFXTextField tfCompany;

    @FXML
    private MFXTextField tfDose;

    @FXML
    private MFXTextField tfVaccineName;

    @FXML
    void onEditVaccineTypePressed(ActionEvent event) {
        btnEditVaccineType.setManaged(false);
        btnSaveVaccineType.setManaged(true);

        tfVaccineName.setDisable(false);
        tfCompany.setDisable(false);
        tfDose.setDisable(false);

        tfVaccineName.setStyle("-fx-opacity: 0.99");
        tfCompany.setStyle("-fx-opacity: 0.99");
        tfDose.setStyle("-fx-opacity: 0.99");

        this.vm = new NewOrEditVaccineTypeViewModel(
                tfVaccineName.textProperty(),
                tfCompany.textProperty(),
                tfDose.textProperty()
        );

        tfDose.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfDose.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    void onSaveVaccineTypePressed(ActionEvent event) {
        //Empty Fields Validation
        if (vm.getVaccineName().equals("") ||
            vm.getManufacturingCompany().equals("") ||
            vm.getNumberOfDoses().equals("")
        ) {
            Page.showDialog(
                    btnSaveVaccineType.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return;
        }

        //Doses validation
        if (Integer.parseInt(vm.getNumberOfDoses()) <= 0) {
            Page.showDialog(
                    btnSaveVaccineType.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Dose",
                    "Doses must be more than 0!"
            );
            return;
        }

        // Vaccine Name Validation
        if (vm.getVaccineName().matches(".*\\d.*")) {
            Page.showDialog(
                    btnSaveVaccineType.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Vaccine Name",
                    "The name should not contain any numbers!"
            );
            return;
        }

        // Manufacturing Company Validation
        if (vm.getManufacturingCompany().matches(".*\\d.*")) {
            Page.showDialog(
                    btnSaveVaccineType.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Vaccine Name",
                    "The name should not contain any numbers!"
            );
            return;
        }
        vaccineType.setVaccineName(tfVaccineName.getText());
        vaccineType.setVaccineName(vm.getVaccineName().trim());
        vaccineType.setManufacturingCompany(vm.getManufacturingCompany());
        vaccineType.setDosesNeeded(Integer.parseInt(vm.getNumberOfDoses()));

        vaccineType.save();
        vaccineTypesController.refresh();

        Page.showDialog(
                btnSaveVaccineType.getScene().getWindow(),
                DialogType.INFO,
                "Success",
                "Successfully added new vaccine."
        );
        getStageDialog().close();
    }

    @FXML
    void onClosePressed(MouseEvent event) {
        getStageDialog().close();
    }

    @FXML
    public void onDeleteVaccineTypePressed(ActionEvent actionEvent) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                btnDeleteVaccineType.getScene().getWindow(),
                "Deletion of Vaccine Type",
                "You are about to delete the vaccine type '" + vaccineType.getVaccineName() + "'.",
                "Do you want to proceed?"
        );
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vaccineType.setVoided();
        }

        vaccineTypesController.refresh();
        getStageDialog().close();
    }

    @Override
    public void onLoaded() {
        if (!Global.isCommittee()) {
            btnEditVaccineType.setManaged(false);
        }

        btnSaveVaccineType.setManaged(false);

        vaccineTypesController = (VaccineTypesController) getUserData();
        vaccineType = vaccineTypesController.selectedVaccineType;

        tfVaccineName.setText(vaccineType.getVaccineName());
        tfCompany.setText(vaccineType.getManufacturingCompany());
        tfDose.setText(String.valueOf(vaccineType.getDosesNeeded()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FontIcon icon = new FontIcon("fas-times");
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(18);

        lblClose.setGraphic(icon);

        if (!Global.isCommittee()) {
            btnDeleteVaccineType.setManaged(false);
        }
    }
}