package com.oodj.vaccspace.controllers;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.Citizen;
import com.oodj.vaccspace.utils.Navigator;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import textorm.TextORM;

import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {
    List<String> iconList = Arrays.asList("fas-bars", "fas-home", "fas-syringe", "fas-hospital", "fas-cog");

    @FXML
    private MFXPillButton btnLogout;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblVaccinationStatus;

    @FXML
    private VBox vbNavigation;

    @FXML
    void onLogoutPressed(ActionEvent event) {
        Global.setUserId(-1);
        Navigator.navigate("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Citizen citizen = TextORM.getOne(Citizen.class, data -> Integer.parseInt(data.get("id")) == Global.getUserId());

        if (citizen == null) {
            Navigator.navigate("login");
            return;
        }

        setupUI(citizen);
        initializeIcons();
    }

    private void setupUI(Citizen citizen) {
        lblVaccinationStatus.setText(String.format("Vaccination Status: %s", citizen.getVaccinationStatus().getValue()));
        lblVaccinationStatus.setStyle(String.format("-fx-background-color: %s;", citizen.getVaccinationStatus().getColor()));
        lblGreeting.setText(getGreetingText(citizen.getName()));
    }

    private String getGreetingText(String name) {
        Calendar rightNow = Calendar.getInstance();
        int timeOfDay = rightNow.get(Calendar.HOUR_OF_DAY);
        String timeGreeting = "";
        if (timeOfDay < 12) {
            timeGreeting = "Good morning, %s";
        } else if (timeOfDay < 16) {
            timeGreeting = "Good afternoon, %";
        } else if (timeOfDay < 21) {
            timeGreeting = "Good evening, %s";
        } else {
            timeGreeting = "Good night, %s";
        }

        return String.format(timeGreeting, name);
    }

    private void initializeIcons() {
        for (Iterator<String> iterator = iconList.iterator(); iterator.hasNext(); ) {
            String fragment = iterator.next();

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

    private MFXButton createIcon(String iconDescription) {
        FontIcon icon = new FontIcon(iconDescription);
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(32);

        MFXButton button = new MFXButton("", icon);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setBackground(Background.EMPTY);
        button.setDepthLevel(DepthLevel.LEVEL2);

        VBox.setVgrow(button, Priority.ALWAYS);

        return button;
    }
}

class DashboardViewModel {

}