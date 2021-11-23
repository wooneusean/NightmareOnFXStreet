package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.models.Appointment;
import com.oodj.vaccspace.models.AppointmentStatus;
import com.oodj.vaccspace.utils.ColorHelper;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public class AppointmentStatusIndicatorCell extends TableCell<Appointment, AppointmentStatus> {
    @Override
    protected void updateItem(AppointmentStatus appointmentStatus, boolean empty) {
        super.updateItem(appointmentStatus, empty);

        if (!empty) {
            Label label = new Label(appointmentStatus.getValue());
            label.setStyle("-fx-border-color: " +
                           ColorHelper.toRGBAString(getStatusColor(appointmentStatus)) +
                           "; -fx-background-color: " +
                           ColorHelper.toRGBAString(getStatusBackground(appointmentStatus)) +
                           "; -fx-background-radius: 5 ;-fx-border-radius: 5");
            label.setPadding(new Insets(4, 8, 4, 8));

            FontIcon statusDot = new FontIcon(appointmentStatus.getIcon());
            statusDot.setIconColor(getStatusColor(appointmentStatus));

            label.setGraphic(statusDot);
            setGraphic(label);
        } else {
            setGraphic(null);
        }
    }

    protected Color getStatusColor(AppointmentStatus status) {
        return ColorHelper.hexToRGB(status.getColor());
    }

    protected Color getStatusBackground(AppointmentStatus status) {
        Color originalColor = ColorHelper.hexToRGB(status.getColor());
        return new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), 0.1);
    }
}
