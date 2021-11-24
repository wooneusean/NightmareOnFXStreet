package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.models.*;
import com.oodj.vaccspace.utils.StringHelper;
import com.oodj.vaccspace.utils.TableHelper;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import textorm.TextORM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AppointmentsController implements Initializable {

    AppointmentsViewModel vm = new AppointmentsViewModel();

    ObservableList<Appointment> masterData;

    FilteredList<Appointment> filteredData;

    SortedList<Appointment> sortableData;

    @FXML
    private TableView<Appointment> tblAppointments;

    @FXML
    private MFXTextField txtSearch;

    @FXML
    void onSearchChanged(KeyEvent event) {

    }

    private Predicate<Appointment> getFilterAppointmentPredicate() {
        return appointment -> {
            appointment.include(Person.class);
            appointment.include(Vaccine.class);
            appointment.include(VaccinationCenter.class);
            appointment.getVaccine().include(VaccineBatch.class);
            appointment.getVaccine().getVaccineBatch().include(VaccineType.class);
            String vaccineName = appointment.getVaccine().getVaccineBatch().getVaccineType().getVaccineName();

            String vaccinationCenterName = appointment.getVaccinationCenter().getVaccinationCenterName();

            return StringHelper.containsIgnoreCase(appointment.getPerson().getName(), vm.getSearch()) ||
                   StringHelper.containsIgnoreCase(vaccinationCenterName, vm.getSearch()) ||
                   StringHelper.containsIgnoreCase(vaccineName, vm.getSearch()) ||
                   StringHelper.containsIgnoreCase(appointment.getAppointmentStatus().getValue(), vm.getSearch()) ||
                   StringHelper.containsIgnoreCase(appointment.getDose().getValue(), vm.getSearch());
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vm.searchProperty().bindBidirectional(txtSearch.textProperty());

        vm.searchProperty().addListener((observableValue, s, t1) -> {
            filteredData.setPredicate(getFilterAppointmentPredicate());
        });

        TableColumn<Appointment, String> personColumn = new TableColumn<>("Registered Person");
        personColumn.setCellValueFactory(rowData -> {
            rowData.getValue().include(Person.class);
            return new SimpleStringProperty(rowData.getValue().getPerson().getName());
        });

        TableColumn<Appointment, String> vaccinationCenterColumn = new TableColumn<>("Vaccination Center");
        vaccinationCenterColumn.setCellValueFactory(rowData -> {
            rowData.getValue().include(VaccinationCenter.class);
            return new SimpleStringProperty(rowData.getValue().getVaccinationCenter().getVaccinationCenterName());
        });

        TableColumn<Appointment, String> vaccinationTypeColumn = new TableColumn<>("Vaccine Type");
        vaccinationTypeColumn.setCellValueFactory(rowData -> {
            Appointment appointment = rowData.getValue();
            appointment.include(Vaccine.class);
            appointment.getVaccine().include(VaccineBatch.class);
            appointment.getVaccine().getVaccineBatch().include(VaccineType.class);
            String vaccineName = appointment.getVaccine().getVaccineBatch().getVaccineType().getVaccineName();

            return new SimpleStringProperty(vaccineName);
        });

        TableColumn<Appointment, LocalDate> appointmentDateColumn = new TableColumn<>("Appointment Date");
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        TableColumn<Appointment, AppointmentStatus> vaccineCenterStatusColumn = new TableColumn<>("Status");
        vaccineCenterStatusColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStatus"));
        vaccineCenterStatusColumn.setCellFactory(statusColumn -> new AppointmentStatusIndicatorCell());

        TableColumn<Appointment, String> doseColumn = new TableColumn<>("Dose");
        doseColumn.setCellValueFactory(rowData -> new SimpleStringProperty(rowData.getValue().getDose().getValue()));

        tblAppointments.getColumns().addAll(
                personColumn,
                vaccinationCenterColumn,
                vaccinationTypeColumn,
                appointmentDateColumn,
                vaccineCenterStatusColumn,
                doseColumn
        );

        tblAppointments.setRowFactory(tableView -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    Appointment Appointment = row.getItem();
                    System.out.println(Appointment.getPerson().getName());
                }
            });
            return row;
        });

        TableHelper.autoSizeColumns(tblAppointments);

        List<Appointment> appointments = TextORM.getAll(Appointment.class, hashMap -> true);

        masterData = FXCollections.observableArrayList(appointments);
        filteredData = new FilteredList<>(masterData);
        sortableData = new SortedList<>(filteredData);

        tblAppointments.setItems(sortableData);

        sortableData.comparatorProperty().bind(tblAppointments.comparatorProperty());
    }
}