package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.dashboard.DashboardController;
import com.oodj.vaccspace.models.*;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewAppointmentController extends BaseController implements Initializable {
    private NewAppointmentViewModel vm = new NewAppointmentViewModel();

    @FXML
    private MFXPillButton btnClose;

    @FXML
    private MFXPillButton btnSubmit;

    @FXML
    private ComboBox<VaccinationCenter> cbCenter;

    @FXML
    private ComboBox<VaccineType> cbVaccine;

    @FXML
    private DatePicker dpDate;

    @FXML
    void onVaccinationCenterChanged(ActionEvent event) {
        var selectedCenter = vm.getSelectedVaccinationCenterProperty().getSelectedItem();
        selectedCenter.include(VaccineBatch.class);

        ArrayList<VaccineType> vaccineTypes = new ArrayList<>();
        selectedCenter.getVaccineBatches().forEach(centerBatch -> {
            if (vaccineTypes.stream().noneMatch(accumulatorType ->
                    Objects.equals(accumulatorType.getVaccineName(), centerBatch.getVaccineType().getVaccineName())
            )) {
                vaccineTypes.add(centerBatch.getVaccineType());
            }
        });

        vm.setVaccineTypeListProperty(FXCollections.observableList(
                Objects.requireNonNull(vaccineTypes)
        ));

        cbVaccine.setDisable(false);
    }

    @FXML
    void onSubmitPressed(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        VaccineBatch nextVaccineBatch = VaccineBatch.getNextAvailableVaccineBatch(vm.getSelectedVaccineTypeProperty().getSelectedItem().getVaccineName());
        nextVaccineBatch.setAvailableAmount(nextVaccineBatch.getAmount() - 1);
        nextVaccineBatch.save();

        Vaccine bookedVaccine = new Vaccine(nextVaccineBatch.getId(), VaccineStatus.BOOKED);
        bookedVaccine.save();

        VaccinationCenter vaccinationCenter = vm.getSelectedVaccinationCenterProperty().getSelectedItem();

        LocalDate appointmentDate = vm.getLocalDateObjectProperty();

        Appointment appointment = new Appointment(
                Global.getUserId(),
                vaccinationCenter.getId(),
                bookedVaccine.getId(),
                appointmentDate,
                AppointmentStatus.CONFIRMED,
                Dose.FIRST
        );
        appointment.save();

        getStageDialog().close();
        ((DashboardController) getUserData()).refresh();
    }

    boolean validateInputs() {
        if (cbCenter.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(cbCenter.getScene().getWindow(), DialogType.ERROR, "Error: No Vaccination Center Selected", "Please ensure you select a vaccination center!");
            return false;
        }

        if (cbVaccine.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(cbCenter.getScene().getWindow(), DialogType.ERROR, "Error: No Vaccine Selected", "Please ensure you select a vaccine!");
            return false;
        }

        if (dpDate.getValue() == null) {
            Page.showDialog(cbCenter.getScene().getWindow(), DialogType.ERROR, "Error: No Date Selected", "Please ensure you select an appointment date!");
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vm.selectedVaccinationCenterPropertyProperty().bindBidirectional(cbCenter.selectionModelProperty());
        vm.selectedVaccineTypePropertyProperty().bindBidirectional(cbVaccine.selectionModelProperty());

        vm.vaccinationCenterListPropertyProperty().bindBidirectional(cbCenter.itemsProperty());
        vm.vaccineTypeListPropertyProperty().bindBidirectional(cbVaccine.itemsProperty());

        vm.localDateObjectPropertyProperty().bindBidirectional(dpDate.valueProperty());

        vm.setVaccinationCenterListProperty(FXCollections.observableList(Objects.requireNonNull(TextORM.getAll(VaccinationCenter.class, vacc -> true))));
        cbVaccine.setDisable(true);
    }

    @FXML
    public void onClosePressed(ActionEvent actionEvent) {
        getStageDialog().close();
    }
}