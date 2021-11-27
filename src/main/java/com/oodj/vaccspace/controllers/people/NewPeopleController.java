package com.oodj.vaccspace.controllers.people;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
import com.oodj.vaccspace.utils.Page;
import com.oodj.vaccspace.utils.ValidationHelper;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NewPeopleController extends BaseController implements Initializable {

    @FXML
    private MFXPillButton btnAddPerson;

    @FXML
    private MFXPillButton btnCancel;

    @FXML
    private MFXCheckbox chkIsNonCitizen;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private MFXTextField txtIdentificationNumber;

    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXTextField txtPhoneNumber;

    @FXML
    void onAddPersonPressed(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        Person newPerson = new Person(
                txtName.getText(),
                txtPhoneNumber.getText(),
                txtEmail.getText(),
                null,
                VaccinationStatus.NOT_REGISTERED,
                txtIdentificationNumber.getText(),
                chkIsNonCitizen.isSelected()
        );
        newPerson.save();

        ((PeopleController) getUserData()).refresh();
        getStageDialog().close();
    }

    private boolean validateInput() {
        //Empty Fields Validation
        if (txtName.getText().equals("") ||
            txtIdentificationNumber.getText().equals("") ||
            txtEmail.getText().equals("") ||
            txtPhoneNumber.getText().equals("")
        ) {
            Page.showDialog(
                    btnAddPerson.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return false;
        }

        // Name Validation
        if (txtName.getText().matches(".*\\d.*")) {
            Page.showDialog(
                    btnAddPerson.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Name",
                    "The name should not contain any numbers!"
            );
            return false;
        }

        //Phone Validation
        if (!txtPhoneNumber.getText().matches("^[0-9]{4,}$")) {
            Page.showDialog(
                    btnAddPerson.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Phone",
                    "Phone number must only have numbers and must be more than 4 characters long!"
            );
            return false;
        }

        //Identification Number Validation
        if (!txtIdentificationNumber.getText().matches("^[0-9]*$")) {
            Page.showDialog(
                    btnAddPerson.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid IC Number",
                    "Identification number must only have numbers!"
            );
            return false;
        }

        //Email Validation
        if (!ValidationHelper.isValidEmail(txtEmail.getText())) {
            Page.showDialog(
                    btnAddPerson.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Email",
                    "Invalid Email Address!"
            );
            return false;
        }

        //IC Duplication Validation
        Person duplicate = TextORM.getOne(
                Person.class,
                data -> Objects.equals(data.get("identificationNumber"), txtIdentificationNumber.getText())
        );

        if (duplicate != null) {
            Page.showDialog(
                    btnAddPerson.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Duplicate Identification Number",
                    "The given identification number already exists!"
            );
            return false;
        }

        return true;
    }

    @FXML
    void onCancelPressed(ActionEvent event) {
        getStageDialog().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
