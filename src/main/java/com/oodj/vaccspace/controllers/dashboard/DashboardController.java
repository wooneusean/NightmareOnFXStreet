package com.oodj.vaccspace.controllers.dashboard;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.Citizen;
import com.oodj.vaccspace.utils.Navigator;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.effects.DepthLevel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import textorm.TextORM;

import java.net.URL;
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
    private MFXTableView<Appointment> tvAppointments;

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
        // Table stuff
        // https://github.com/palexdev/MaterialFX/blob/fcd13025937704043b50afc51b8b42ee8ea397ce/demo/src/main/java/io/github/palexdev/materialfx/demo/controllers/TableViewsDemoController.java#L134


        MFXTableColumn<Appointment> appointmentLocation = new MFXTableColumn<>("Location", Comparator.comparing(Appointment::getAppointmentCenterName));
        appointmentLocation.setRowCellFunction(appointment -> new MFXTableRowCell(appointment.getAppointmentCenterName()));

        MFXTableColumn<Appointment> appointmentVaccine = new MFXTableColumn<>("Vaccine", Comparator.comparing(Appointment::getVaccineName));
        appointmentVaccine.setRowCellFunction(appointment -> new MFXTableRowCell(appointment.getVaccineName()));

        MFXTableColumn<Appointment> appointmentDate = new MFXTableColumn<>("Appointment Date", Comparator.comparing(Appointment::getAppointmentDate));
        appointmentDate.setRowCellFunction(appointment -> new MFXTableRowCell(appointment.getAppointmentDate().toString()));

        MFXTableColumn<Appointment> appointmentStatus = new MFXTableColumn<>("Status", Comparator.comparing(Appointment::getAppointmentStatus));
        appointmentStatus.setRowCellFunction(appointment -> new MFXTableRowCell(appointment.getAppointmentStatus().getValue()));

        MFXTableColumn<Appointment> actions = new MFXTableColumn<>("Actions");
        actions.setRowCellFunction(appointment -> {
            MFXTableRowCell rowCell = new MFXTableRowCell("");

            MFXButton btn = new MFXButton("Show");
            btn.setBackground(new Background(new BackgroundFill(Color.rgb(100, 134, 221), new CornerRadii(3), new Insets(0))));
            btn.setTextFill(Color.WHITE);

            btn.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> System.out.println(appointment.getAppointmentDate()));
            rowCell.setLeadingGraphic(btn);
            return rowCell;
        });

        tvAppointments.getTableColumns().addAll(appointmentLocation, appointmentVaccine, appointmentDate, appointmentStatus, actions);

        if (citizen.getAppointments() == null || citizen.getAppointments().size() == 0) return;

        tvAppointments.setItems(FXCollections.observableArrayList(citizen.getAppointments()));
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

