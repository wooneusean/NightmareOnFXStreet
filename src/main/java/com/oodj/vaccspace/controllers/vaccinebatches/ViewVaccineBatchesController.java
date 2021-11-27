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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import textorm.TextORM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewVaccineBatchesController extends BaseController implements Initializable {

    VaccineBatchesController vaccineBatchController = null;

    VaccineBatch vaccineBatch = new VaccineBatch();

    int registeredVaccines = 0;

    @FXML
    private Label lblClose;

    @FXML
    private MFXPillButton btnDeleteBatch;

    @FXML
    private MFXPillButton btnEditBatch;

    @FXML
    private MFXPillButton btnSaveBatch;

    @FXML
    private ComboBox<VaccinationCenter> cmbVaccinationCenter;

    @FXML
    private ComboBox<VaccineType> cmbVaccineType;

    @FXML
    private DatePicker dtpExpiryDate;

    @FXML
    private MFXTextField txtBatchAmount;

    @FXML
    void onClosePressed(MouseEvent event) {
        getStageDialog().close();
    }

    @FXML
    void onDeleteBatchPressed(ActionEvent event) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                btnDeleteBatch.getScene().getWindow(),
                "Deletion of Vaccine Batch",
                "You are about to delete this batch!",
                "Do you want to proceed?"
        );
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vaccineBatch.delete();
        }

        vaccineBatchController.refresh();
        getStageDialog().close();
    }

    @FXML
    void onEditBatchPressed(ActionEvent event) {
        btnEditBatch.setManaged(false);
        btnSaveBatch.setManaged(true);

        txtBatchAmount.setDisable(false);
        dtpExpiryDate.setDisable(false);

        txtBatchAmount.setStyle("-fx-opacity: 0.99");
    }

    @FXML
    void onSaveBatchPressed(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        int batchAmount = Integer.parseInt(txtBatchAmount.getText());
        int originalAmount = vaccineBatch.getAmount();
        vaccineBatch.setAmount(batchAmount);
        vaccineBatch.setExpiryDate(dtpExpiryDate.getValue());
        vaccineBatch.setAvailableAmount(vaccineBatch.getAvailableAmount() + (batchAmount - originalAmount));

        vaccineBatch.save();

        ((VaccineBatchesController) getUserData()).refresh();
        getStageDialog().close();
    }

    boolean validateInput() {
        int batchAmount;

        try {
            batchAmount = Integer.parseInt(txtBatchAmount.getText());
        } catch (NumberFormatException e) {
            Page.showDialog(
                    btnSaveBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccine Batch Amount is not in a Valid Format",
                    "Please ensure you input a valid number!"
            );
            return false;
        }

        if (batchAmount <= 0) {
            Page.showDialog(
                    btnSaveBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccine Batch Amount is Less Than 1",
                    "Please ensure you at least have 1 vaccine in a batch!"
            );
            return false;
        }

        if (dtpExpiryDate.getValue() == null) {
            Page.showDialog(
                    btnSaveBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Expiry Date Selected",
                    "Please ensure you select an expiry date!"
            );
            return false;
        }

        if (dtpExpiryDate.getValue().isBefore(LocalDate.now())) {
            Page.showDialog(
                    btnSaveBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Expiry Date Cannot Be Before Today",
                    "Please set an expiry date that is after today!"
            );
            return false;
        }

//        List<Vaccine> registeredVaccines = TextORM.getAll(Vaccine.class,
//                data -> Objects.equals(data.get("vaccineBatchId"), vaccineBatch.getId()));
//
//        if (batchAmount < registeredVaccines.size()) {
//            Page.showDialog(
//                    btnSaveBatch.getScene().getWindow(),
//                    DialogType.ERROR,
//                    "Error: Vaccine Batch Amount is Less Than " + registeredVaccines.size(),
//                    "There are " + registeredVaccines.size() + " amount of vaccines registered to this batch, please ensure you at least have "
//                            + registeredVaccines.size() + " vaccines in this batch! "
//            );
//            return false;
//        }

        if (batchAmount < registeredVaccines) {
            Page.showDialog(
                    btnSaveBatch.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccine Batch Amount is Less Than " + registeredVaccines,
                    "There are " + registeredVaccines +
                    " amount of vaccines registered to this batch, please ensure you at least have "
                    + registeredVaccines + " vaccines in this batch!"
            );
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FontIcon icon = new FontIcon("fas-times");
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(18);

        lblClose.setGraphic(icon);

        List<VaccinationCenter> vaccinationCenters = TextORM.getAll(VaccinationCenter.class, data -> true);

        cmbVaccinationCenter.setItems(FXCollections.observableList(vaccinationCenters));

        List<VaccineType> vaccineTypes = TextORM.getAll(VaccineType.class, data -> true);

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

    @Override
    public void onLoaded() {
        btnSaveBatch.setManaged(false);

        vaccineBatchController = (VaccineBatchesController) getUserData();
        vaccineBatch = vaccineBatchController.selectedVaccineBatch;

        cmbVaccinationCenter.getSelectionModel().select(vaccineBatch.getVaccinationCenter());
        cmbVaccineType.getSelectionModel().select(vaccineBatch.getVaccineType());
        txtBatchAmount.setText(String.valueOf(vaccineBatch.getAmount()));
        dtpExpiryDate.setValue(vaccineBatch.getExpiryDate());

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

        registeredVaccines = vaccineBatch.getAmount() - vaccineBatch.getAvailableAmount();
    }
}
