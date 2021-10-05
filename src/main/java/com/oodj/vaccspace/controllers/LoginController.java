package com.oodj.vaccspace.controllers;

import com.oodj.vaccspace.utils.Navigator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    LoginControllerViewModel vm = new LoginControllerViewModel();

    @FXML
    private MFXTextField tfUsername;

    @FXML
    private MFXPasswordField tfPassword;

    @FXML
    private MFXButton btnLogin;

    @FXML
    private MFXButton btnRegister;


    @FXML
    void onLoginPressed(ActionEvent event) {

    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        try {
            Navigator.openInNewWindow("register");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfUsername.textProperty().bindBidirectional(vm.usernameProperty());
        tfPassword.passwordProperty().bindBidirectional(vm.passwordProperty());
    }
}

class LoginControllerViewModel {
    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");

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

