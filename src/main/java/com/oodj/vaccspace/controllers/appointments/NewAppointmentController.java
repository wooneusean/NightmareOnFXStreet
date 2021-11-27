package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.home.HomeController;
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
import java.util.*;

public class NewAppointmentController extends BaseController implements Initializable {
    private final NewAppointmentViewModel vm = new NewAppointmentViewModel();

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

        HashSet<VaccineType> vaccineTypes = new HashSet<>();
        selectedCenter.getVaccineBatches().forEach(centerBatch -> {
            centerBatch.include(VaccineType.class);
            vaccineTypes.add(centerBatch.getVaccineType());
        });

        vm.setVaccineTypeListProperty(FXCollections.observableList(new ArrayList<>(vaccineTypes)));

        cbVaccine.setDisable(false);
    }

    @Override
    public void onLoaded() {
    }

    @FXML
    void onSubmitPressed(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        VaccineBatch nextVaccineBatch;

        try {
            nextVaccineBatch = VaccineBatch.getNextAvailableVaccineBatch(
                    vm.getSelectedVaccineTypeProperty()
                      .getSelectedItem()
                      .getVaccineName()
            );
        } catch (IndexOutOfBoundsException | NoSuchElementException | IllegalArgumentException ex) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Batches Available",
                    ex.getMessage()
            );
            return;
        }

        nextVaccineBatch.setAvailableAmount(nextVaccineBatch.getAmount() - 1);
        nextVaccineBatch.save();

        Vaccine bookedVaccine = new Vaccine(nextVaccineBatch.getId(), VaccineStatus.BOOKED);
        bookedVaccine.save();

        VaccinationCenter vaccinationCenter = vm.getSelectedVaccinationCenterProperty().getSelectedItem();

        LocalDate appointmentDate = vm.getLocalDateObjectProperty();

        Person person = ((HomeController) getUserData()).getPerson();

        person.include(Appointment.class);
        List<Appointment> previousAppointments = person.getAppointments();

        boolean hasFulfilledAppointments = previousAppointments.stream()
                                                               .anyMatch(
                                                                       appointment -> appointment
                                                                               .getAppointmentStatus()
                                                                               .equals(AppointmentStatus.FULFILLED)
                                                               );

        Appointment appointment = new Appointment(
                person.getId(),
                vaccinationCenter.getId(),
                bookedVaccine.getId(),
                appointmentDate,
                AppointmentStatus.CONFIRMED,
                hasFulfilledAppointments ? Dose.SECOND : Dose.FIRST
        );
        appointment.save();

        if (person.getVaccinationStatus().equals(VaccinationStatus.NOT_REGISTERED)) {
            person.setVaccinationStatus(VaccinationStatus.AWAITING_FIRST_DOSE);
            person.save();
        }

        getStageDialog().close();
        ((HomeController) getUserData()).refresh();
    }

    boolean validateInputs() {
        if (cbCenter.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Vaccination Center Selected",
                    "Please ensure you select a vaccination center!"
            );
            return false;
        }

        if (cbVaccine.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Vaccine Selected",
                    "Please ensure you select a vaccine!"
            );
            return false;
        }

        if (dpDate.getValue() == null) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: No Date Selected",
                    "Please ensure you select an appointment date!"
            );
            return false;
        }

        if (dpDate.getValue().isBefore(LocalDate.now())) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Date Selected is Before Today",
                    "Please ensure you select a date after today!"
            );
            return false;
        }

        if (hasPendingAppointments()) return false;

        if (isVaccineTypeMismatch()) return false;

        return true;
    }

    private boolean isVaccineTypeMismatch() {
        Person person = ((HomeController) getUserData()).getPerson();

        person.include(Appointment.class);

        List<Appointment> appointments = person.getAppointments();

        if (appointments == null) {
            return false;
        }

        Optional<Appointment> possibleAppointment = appointments.stream()
                                                                .filter(appointment ->
                                                                                appointment.getAppointmentStatus() ==
                                                                                AppointmentStatus.FULFILLED)
                                                                .findFirst();

        if (possibleAppointment.isEmpty()) {
            return false;
        }

        Appointment previousFulfilledAppointment = possibleAppointment.get();
        previousFulfilledAppointment.include(Vaccine.class);
        previousFulfilledAppointment.getVaccine().include(VaccineBatch.class);
        previousFulfilledAppointment.getVaccine().getVaccineBatch().include(VaccineType.class);
        VaccineType previousVaccineType = previousFulfilledAppointment.getVaccine().getVaccineBatch().getVaccineType();
        if (cbVaccine.getSelectionModel().getSelectedItem().getId() != previousVaccineType.getId()) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccine Type Does Not Match",
                    "You have a fulfilled appointment with a different vaccine type! " +
                    "We strongly advise against mixing your vaccines! " +
                    "Please go select your previous vaccine, which is '" + previousVaccineType.getVaccineName() + "'."
            );
            return true;
        }
        return false;
    }

    private boolean hasPendingAppointments() {
        Person person = ((HomeController) getUserData()).getPerson();
        person.include(Appointment.class);

        List<Appointment> appointments = person.getAppointments();

        if (appointments == null) {
            return false;
        }

        boolean hasPendingAppointments = appointments
                .stream()
                .anyMatch(appointment ->
                                  appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED
                );
        if (hasPendingAppointments) {
            Page.showDialog(
                    cbCenter.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Unfulfilled Appointment Found",
                    "You already have an unfulfilled appointment! Please fulfill all appointments before creating new appointments!"
            );
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vm.selectedVaccinationCenterPropertyProperty().bindBidirectional(cbCenter.selectionModelProperty());
        vm.selectedVaccineTypePropertyProperty().bindBidirectional(cbVaccine.selectionModelProperty());

        vm.vaccinationCenterListPropertyProperty().bindBidirectional(cbCenter.itemsProperty());
        vm.vaccineTypeListPropertyProperty().bindBidirectional(cbVaccine.itemsProperty());

        vm.localDateObjectPropertyProperty().bindBidirectional(dpDate.valueProperty());

        vm.setVaccinationCenterListProperty(
                FXCollections.observableList(
                        Objects.requireNonNull(
                                TextORM.getAll(VaccinationCenter.class, vacc -> true)
                        )
                )
        );

        cbVaccine.setDisable(true);
    }

    @FXML
    public void onClosePressed(ActionEvent actionEvent) {
        getStageDialog().close();
    }
}