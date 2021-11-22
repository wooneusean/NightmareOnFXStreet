package com.oodj.vaccspace.controllers.register;

import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    @FXML
    public BorderPane container;

    RegisterViewModel vm = null;

    @FXML
    private MFXPillButton btnBackToLogin;

    @FXML
    private MFXTextField tfEmail;

    @FXML
    private Label lblID;

    @FXML
    private MFXTextField tfIdentificationNumber;

    @FXML
    private MFXTextField tfName;

    @FXML
    private MFXPasswordField tfPassword;

    @FXML
    private MFXTextField tfPhoneNumber;

    @FXML
    private MFXPasswordField tfRepeatPassword;

    @FXML
    private MFXCheckbox cbIsNotCitizen;

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

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
        if (!vm.getPhoneNumber().matches("^[0-9]*$")) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Phone",
                    "Phone number must only have numbers!"
            );
            return;
        }

        //Identification Number Validation
        if (!vm.getIdentificationNumber().matches("^[0-9]*$")) {
            Page.showDialog(
                    container.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid IC Number",
                    "Identification number must only have numbers!"
            );
            return;
        }

        //Email Validation
        if (!validateEmail(vm.getEmail())) {
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
                tfName.textProperty(),
                tfPassword.passwordProperty(),
                tfRepeatPassword.passwordProperty(),
                tfPhoneNumber.textProperty(),
                tfIdentificationNumber.textProperty(),
                tfEmail.textProperty(),
                cbIsNotCitizen.selectedProperty()
        );
    }
}

