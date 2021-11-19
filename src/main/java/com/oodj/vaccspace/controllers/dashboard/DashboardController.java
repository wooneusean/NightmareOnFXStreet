package com.oodj.vaccspace.controllers.dashboard;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import com.oodj.vaccspace.models.Citizen;
import com.oodj.vaccspace.models.Dose;
import com.oodj.vaccspace.utils.Navigator;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import textorm.TextORM;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DashboardController implements Initializable {
    List<DashboardIconButton> iconList = Arrays.asList(
            new DashboardIconButton("fas-bars", "btnMenu", this::onBtnPress),
            new DashboardIconButton("fas-home", "btnHome", this::onBtnPress),
            new DashboardIconButton("fas-syringe", "btnVaccination", this::onBtnPress),
            new DashboardIconButton("fas-hospital", "btnVaccinationCenter", this::onBtnPress),
            new DashboardIconButton("fas-cog", "btnSettings", this::onBtnPress)
    );

    Citizen citizen = null;

    @FXML
    private MFXButton btnMenu;

    @FXML
    private MFXButton btnHome;

    @FXML
    private MFXButton btnVaccination;

    @FXML
    private MFXButton btnVaccinationCenter;

    @FXML
    private MFXButton btnSettings;

    @FXML
    private MFXPillButton btnLogout;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblVaccinationStatus;

    @FXML
    private VBox vbNavigation;

    @FXML
    private TableView<Appointment> tvAppointments;

    @FXML
    private MFXPillButton btnNewAppointment;

    @FXML
    void onBtnPress(ActionEvent event) {
        System.out.println(event.getSource());
    }

    @FXML
    void onLogoutPressed(ActionEvent event) {
        Global.setUserId(-1);
        Navigator.navigate("login");
    }

    @FXML
    void onNewAppointmentPressed(ActionEvent event) {
        Navigator.showInDialog(btnNewAppointment.getScene().getWindow(), "new_appointment", this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        citizen = TextORM.getOne(Citizen.class, data -> Integer.parseInt(data.get("id")) == Global.getUserId(), Appointment.class);

        if (citizen == null) {
            Navigator.navigate("login");
            return;
        }

        setupUI();
    }

    public void refresh() {
        citizen.include(Appointment.class);
        tvAppointments.setItems(FXCollections.observableArrayList(citizen.getAppointments()));
    }

    private void setupUI() {
        lblVaccinationStatus.setText(String.format("Vaccination Status: %s", citizen.getVaccinationStatus().getValue()));
        lblVaccinationStatus.setStyle(String.format("-fx-background-color: %s;", citizen.getVaccinationStatus().getColor()));
        lblGreeting.setText(getGreetingText(citizen.getName()));

        setupTable(citizen);

        initializeIcons();
    }

    private void setupTable(Citizen citizen) {
        TableColumn<Appointment, String> appointmentLocation = new TableColumn<>("Location");
        appointmentLocation.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getVaccinationCenter().getVaccinationCenterName()));

        TableColumn<Appointment, String> appointmentVaccine = new TableColumn<>("Vaccine");
        appointmentVaccine.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getVaccineName()));

        TableColumn<Appointment, LocalDate> appointmentDate = new TableColumn<>("Appointment Date");
        appointmentDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        TableColumn<Appointment, AppointmentStatus> appointmentStatus = new TableColumn<>("Status");
        appointmentStatus.setCellValueFactory(new PropertyValueFactory<>("appointmentStatus"));

        TableColumn<Appointment, Dose> dose = new TableColumn<>("Dose");
        dose.setCellValueFactory(new PropertyValueFactory<>("dose"));

        tvAppointments.getColumns().addAll(appointmentLocation, appointmentVaccine, appointmentDate, appointmentStatus, dose);

        // Autosize all columns
        tvAppointments.widthProperty().addListener((observableValue, number, t1) -> {
            int numCol = tvAppointments.getColumns().size();
            for (var col :
                    tvAppointments.getColumns()) {
                col.setPrefWidth((t1.doubleValue() - numCol) / numCol);
            }
        });

        if (citizen.getAppointments() == null || citizen.getAppointments().size() == 0) return;

        citizen.getAppointments().sort(Comparator.comparing(Appointment::getAppointmentDate));

        tvAppointments.setItems(FXCollections.observableArrayList(citizen.getAppointments()));

        // https://stackoverflow.com/a/26565887/4987298
        tvAppointments.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    Appointment rowData = row.getItem();
                    System.out.println(rowData.getVaccineName());
                }
            });
            return row;
        });
    }

    private String getGreetingText(String name) {
        Calendar rightNow = Calendar.getInstance();
        int timeOfDay = rightNow.get(Calendar.HOUR_OF_DAY);
        String timeGreeting;
        if (timeOfDay < 12) {
            timeGreeting = "Good morning, %s";
        } else if (timeOfDay < 16) {
            timeGreeting = "Good afternoon, %s";
        } else if (timeOfDay < 21) {
            timeGreeting = "Good evening, %s";
        } else {
            timeGreeting = "Good night, %s";
        }

        return String.format(timeGreeting, name);
    }

    private void initializeIcons() {
        for (Iterator<DashboardIconButton> iterator = iconList.iterator(); iterator.hasNext(); ) {
            DashboardIconButton fragment = iterator.next();

            vbNavigation.getChildren().add(createIcon(fragment));
//            if (iterator.hasNext()) {
//                vbNavigation.getChildren().add(createSpacer());
//            }
        }
    }

    private Region createSpacer() {
        Region region = new Region();
        VBox.setVgrow(region, Priority.ALWAYS);
        return region;
    }

    private MFXButton createIcon(DashboardIconButton iconButton) {
        FontIcon icon = new FontIcon(iconButton.getIconLiteral());
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(32);

        MFXButton button = new MFXButton("", icon);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setBackground(Background.EMPTY);
        button.setDepthLevel(DepthLevel.LEVEL2);
        button.setRippleRadius(50);

        button.setId(iconButton.getFxmlId());
        button.setOnAction(iconButton.getEventHandler());

        VBox.setVgrow(button, Priority.ALWAYS);

        return button;
    }
}

