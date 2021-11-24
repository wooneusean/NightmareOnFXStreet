package com.oodj.vaccspace.controllers.home;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.appointments.AppointmentStatusIndicatorCell;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.TableHelper;
import io.github.euseanwoon.MFXPillButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    Person person = null;

    @FXML
    private MFXPillButton btnNewAppointment;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblVaccinationStatus;

    @FXML
    private TableView<Appointment> tblAppointments;

    @FXML
    void onNewAppointmentPressed(ActionEvent event) {
        Navigator.showInDialog(btnNewAppointment.getScene().getWindow(), "new_appointment", this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        person = Global.getLoggedInUser();
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

    public void refresh() {
        person = Global.getLoggedInUser();
        person.include(Appointment.class);
        tblAppointments.setItems(FXCollections.observableArrayList(person.getAppointments()));
        updateVaccinationStatusBanner();
    }

    private void setupTable() {
        TableColumn<Appointment, String> appointmentLocation = new TableColumn<>("Location");
        appointmentLocation.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue()
                                                                                       .getVaccinationCenter()
                                                                                       .getVaccinationCenterName()));

        TableColumn<Appointment, String> appointmentVaccine = new TableColumn<>("Vaccine");
        appointmentVaccine.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getVaccineName()));

        TableColumn<Appointment, LocalDate> appointmentDate = new TableColumn<>("Appointment Date");
        appointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        TableColumn<Appointment, AppointmentStatus> appointmentStatus = new TableColumn<>("Status");
        appointmentStatus.setCellValueFactory(new PropertyValueFactory<>("appointmentStatus"));
        appointmentStatus.setCellFactory(statusColumn -> new AppointmentStatusIndicatorCell());

        TableColumn<Appointment, String> dose = new TableColumn<>("Dose");
        dose.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getDose().getValue()));

        tblAppointments.getColumns()
                       .addAll(appointmentLocation, appointmentVaccine, appointmentDate, appointmentStatus, dose);

        // Autosize all columns
        TableHelper.autoSizeColumns(tblAppointments);

        if (person.getAppointments() == null || person.getAppointments().size() == 0) return;

        person.getAppointments().sort(Comparator.comparing(Appointment::getAppointmentDate));

        tblAppointments.setItems(FXCollections.observableArrayList(person.getAppointments()));

        // https://stackoverflow.com/a/26565887/4987298
        tblAppointments.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    Appointment rowData = row.getItem();
                    Navigator.showInDialog(tblAppointments.getScene().getWindow(), "view_appointment", rowData);
                }
            });
            return row;
        });
    }


    private void updateVaccinationStatusBanner() {
        lblVaccinationStatus.setText(String.format("Vaccination Status: %s", person.getVaccinationStatus().getValue()));
        lblVaccinationStatus.setStyle(String.format(
                "-fx-background-color: %s;",
                person.getVaccinationStatus().getColor()
        ));
        lblGreeting.setText(getGreetingText(person.getName()));
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
