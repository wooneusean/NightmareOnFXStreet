package com.oodj.vaccspace.controllers.dashboard;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.utils.Logging;
import com.oodj.vaccspace.utils.Navigator;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    boolean isNavigationMinimized = false;

    @FXML
    private BorderPane bpDashboard;

    @FXML
    private MFXButton btnMenu;

    @FXML
    private MFXPillButton btnAccount;

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
    private VBox vbxContent;

    List<DashboardIconButton> iconList = Arrays.asList(
            new DashboardIconButton(
                    "fas-home",
                    "btnHome",
                    actionEvent -> onNavBtnPress(actionEvent, Global.isCommittee() ? "appointments" : "home"),
                    false
            ),
            new DashboardIconButton(
                    "fas-users",
                    "btnPeople",
                    actionEvent -> onNavBtnPress(actionEvent, "people"),
                    true
            ),
            new DashboardIconButton(
                    "fas-syringe",
                    "btnVaccination",
                    actionEvent -> onNavBtnPress(actionEvent, "vaccines"),
                    false
            ),
            new DashboardIconButton(
                    "fas-boxes",
                    "btnBatches",
                    actionEvent -> onNavBtnPress(actionEvent, "vaccine_batches"),
                    true
            ),
            new DashboardIconButton(
                    "fas-hospital",
                    "btnVaccinationCenter",
                    actionEvent -> onNavBtnPress(actionEvent, "vaccine_centers"),
                    false
            ),
            new DashboardIconButton(
                    "fas-chart-bar",
                    "btnReports",
                    actionEvent -> onNavBtnPress(actionEvent, "reports"),
                    true
            )
    );

    @FXML
    private VBox vbxNavigation;

    @FXML
    void onNavBtnPress(ActionEvent event, String route) {
        Navigator.navigateInContainer(route, vbxContent, this);
    }

    @FXML
    void onAccountPressed(ActionEvent event) {
        Navigator.showInDialog(btnAccount.getScene().getWindow(),"view_profile", null);
    }

    @FXML
    void onLogoutPressed(ActionEvent event) {
        Logging.write(String.format("Logout {user_id: %d, committee: %b}", Global.getUserId(), Global.isCommittee()));
        Global.setUserId(-1);
        Navigator.navigate("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Navigator.navigateInContainer(Global.isCommittee() ? "appointments" : "home", vbxContent, null);

        if(Global.isCommittee()) {
            btnAccount.setManaged(false);
        }

        initializeIcons();
        Global.setDashboardReference(this);
    }

    private void initializeIcons() {
        for (DashboardIconButton icon : iconList) {
            if (!icon.isRequiresCommittee() || Global.isCommittee()) {
                vbxNavigation.getChildren().add(createIcon(icon));
            }
        }
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

    public VBox getVbxContent() {
        return vbxContent;
    }
}

