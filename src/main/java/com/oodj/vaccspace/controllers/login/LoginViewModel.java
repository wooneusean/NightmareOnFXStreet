package com.oodj.vaccspace.controllers.login;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class LoginViewModel {
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final BooleanProperty isCommittee = new SimpleBooleanProperty(false);

    public LoginViewModel(StringProperty email, StringProperty password, BooleanProperty isCommittee) {
        this.email.bindBidirectional(email);
        this.password.bindBidirectional(password);
        this.isCommittee.bindBidirectional(isCommittee);
    }

    public boolean isCommittee() {
        return isCommittee.get();
    }

    public void setIsCommittee(boolean isCommittee) {
        this.isCommittee.set(isCommittee);
    }

    public BooleanProperty isCommitteeProperty() {
        return isCommittee;
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
