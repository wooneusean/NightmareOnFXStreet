package com.oodj.vaccspace.controllers;

import com.oodj.vaccspace.utils.Navigator;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    RegisterControllerViewModel vm = null;

    @FXML
    private MFXPillButton btnBackToLogin;

    @FXML
    private MFXTextField tfEmail;

    @FXML
    private MFXTextField tfIdentificationNumber;

    @FXML
    private MFXTextField tfName;

    @FXML
    private MFXTextField tfPassword;

    @FXML
    private MFXTextField tfPhoneNumber;

    @FXML
    private MFXTextField tfRepeatPassword;

    @FXML
    void onBackToLoginPressed(ActionEvent event) {
        Navigator.navigate("login");
    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        System.out.println(vm.getEmail());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.vm = new RegisterControllerViewModel(
                tfName.textProperty(),
                tfPassword.textProperty(),
                tfRepeatPassword.textProperty(),
                tfPhoneNumber.textProperty(),
                tfIdentificationNumber.textProperty(),
                tfEmail.textProperty()
        );
    }
}

class RegisterControllerViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty repeatPassword = new SimpleStringProperty("");
    private final StringProperty phoneNumber = new SimpleStringProperty("");
    private final StringProperty identificationNumber = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");

    public RegisterControllerViewModel(
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