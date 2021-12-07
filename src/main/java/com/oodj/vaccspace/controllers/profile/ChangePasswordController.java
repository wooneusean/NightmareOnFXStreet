package com.oodj.vaccspace.controllers.profile;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.Page;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangePasswordController extends BaseController implements Initializable {

    Person person = new Person();

    @FXML
    private MFXPillButton btnSaveChanges;

    @FXML
    private Label lblClose;

    @FXML
    private MFXPasswordField txtNewPassword;

    @FXML
    private MFXPasswordField txtOldPassword;

    @FXML
    private MFXPasswordField txtRepeatPassword;

    @FXML
    void onClosePressed(MouseEvent event) {
        getStageDialog().close();
        Navigator.showInDialog(lblClose.getScene().getWindow(),"view_profile", null);
    }

    @FXML
    void onSaveChangesPressed(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        Optional<ButtonType> result = Page.showDialogAndWait(
                txtNewPassword.getScene().getWindow(),
                "Changing password",
                "You are about to change your password",
                "Do you want to proceed?"
        );
        if (result.isPresent() && result.get() == ButtonType.OK) {
            person.setPassword(txtNewPassword.getPassword());
            person.save();

            getStageDialog().close();
            Navigator.showInDialog(lblClose.getScene().getWindow(),"view_profile", null);
        }
    }

    private boolean validateInput() {
        person = Global.getLoggedInUser();
        
        //Empty Fields Validation
        if (txtNewPassword.getPassword().equals("") ||
                txtOldPassword.getPassword().equals("") ||
                txtRepeatPassword.getPassword().equals("")
        ) {
            Page.showDialog(
                    txtNewPassword.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return false;
        }

        if (txtNewPassword.getPassword().length() < 8) {
            Page.showDialog(
                    txtNewPassword.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Password",
                    "Your password must have at least 8 characters!"
            );
            return false;
        }

        if (!txtRepeatPassword.getPassword().equals(txtNewPassword.getPassword())) {
            Page.showDialog(
                    txtRepeatPassword.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Repeat password is wrong",
                    "Please ensure the repeated password is correct!"
            );
            return false;
        }

        if (!txtOldPassword.getPassword().equals(person.getPassword())) {
            Page.showDialog(
                    txtOldPassword.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Wrong Password",
                    "Old Password is wrong!"
            );
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FontIcon icon = new FontIcon("fas-arrow-left");
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(18);

        lblClose.setGraphic(icon);
    }
}
