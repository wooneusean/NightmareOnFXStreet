package com.oodj.vaccspace.controllers.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class LoginViewModel {
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");

    public LoginViewModel(StringProperty username, StringProperty password) {
        this.username.bindBidirectional(username);
        this.password.bindBidirectional(password);
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

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }
}
