package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentController extends BaseController implements Initializable {
    Appointment appointment = null;

    @FXML
    private MFXPillButton btnCancelAppointment;

    @FXML
    private Label lblClose;

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

        Global.getHomeReference().refresh();

        getStageDialog().close();
    }


    @FXML
    void onClosePressed(MouseEvent event) {
        getStageDialog().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FontIcon icon = new FontIcon("fas-times");
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(18);

        lblClose.setGraphic(icon);
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
