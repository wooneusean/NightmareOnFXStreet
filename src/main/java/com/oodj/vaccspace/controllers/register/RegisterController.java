package com.oodj.vaccspace.controllers.register;

import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.Page;
import com.oodj.vaccspace.utils.ValidationHelper;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    public BorderPane container;

    RegisterViewModel vm = null;

    @FXML
    private MFXPillButton btnBackToLogin;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private Label lblID;

    @FXML
    private MFXTextField txtIdentificationNumber;

    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXPasswordField txtPassword;

    @FXML
    private MFXTextField txtPhoneNumber;

    @FXML
    private MFXPasswordField txtRepeatPassword;

    @FXML
    private MFXCheckbox cbIsNotCitizen;

    @FXML
    void onBackToLoginPressed(ActionEvent event) {
        Navigator.navigate("login");
    }

    @FXML
    void onToggleIdentification(ActionEvent event) {
        if (cbIsNotCitizen.isSelected()) {
            lblID.setText("Passport Number");
        } else {
            lblID.setText("Identification Number");
        }
    }

    @FXML
    void onRegisterPressed(ActionEvent event) {
        //Empty Fields Validation
        if (vm.getName().equals("") ||
            vm.getPassword().equals("") ||
            vm.getRepeatPassword().equals("") ||
            vm.getPhoneNumber().equals("") ||
            vm.getEmail().equals("") ||
            vm.getIdentificationNumber().equals("")
        ) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return;
        }

        // Name Validation
        if (vm.getName().matches(".*\\d.*")) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Name",
                    "The name should not contain any numbers!"
            );
            return;
        }

        //Password Validation
        if (!vm.getPassword().equals(vm.getRepeatPassword())) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Password",
                    "The passwords do not match!"
            );
            return;
        }

        if (vm.getPassword().length() < 8) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Password",
                    "Your password must have at least 8 characters!"
            );
            return;
        }

        //Phone Validation
        if (!vm.getPhoneNumber().matches("^[0-9]{4,}$")) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Phone",
                    "Phone number must only have numbers and must be more than 4 characters long!"
            );
            return;
        }

        if (vm.isNotCitizen()) {
            //Passport Number Validation
            //https://stackoverflow.com/a/40647805/4987298
            if (!vm.getIdentificationNumber().matches("^(?!^0+$)[a-zA-Z0-9]{3,20}$")) {
                Page.showDialog(
                        container.getScene().getWindow(),
                        DialogType.ERROR,
                        "Error: Invalid Passport Number",
                        "Passport number is not in a correct format!"
                );
                return;
            }
        } else {
            //Identification Number Validation
            if (!vm.getIdentificationNumber().matches("^[0-9]{12}$")) {
                Page.showDialog(
                        container.getScene().getWindow(),
                        DialogType.ERROR,
                        "Error: Invalid IC Number",
                        "Identification number must only have numbers and must have 12 digits!"
                );
                return;
            }
        }

        //Email Validation
        if (!ValidationHelper.isValidEmail(vm.getEmail())) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Email",
                    "Invalid Email Address!"
            );
            return;
        }

        //IC Duplication Validation
        Person duplicate = TextORM.getOne(
                Person.class,
                data -> Objects.equals(data.get("identificationNumber"), vm.getIdentificationNumber())
        );

        if (duplicate != null) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Duplicate Identification Number",
                    "The given identification number already exists!"
            );
            return;
        }

        //Email Duplication Validation
        duplicate = TextORM.getOne(
                Person.class,
                data -> Objects.equals(data.get("email"), vm.getEmail())
        );

        if (duplicate != null) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Duplicate Email",
                    "The given email is already registered!"
            );
            return;
        }

        Person newPerson = new Person(
                vm.getName(),
                vm.getPhoneNumber(),
                vm.getEmail(),
                vm.getPassword(),
                VaccinationStatus.NOT_REGISTERED,
                vm.getIdentificationNumber(),
                vm.isNotCitizen()
        );
        newPerson.save();

        Page.showDialog(container.getScene().getWindow(), DialogType.INFO, "Success", "Successfully registered.");
        Navigator.navigate("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.vm = new RegisterViewModel(
                txtName.textProperty(),
                txtPassword.passwordProperty(),
                txtRepeatPassword.passwordProperty(),
                txtPhoneNumber.textProperty(),
                txtIdentificationNumber.textProperty(),
                txtEmail.textProperty(),
                cbIsNotCitizen.selectedProperty()
        );
    }
}

