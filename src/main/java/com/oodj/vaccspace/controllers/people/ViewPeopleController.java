package com.oodj.vaccspace.controllers.people;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import textorm.TextORM;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewPeopleController extends BaseController implements Initializable {

    PeopleController peopleController;

    Person person;

    @FXML
    private MFXPillButton btnCancel;

    @FXML
    private MFXPillButton btnSaveChanges;

    @FXML
    private MFXPillButton btnEditPerson;

    @FXML
    private MFXPillButton btnViewAppointments;

    @FXML
    private MFXPillButton btnDelete;

    @FXML
    private MFXCheckbox chkIsNonCitizen;

    @FXML
    private ComboBox<VaccinationStatus> cmbVaccinationStatus;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXTextField txtPhoneNumber;

    @FXML
    void onEditPersonPressed(ActionEvent event) {
        btnEditPerson.setManaged(false);
        btnSaveChanges.setManaged(true);

        txtName.setDisable(false);
        txtPhoneNumber.setDisable(false);
        txtEmail.setDisable(false);
        cmbVaccinationStatus.setDisable(false);
        chkIsNonCitizen.setDisable(false);

        txtName.setStyle("-fx-opacity: 0.99");
        txtPhoneNumber.setStyle("-fx-opacity: 0.99");
        txtEmail.setStyle("-fx-opacity: 0.99");
    }

    @FXML
    public void onViewAppointmentPressed(ActionEvent actionEvent) {
        Navigator.navigateInContainer("home", Global.getDashboardReference().getVbxContent(), person);
        getStageDialog().close();
    }

    @FXML
    void onDeletePressed(ActionEvent event) {
        Optional<ButtonType> result = Page.showDialogAndWait(
                txtEmail.getScene().getWindow(),
                "Deletion of User",
                "You are about to delete the user '" + person.getName() + "'.",
                "Do you want to proceed?"
        );
        if (result.isPresent() && result.get() == ButtonType.OK) {
            person.delete();

        }

        ((PeopleController) getUserData()).refresh();
        getStageDialog().close();
    }

    @FXML
    void onSaveChangesPressed(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        person.setName(txtName.getText());
        person.setPhone(txtPhoneNumber.getText());
        person.setEmail(txtEmail.getText());
        person.setNonCitizen(chkIsNonCitizen.isSelected());
        person.setVaccinationStatus(cmbVaccinationStatus.getSelectionModel().getSelectedItem());
        person.save();

        ((PeopleController) getUserData()).refresh();
        getStageDialog().close();
    }

    private boolean validateInput() {
        //Empty Fields Validation
        if (txtName.getText().equals("") ||
            txtEmail.getText().equals("") ||
            txtPhoneNumber.getText().equals("") ||
            cmbVaccinationStatus.getSelectionModel().getSelectedItem() == null
        ) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Empty fields",
                    "All fields must be filled in!"
            );
            return false;
        }

        // Name Validation
        if (txtName.getText().matches(".*\\d.*")) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Invalid Name",
                    "The name should not contain any numbers!"
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

        //Identification Number Validation
        if (cmbVaccinationStatus.getSelectionModel().getSelectedItem() == null) {
            Page.showDialog(
                    txtName.getScene().getWindow(),
                    DialogType.ERROR,
                    "Error: Vaccination Status Not Chosen",
                    "Please select a valid vaccination status!"
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

    @FXML
    void onCancelPressed(ActionEvent event) {
        getStageDialog().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbVaccinationStatus.getItems().setAll(VaccinationStatus.values());
    }

    @Override
    public void onLoaded() {
        btnSaveChanges.setManaged(false);

        peopleController = (PeopleController) getUserData();
        person = peopleController.getSelectedPerson();

        txtName.setText(person.getName());
        txtPhoneNumber.setText(person.getPhone());
        txtEmail.setText(person.getEmail());
        chkIsNonCitizen.setSelected(person.isNonCitizen());

        cmbVaccinationStatus.getSelectionModel().select(person.getVaccinationStatus());
        cmbVaccinationStatus.setConverter(new StringConverter<>() {
            @Override
            public String toString(VaccinationStatus vaccinationStatus) {
                return vaccinationStatus.getValue();
            }

            @Override
            public VaccinationStatus fromString(String s) {
                return VaccinationStatus.valueOf(s);
            }
        });
    }


}
