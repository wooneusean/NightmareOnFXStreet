package com.oodj.vaccspace.controllers.home;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.appointments.AppointmentStatusIndicatorCell;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.TableHelper;
import io.github.euseanwoon.MFXPillButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import textorm.Model;
import textorm.TextORM;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class HomeController extends BaseController {

    Person person = null;

    Appointment selectedAppointment;

    ObservableList<Appointment> masterData;

    FilteredList<Appointment> filteredData;

    SortedList<Appointment> sortableData;

    @FXML
    private MFXPillButton btnNewAppointment;

    @FXML
    private Hyperlink lblBack;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblName;

    @FXML
    private Label lblVaccinationStatus;

    @FXML
    private TableView<Appointment> tblAppointments;

    public Person getPerson() {
        return person;
    }

    @Override
    public <T extends Model> T getSelectedModel() {
        return (T) selectedAppointment;
    }

    @FXML
    void onNewAppointmentPressed(ActionEvent event) {
        Navigator.showInDialog(btnNewAppointment.getScene().getWindow(), "new_appointment", this);
    }

    @FXML
    void onLblBack(ActionEvent event) {
        Navigator.navigateInContainer("people", Global.getDashboardReference().getVbxContent(), null);
    }

    @Override
    public void onLoaded() {

        if (Global.isCommittee()) {
            person = (Person) getUserData();
            lblGreeting.setManaged(false);
        } else {
            person = Global.getLoggedInUser();
            lblBack.setManaged(false);
            lblBack.setVisible(false);
            lblName.setVisible(false);
        }

        Global.setHomeReference(this);

        if (person == null) {
            Navigator.navigate("login");
            return;
        }

        person.include(Appointment.class);

        setupUI();
    }

    private void setupUI() {
        updateVaccinationStatusBanner();

        setupTable();
    }

    @Override
    public void refresh() {
        person = TextORM.getOne(Person.class, hashMap -> Integer.parseInt(hashMap.get("id")) == person.getId());

        List<Appointment> appointments = TextORM.getAll(
                Appointment.class,
                hashMap -> Integer.parseInt(hashMap.get("personId")) == person.getId()
        );

        masterData = FXCollections.observableArrayList(appointments);
        filteredData = new FilteredList<>(masterData);
        sortableData = new SortedList<>(filteredData);

        tblAppointments.setItems(sortableData);

        sortableData.comparatorProperty().bind(tblAppointments.comparatorProperty());

        updateVaccinationStatusBanner();

        if (person.getVaccinationStatus().equals(VaccinationStatus.FULLY_VACCINATED)) {
            btnNewAppointment.setManaged(false);
            btnNewAppointment.setVisible(false);
        }
    }

    private void setupTable() {
        TableColumn<Appointment, String> appointmentLocation = new TableColumn<>("Location");
        appointmentLocation.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue()
                                                                                       .getVaccinationCenter()
                                                                                       .getVaccinationCenterName()));

        TableColumn<Appointment, String> appointmentVaccineColumn = new TableColumn<>("Vaccine");
        appointmentVaccineColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue()
                                                                                            .getVaccineName()));

        TableColumn<Appointment, LocalDate> appointmentDateColumn = new TableColumn<>("Appointment Date");
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        TableColumn<Appointment, AppointmentStatus> appointmentStatusColumn = new TableColumn<>("Status");
        appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStatus"));
        appointmentStatusColumn.setCellFactory(statusColumn -> new AppointmentStatusIndicatorCell());

        TableColumn<Appointment, String> doseColumn = new TableColumn<>("Dose");
        doseColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getDose().getValue()));

        tblAppointments.getColumns().addAll(
                appointmentLocation,
                appointmentVaccineColumn,
                appointmentDateColumn,
                appointmentStatusColumn,
                doseColumn
        );

        // https://stackoverflow.com/a/26565887/4987298
        tblAppointments.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedAppointment = row.getItem();
                    Navigator.showInDialog(tblAppointments.getScene().getWindow(), "view_appointment", this);
                }
            });
            return row;
        });

        // Autosize all columns
        TableHelper.autoSizeColumns(tblAppointments);

        refresh();
    }


    private void updateVaccinationStatusBanner() {
        lblVaccinationStatus.setText(String.format("Vaccination Status: %s", person.getVaccinationStatus().getValue()));
        lblVaccinationStatus.setStyle(String.format(
                "-fx-background-color: %s;",
                person.getVaccinationStatus().getColor()
        ));
        lblGreeting.setText(getGreetingText(person.getName()));
        lblName.setText("Appointments of " + person.getName());
    }

    private String getGreetingText(String name) {
        Calendar rightNow = Calendar.getInstance();
        int timeOfDay = rightNow.get(Calendar.HOUR_OF_DAY);
        String timeGreeting;
        if (timeOfDay < 12) {
            timeGreeting = "Good morning, %s";
        } else if (timeOfDay < 16) {
            timeGreeting = "Good afternoon, %s";
        } else if (timeOfDay < 21) {
            timeGreeting = "Good evening, %s";
        } else {
            timeGreeting = "Good night, %s";
        }

        return String.format(timeGreeting, name);
    }


}
