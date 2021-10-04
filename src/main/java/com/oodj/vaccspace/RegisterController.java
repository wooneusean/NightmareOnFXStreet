package com.oodj.vaccspace;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private MFXButton btnLogin;

    @FXML
    void onBtnLoginPressed(ActionEvent event) {
        Navigator.navigate("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
