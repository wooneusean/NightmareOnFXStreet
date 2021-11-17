package com.oodj.vaccspace.controllers.register;

import com.oodj.vaccspace.models.Citizen;
import com.oodj.vaccspace.models.VaccinationStatus;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    public BorderPane container;

    RegisterViewModel vm = null;

    @FXML
    private MFXPillButton btnBackToLogin;

    @FXML
    private MFXTextField tfEmail;

    @FXML
    private MFXTextField tfIdentificationNumber;

    @FXML
    private MFXTextField tfName;

    @FXML
    private MFXPasswordField tfPassword;

    @FXML
    private MFXTextField tfPhoneNumber;

    @FXML
    private MFXPasswordField tfRepeatPassword;

    @FXML
    void onBackToLoginPressed(ActionEvent event) {
        Navigator.navigate("login");
    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        //Empty Fields Validation
        if (vm.getName().equals("") || vm.getPassword().equals("") || vm.getRepeatPassword().equals("") || vm.getPhoneNumber().equals("") || vm.getEmail().equals("") || vm.getIdentificationNumber().equals("")) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Empty fields", "All fields must be filled in!");
            return;
        }

        // Name Validation
        if (vm.getName().matches(".*\\d.*")) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Invalid Name", "The name should not contain any numbers!");
            return;
        }

        //Password Validation
        if (!vm.getPassword().equals(vm.getRepeatPassword())) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Invalid Password", "The passwords do not match!");
            return;
        }

        if (vm.getPassword().length() < 8) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Invalid Password", "Your password must have at least 8 characters!");
            return;
        }

        //Phone Validation
        if (!vm.getPhoneNumber().matches("^[0-9]*$")) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Invalid Phone", "Phone number must only have numbers!");
            return;
        }

        //Identification Number Validation
        if (!vm.getIdentificationNumber().matches("^[0-9]*$")) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Invalid IC Number", "Identification number must only have numbers!");
            return;
        }

        //Email Validation
        if (!vm.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Invalid Email", "Invalid Email Address!");
            return;
        }

        //IC Duplication Validation
        Citizen duplicate = TextORM.getOne(Citizen.class, data -> Objects.equals(data.get("IC"), vm.getIdentificationNumber()));

        if (duplicate != null) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Duplicate Identification Number", "The given identification number already exists!");
            return;
        }

        //Success case
        Citizen newCitizen = new Citizen(vm.getName(), vm.getPhoneNumber(), vm.getEmail(), vm.getPassword(), VaccinationStatus.NOT_REGISTERED, vm.getIdentificationNumber());
        newCitizen.save();
        Page.showDialog(container.getScene().getWindow(), DialogType.INFO, "Success","Successfully registered.");
        Navigator.navigate("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.vm = new RegisterViewModel(
                tfName.textProperty(),
                tfPassword.passwordProperty(),
                tfRepeatPassword.passwordProperty(),
                tfPhoneNumber.textProperty(),
                tfIdentificationNumber.textProperty(),
                tfEmail.textProperty()
        );
    }
}

