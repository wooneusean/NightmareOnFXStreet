package com.oodj.vaccspace.controllers;

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
        Citizen duplicate = TextORM.getOne(Citizen.class, data -> Objects.equals(data.get("IC"), vm.getIdentificationNumber()));

        if (duplicate != null) {
            Page.showDialog(container.getScene().getWindow(), DialogType.ERROR, "Error: Duplicate Identification Number", "The given identification number already exists!");
            return;
        }

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

class RegisterViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty repeatPassword = new SimpleStringProperty("");
    private final StringProperty phoneNumber = new SimpleStringProperty("");
    private final StringProperty identificationNumber = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");

    public RegisterViewModel(
            StringProperty name,
            StringProperty password,
            StringProperty repeatPassword,
            StringProperty phoneNumber,
            StringProperty identificationNumber,
            StringProperty email
    ) {
        this.name.bindBidirectional(name);
        this.password.bindBidirectional(password);
        this.repeatPassword.bindBidirectional(repeatPassword);
        this.phoneNumber.bindBidirectional(phoneNumber);
        this.identificationNumber.bindBidirectional(identificationNumber);
        this.email.bindBidirectional(email);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword.get();
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword.set(repeatPassword);
    }

    public StringProperty repeatPasswordProperty() {
        return repeatPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber.get();
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber.set(identificationNumber);
    }

    public StringProperty identificationNumberProperty() {
        return identificationNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }
}