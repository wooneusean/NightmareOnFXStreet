package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewAppointmentController extends BaseController implements Initializable {
    Appointment appointment = null;

    @FXML
    private HBox boxCitizenView;

    @FXML
    private VBox boxCommitteeView;

    @FXML
    private MFXPillButton btnAbsent;

    @FXML
    private MFXPillButton btnCancelAppointment;

    @FXML
    private MFXPillButton btnFulfilled;

    @FXML
    private MFXPillButton btnVoidAppointment;

    @FXML
    private Label lblClose;

    @FXML
    private Label lblControls;

    @FXML
    private MFXTextField txtCenter;

    @FXML
    private MFXTextField txtDate;

    @FXML
    private MFXTextField txtDose;

    @FXML
    private MFXTextField txtVaccine;

    @FXML
    void onAbsentPressed(ActionEvent event) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                btnAbsent.getScene().getWindow(),
                "Changing Appointment Status",
                "You are about to set the status to ABSENT!",
                "Do you want to proceed?"
        );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            appointment.absentAppointment();
        }

//        Global.getHomeReference().refresh();
//        Navigator.navigateInContainer("appointment", Global.getDashboardReference().getVbxContent(), null);

        getStageDialog().close();
    }

    @FXML
    void onFulfilledPressed(ActionEvent event) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                btnFulfilled.getScene().getWindow(),
                "Changing Appointment Status",
                "You are about to set the status to FULFILLED!",
                "Do you want to proceed?"
        );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            appointment.fulfillAppointment();
        }

//        Global.getHomeReference().refresh();

        getStageDialog().close();
    }

    @FXML
    public void onCancelAppointmentPressed(ActionEvent event) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                btnVoidAppointment.getScene().getWindow(),
                "Changing Appointment Status",
                "You are about to set the status to CANCEL / VOIDED!",
                "Do you want to proceed?"
        );

        if (result.isPresent() && result.get() == ButtonType.OK) {
            appointment.cancel();
        }

//        Global.getHomeReference().refresh();

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

        if(Global.isCommittee() && appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {
            boxCitizenView.setManaged(false);
            btnCancelAppointment.setManaged(false);


        } else if (!Global.isCommittee() && appointment.getAppointmentStatus().equals(AppointmentStatus.CONFIRMED)) {


            boxCommitteeView.setManaged(false);
            lblControls.setManaged(false);
            btnAbsent.setManaged(false);
            btnVoidAppointment.setManaged(false);
            btnFulfilled.setManaged(false);
        } else {
            // No controls shown at all
            boxCitizenView.setManaged(false);
            btnCancelAppointment.setManaged(false);

            boxCommitteeView.setManaged(false);
            lblControls.setManaged(false);
            btnAbsent.setManaged(false);
            btnVoidAppointment.setManaged(false);
            btnFulfilled.setManaged(false);
        }
    }
}
