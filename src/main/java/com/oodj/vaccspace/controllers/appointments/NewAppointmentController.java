package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.controllers.dashboard.DashboardController;
import com.oodj.vaccspace.models.*;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.BindingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private MFXComboBox<VaccinationCenter> cbCenter;

    @FXML
    private MFXComboBox<VaccineType> cbVaccine;

    @FXML
    private MFXDatePicker dpDate;

    @FXML
    void onSubmitPressed(ActionEvent event) {
        Appointment appointment = new Appointment(Global.getUserId(), cbCenter.getSelectedValue().getId(), cbVaccine.getSelectedValue().getId(), dpDate.getDate(), AppointmentStatus.AWAITING_CONFIRMATION, Dose.FIRST);
        appointment.save();
        getStageDialog().close();
        ((DashboardController) getUserData()).refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<VaccinationCenter> centers = FXCollections.observableList(Objects.requireNonNull(TextORM.getAll(VaccinationCenter.class, vacc -> true)));
        cbCenter.setItems(centers);
        cbCenter.setValidated(true);
        cbCenter.getValidator().add(BindingUtils.toProperty(cbCenter.getSelectionModel().selectedIndexProperty().isNotEqualTo(-1)), "A value must be selected");

        ObservableList<VaccineType> vaccines = FXCollections.observableList(Objects.requireNonNull(TextORM.getAll(VaccineType.class, type -> true)));
        cbVaccine.setItems(vaccines);
        cbVaccine.setValidated(true);
        cbVaccine.getValidator().add(BindingUtils.toProperty(cbVaccine.getSelectionModel().selectedIndexProperty().isNotEqualTo(-1)), "A value must be selected");
    }

    @FXML
    public void onClosePressed(ActionEvent actionEvent) {
        getStageDialog().close();
    }
}
