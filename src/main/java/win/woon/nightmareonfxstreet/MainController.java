package win.woon.nightmareonfxstreet;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    MainControllerViewModel vm = new MainControllerViewModel();

    // For binding directly in FXML file, a Property must be created with its setter and getters as such
    // However this method only allows Unidirectional data binding.
    private final StringProperty textValue = new SimpleStringProperty("");

    public String getTextValue() {
        return textValue.get();
    }

    public StringProperty textValueProperty() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue.set(textValue);
    }

    @FXML
    private TextField tfOtherText;

    // For binding with a ViewModel, simple perform the biding in `initialize()`
    @FXML
    private TextField tfText;

    @FXML
    private TextField tfName;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnAddName;

    @FXML
    private ComboBox<String> cbNames;

    ObservableList<String> names = FXCollections.observableArrayList("John", "Mary", "Jean");

    public ObservableList<String> getNames() {
        return names;
    }

    public void setNames(ObservableList<String> names) {
        this.names = names;
    }


    @FXML
    void addName(ActionEvent event) {
        String name = tfName.getText();
        if (names.contains(name) || name.equals("")) return;

        names.add(name);
        tfName.clear();
    }

    @FXML
    void printData(ActionEvent event) {
        System.out.println(vm.getUsername());
        System.out.println(vm.getPassword());
        System.out.println(getTextValue());
        setTextValue("Text Value Changed");
        vm.setUsername("Username Changed");
        vm.setPassword("Password Changed");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Binding properties using the textProperty() of the textfield
        tfName.textProperty().bindBidirectional(vm.passwordProperty());

        // Using FluentAPI
        Bindings.bindBidirectional(tfText.textProperty(), vm.usernameProperty());

        cbNames.setItems(names);
    }
}

class MainControllerViewModel {

    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");

    public MainControllerViewModel() {
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}

class ApplicantInfo {
    String applicantName;
    String applicantJob;
    Integer applicantAge;

    public ApplicantInfo(String applicantName, String applicantJob, Integer applicantAge) {
        this.applicantName = applicantName;
        this.applicantJob = applicantJob;
        this.applicantAge = applicantAge;
    }

    @Override
    public String toString() {
        return "ApplicantInfo{" +
                "applicantName='" + applicantName + '\'' +
                ", applicantJob='" + applicantJob + '\'' +
                ", applicantAge=" + applicantAge +
                '}';
    }
}