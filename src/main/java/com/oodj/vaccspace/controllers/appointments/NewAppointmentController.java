package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.dashboard.DashboardController;
import com.oodj.vaccspace.models.*;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewAppointmentController extends BaseController implements Initializable {


    @FXML
    private MFXPillButton btnClose;

    @FXML
    private MFXPillButton btnSubmit;

    @FXML
    private ComboBox<VaccinationCenter> cbCenter;

    @FXML
    private ComboBox<VaccineType> cbVaccine;

    @FXML
    private MFXDatePicker dpDate;

    @FXML
    void onSubmitPressed(ActionEvent event) {
        Appointment appointment = new Appointment(Global.getUserId(), cbCenter.getSelectionModel().getSelectedItem().getId(), cbVaccine.getSelectionModel().getSelectedItem().getId(), dpDate.getDate(), AppointmentStatus.AWAITING_CONFIRMATION, Dose.FIRST);
        appointment.save();
        getStageDialog().close();
        ((DashboardController) getUserData()).refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<VaccinationCenter> centers = FXCollections.observableList(Objects.requireNonNull(TextORM.getAll(VaccinationCenter.class, vacc -> true)));
        cbCenter.setItems(centers);

        ObservableList<VaccineType> vaccines = FXCollections.observableList(Objects.requireNonNull(TextORM.getAll(VaccineType.class, type -> true)));
        cbVaccine.setItems(vaccines);
    }

    @FXML
    public void onClosePressed(ActionEvent actionEvent) {
        getStageDialog().close();
    }
}
