package com.oodj.vaccspace.controllers.login;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.Committee;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.User;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
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
    private MFXTextField tfEmail;

    @FXML
    private MFXPasswordField tfPassword;

    @FXML
    private MFXPillButton btnLogin;

    @FXML
    private Hyperlink lnkRegister;

    @FXML
    private MFXCheckbox chkIsCommittee;

    @FXML
    void onLoginPressed(ActionEvent event) {
        User user = getUser();

        if (user != null) {
            Global.setUserId(user.getId());
            Global.setIsCommittee(vm.isCommittee());
            Navigator.navigate("dashboard");
        } else {
            Page.showDialog(
                    tfEmail.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Credentials",
                    "Username and password combination does not match any records!"
            );
        }
    }

    private User getUser() {
        Class<? extends User> toFind = vm.isCommittee() ? Committee.class : Person.class;
        return TextORM.getOne(
                toFind,
                data -> Objects.equals(data.get("email"), vm.getEmail()) &&
                        Objects.equals(data.get("password"), vm.getPassword())
        );
    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        try {
            Navigator.navigate("register");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSubmit(ActionEvent event) {
        // MFX is scuffed so this doesn't work for now
        // and will probably not work until MFX
        // is updated.
        System.out.println("Submitted");
        onLoginPressed(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vm = new LoginViewModel(
                tfEmail.textProperty(),
                tfPassword.passwordProperty(),
                chkIsCommittee.selectedProperty()
        );
    }
}