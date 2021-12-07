package com.oodj.vaccspace.controllers.vaccinetypes;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class NewVaccineTypeController extends BaseController implements Initializable {

    NewOrEditVaccineTypeViewModel vm = null;

    @FXML
    private MFXPillButton btnClose;

    @FXML
    private MFXPillButton btnSaveVaccineType;

    @FXML
    private MFXTextField tfCompany;

    @FXML
    private MFXTextField tfDose;

    @FXML
    private MFXTextField tfVaccineName;

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

        VaccineType newVaccineType = new VaccineType(
                vm.getVaccineName(),
                vm.getManufacturingCompany(),
                Integer.parseInt(vm.getNumberOfDoses())
        );

        newVaccineType.save();

        Page.showDialog(
                btnSaveVaccineType.getScene().getWindow(),
                DialogType.INFO,
                "Success",
                "Successfully added new vaccine."
        );

        ((VaccineTypesController) getUserData()).refresh();

        getStageDialog().close();
    }

    @FXML
    void onClosePressed(ActionEvent event) {
        getStageDialog().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}