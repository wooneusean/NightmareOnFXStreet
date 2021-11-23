package com.oodj.vaccspace.controllers.people;

import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
import com.oodj.vaccspace.utils.ColorHelper;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public class VaccinationStatusIndicatorCell extends TableCell<Person, VaccinationStatus> {
    @Override
    protected void updateItem(VaccinationStatus centerStatus, boolean empty) {
        super.updateItem(centerStatus, empty);

        if (!empty) {
            Label label = new Label(centerStatus.getValue());
            label.setStyle("-fx-border-color: " +
                           ColorHelper.toRGBAString(getStatusColor(centerStatus).saturate()) +
                           "; -fx-background-color: " +
                           ColorHelper.toRGBAString(getStatusBackground(centerStatus)) +
                           "; -fx-background-radius: 5 ;-fx-border-radius: 5");
            label.setPadding(new Insets(4, 8, 4, 8));

            FontIcon statusDot = new FontIcon(centerStatus.getIcon());
            statusDot.setIconColor(getStatusColor(centerStatus).saturate());

            label.setGraphic(statusDot);
            setGraphic(label);
        } else {
            setGraphic(null);
        }
    }

    protected Color getStatusColor(VaccinationStatus status) {
        return ColorHelper.hexToRGB(status.getColor());
    }

    protected Color getStatusBackground(VaccinationStatus status) {
        Color originalColor = ColorHelper.hexToRGB(status.getColor());
        return new Color(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), 0.1);
    }
}
