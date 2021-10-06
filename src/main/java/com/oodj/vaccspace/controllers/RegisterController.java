package com.oodj.vaccspace.controllers;

import com.oodj.vaccspace.utils.Navigator;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
