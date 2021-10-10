package com.oodj.vaccspace.controllers.login;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.Citizen;
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
import javafx.scene.control.Hyperlink;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    LoginViewModel vm = null;

    @FXML
    private MFXTextField tfUsername;

    @FXML
    private MFXPasswordField tfPassword;

    @FXML
    private MFXPillButton btnLogin;

    @FXML
    private Hyperlink lnkRegister;

    @FXML
    void onLoginPressed(ActionEvent event) {
        Citizen citizen = TextORM.getOne(Citizen.class, data -> Objects.equals(data.get("name"), vm.getUsername()) && Objects.equals(data.get("password"), vm.getPassword()));

        if (citizen != null) {
            Global.setUserId(citizen.getId());
            Navigator.navigate("dashboard");
        } else {
            Page.showDialog(tfUsername.getScene().getWindow(), DialogType.ERROR, "Error: Invalid Credentials", "Username and password combination does not match any records!");
        }
    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        try {
            Navigator.navigate("register");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vm = new LoginViewModel(tfUsername.textProperty(), tfPassword.passwordProperty());
    }
}

