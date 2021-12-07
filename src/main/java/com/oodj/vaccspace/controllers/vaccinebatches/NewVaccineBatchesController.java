package com.oodj.vaccspace.controllers.vaccinebatches;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.VaccinationCenter;
import com.oodj.vaccspace.models.VaccineBatch;
import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import textorm.TextORM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewVaccineBatchesController extends BaseController implements Initializable {

    @FXML
    private MFXPillButton btnAddBatch;

    @FXML
    private MFXPillButton btnCancel;

    @FXML
    private ComboBox<VaccinationCenter> cmbVaccinationCenter;

    @FXML
    private ComboBox<VaccineType> cmbVaccineType;

    @FXML
    private DatePicker dtpExpiryDate;

    @FXML
    private MFXTextField txtBatchAmount;

    @FXML
    void onAddBatchPressed(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        int batchAmount = Integer.parseInt(txtBatchAmount.getText());
        VaccinationCenter vaccinationCenter = cmbVaccinationCenter.getSelectionModel().getSelectedItem();
        VaccineType vaccineType = cmbVaccineType.getSelectionModel().getSelectedItem();

        VaccineBatch newVaccineBatch = new VaccineBatch(
                vaccineType.getId(),
                batchAmount,
                batchAmount,
                vaccinationCenter.getId(),
                LocalDate.now(),
                dtpExpiryDate.getValue()
        );

        newVaccineBatch.save();

        getStageDialog().close();
        ((VaccineBatchesController) getUserData()).refresh();
    }

    @FXML
    void onCancelPressed(ActionEvent event) {
        getStageDialog().close();
    }

    boolean validateInput() {
        if (cmbVaccinationCenter.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(
                    btnAddBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Vaccination Center Selected",
                    "Please ensure you select a vaccination center!"
            );
            return false;
        }

        if (cmbVaccineType.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(
                    btnAddBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Vaccine Type Selected",
                    "Please ensure you select a vaccine type!"
            );
            return false;
        }

        int batchAmount;

        try {
            batchAmount = Integer.parseInt(txtBatchAmount.getText());
        } catch (NumberFormatException e) {
            Page.showDialog(
                    btnAddBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccine Batch Amount is not in a Valid Format",
                    "Please ensure you input a valid number!"
            );
            return false;
        }

        if (batchAmount <= 0) {
            Page.showDialog(
                    btnAddBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccine Batch Amount is Less Than 1",
                    "Please ensure you at least have 1 vaccine in a batch!"
            );
            return false;
        }

        if (dtpExpiryDate.getValue() == null) {
            Page.showDialog(
                    btnAddBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Expiry Date Selected",
                    "Please ensure you select an expiry date!"
            );
            return false;
        }

        if (dtpExpiryDate.getValue().isBefore(LocalDate.now())) {
            Page.showDialog(
                    btnAddBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Expiry Date Cannot Be Before Today",
                    "Please set an expiry date that is after today!"
            );
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<VaccinationCenter> vaccinationCenters = TextORM.getAll(VaccinationCenter.class, data -> true);

        cmbVaccinationCenter.setItems(FXCollections.observableList(vaccinationCenters));

        List<VaccineType> vaccineTypes = TextORM.getAll(
                VaccineType.class,
                data -> Objects.equals(data.get("isVoided"), "false")
        );

        cmbVaccineType.setItems(FXCollections.observableList(vaccineTypes));

        txtBatchAmount.setText("0");

        txtBatchAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.startsWith("0") && newValue.length() > 1) {
                txtBatchAmount.setText(newValue.substring(1));
            }

            if (!newValue.matches("\\d*")) {
                txtBatchAmount.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (newValue.equals("")) {
                txtBatchAmount.setText("0");
            }
        });
    }
}
