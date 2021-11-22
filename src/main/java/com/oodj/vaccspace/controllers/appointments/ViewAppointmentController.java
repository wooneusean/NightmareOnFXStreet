package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import com.oodj.vaccspace.models.VaccinationStatus;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentController extends BaseController implements Initializable {
    Appointment appointment = null;

    @FXML
    private MFXPillButton btnCancelAppointment;

    @FXML
    private MFXPillButton btnClose;

    @FXML
    private MFXTextField txtCenter;

    @FXML
    private MFXTextField txtDate;

    @FXML
    private MFXTextField txtVaccine;

    @FXML
    private MFXTextField txtDose;

    @FXML
    public void onCancelAppointmentPressed(ActionEvent event) {
        appointment.cancel();

        Global.getDashboardReference().refresh();

        getStageDialog().close();
    }

    @FXML
    public void onClosePressed(ActionEvent actionEvent) {
        getStageDialog().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void onLoaded() {
        appointment = (Appointment) getUserData();
        txtCenter.setText(appointment.getVaccinationCenter().toString());
        txtVaccine.setText(appointment.getVaccineName());
        txtDate.setText(appointment.getAppointmentDate().toString());
        txtDose.setText(appointment.getDose().getValue());

        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {
            btnCancelAppointment.setManaged(false);
        }
    }
}
