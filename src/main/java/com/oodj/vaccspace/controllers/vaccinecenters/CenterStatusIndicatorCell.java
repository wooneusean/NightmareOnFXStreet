package com.oodj.vaccspace.controllers.vaccinecenters;

import com.oodj.vaccspace.models.CenterStatus;
import com.oodj.vaccspace.models.VaccinationCenter;
import com.oodj.vaccspace.utils.ColorHelper;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

class CenterStatusIndicatorCell extends TableCell<VaccinationCenter, CenterStatus> {
    @Override
    protected void updateItem(CenterStatus centerStatus, boolean empty) {
        super.updateItem(centerStatus, empty);

        if (!empty) {
            Label label = new Label(centerStatus.getValue());
            label.setStyle("-fx-border-color: " +
                           ColorHelper.toRGBAString(getStatusColor(centerStatus)) +
                           "; -fx-background-color: " +
                           ColorHelper.toRGBAString(getStatusBackground(centerStatus)) +
                           "; -fx-background-radius: 5 ;-fx-border-radius: 5");
            label.setPadding(new Insets(4, 8, 4, 8));

            FontIcon statusDot = new FontIcon(getStatusIcon(centerStatus));
            statusDot.setIconColor(getStatusColor(centerStatus));

            label.setGraphic(statusDot);
            setGraphic(label);
        }
    }

    protected String getStatusIcon(CenterStatus status) {
        switch (status) {
            case OPEN -> {
                return "far-check-circle";
            }
            case CLOSED -> {
                return "far-times-circle";
            }
            case CLOSED_COVID -> {
                return "fas-exclamation-circle";
            }
            default -> {
                return "fas-question-circle";
            }
        }
    }

    protected Color getStatusColor(CenterStatus status) {
        switch (status) {
            case OPEN -> {
                return new Color(0, 1, 0, 1);
            }
            case CLOSED -> {
                return new Color(1, 0, 0, 1);
            }
            case CLOSED_COVID -> {
                return new Color(1, 1, 0, 1);
            }
            default -> {
                return new Color(0, 0, 0, 1);
            }
        }
    }

    protected Color getStatusBackground(CenterStatus status) {
        switch (status) {
            case OPEN -> {
                return new Color(0, 0.6, 0, 0.1);
            }
            case CLOSED -> {
                return new Color(0.9, 0, 0, 0.1);
            }
            case CLOSED_COVID -> {
                return new Color(0.9, 0.9, 0, 0.1);
            }
            default -> {
                return new Color(0, 0, 0, 0.1);
            }
        }
    }
}
