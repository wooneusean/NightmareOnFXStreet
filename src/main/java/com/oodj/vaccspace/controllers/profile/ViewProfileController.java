package com.oodj.vaccspace.controllers.profile;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.Page;
import com.oodj.vaccspace.utils.ValidationHelper;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
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
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewProfileController extends BaseController implements Initializable {

    Person person = new Person();

    @FXML
    private MFXPillButton btnChangePassword;

    @FXML
    private MFXPillButton btnDelete;

    @FXML
    private MFXPillButton btnEditPerson;

    @FXML
    private MFXPillButton btnSaveChanges;

    @FXML
    private MFXCheckbox chkIsNonCitizen;

    @FXML
    private Label lblClose;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXTextField txtPhoneNumber;

    @FXML
    void onClosePressed(MouseEvent event) {
        getStageDialog().close();
    }

    @FXML
    void onChangePasswordPressed(ActionEvent event) {
        Navigator.showInDialog(btnChangePassword.getScene().getWindow(),"change_password", null);
    }

    @FXML
    void onEditPersonPressed(ActionEvent event) {
        btnEditPerson.setManaged(false);
        btnSaveChanges.setManaged(true);

        txtPhoneNumber.setDisable(false);
        txtEmail.setDisable(false);
        chkIsNonCitizen.setDisable(false);

        txtPhoneNumber.setStyle("-fx-opacity: 0.99");
        txtEmail.setStyle("-fx-opacity: 0.99");
    }

    @FXML
    void onSaveChangesPressed(ActionEvent event) {
        if (!validateInput()) {
            return;
        }


        Optional<ButtonType> result = Page.showDialogAndWait(
                txtEmail.getScene().getWindow(),
                "Edit Profile",
                "You are about to edit your profile details",
                "Do you want to proceed?"
        );
        if (result.isPresent() && result.get() == ButtonType.OK) {
            person.setPhone(txtPhoneNumber.getText());
            person.setEmail(txtEmail.getText());
            person.setNonCitizen(chkIsNonCitizen.isSelected());
            person.save();

            getStageDialog().close();
        }
    }

    private boolean validateInput() {
        //Empty Fields Validation
        if (txtEmail.getText().equals("") ||
                txtPhoneNumber.getText().equals("")
        ) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return false;
        }

        //Phone Validation
        if (!txtPhoneNumber.getText().matches("^[0-9]{4,}$")) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Phone",
                    "Phone number must only have numbers!"
            );
            return false;
        }

        //Email Validation
        if (!ValidationHelper.isValidEmail(txtEmail.getText())) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Email",
                    "Invalid Email Address!"
            );
            return false;
        }

        //Email Duplication Validation
        Person duplicate = TextORM.getOne(
                Person.class,
                data -> Objects.equals(data.get("email"), txtEmail.getText()) &&
                        Integer.parseInt(data.get("id")) != person.getId()
        );

        if (duplicate != null) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Duplicate Email",
                    "The given email is already registered!"
            );
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FontIcon icon = new FontIcon("fas-times");
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(18);

        lblClose.setGraphic(icon);
    }

    @Override
    public void onLoaded() {
        btnSaveChanges.setManaged(false);

        person = Global.getLoggedInUser();

        txtName.setText(person.getName());
        txtPhoneNumber.setText(person.getPhone());
        txtEmail.setText(person.getEmail());
        chkIsNonCitizen.setSelected(person.isNonCitizen());
    }
}
