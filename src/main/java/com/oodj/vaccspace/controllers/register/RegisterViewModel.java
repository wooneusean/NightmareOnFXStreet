package com.oodj.vaccspace.controllers.register;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class RegisterViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty repeatPassword = new SimpleStringProperty("");
    private final StringProperty phoneNumber = new SimpleStringProperty("");
    private final StringProperty identificationNumber = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final BooleanProperty isNotCitizen = new SimpleBooleanProperty(false);

    public RegisterViewModel(
            StringProperty name,
            StringProperty password,
            StringProperty repeatPassword,
            StringProperty phoneNumber,
            StringProperty identificationNumber,
            StringProperty email,
            BooleanProperty isNotCitizen
    ) {
        this.name.bindBidirectional(name);
        this.password.bindBidirectional(password);
        this.repeatPassword.bindBidirectional(repeatPassword);
        this.phoneNumber.bindBidirectional(phoneNumber);
        this.identificationNumber.bindBidirectional(identificationNumber);
        this.email.bindBidirectional(email);
        this.isNotCitizen.bindBidirectional(isNotCitizen);
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

    public boolean isNotCitizen() {
        return isNotCitizen.get();
    }

    public void setIsNotCitizen(boolean isNotCitizen) {
        this.isNotCitizen.set(isNotCitizen);
    }

    public BooleanProperty isNotCitizenProperty() {
        return isNotCitizen;
    }
}
