package com.oodj.vaccspace.controllers.vaccinebatches;

import com.oodj.vaccspace.models.VaccineBatch;
import javafx.scene.control.TableCell;

class AmountRemainingCell extends TableCell<VaccineBatch, String> {
    @Override
    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
            setText(null);
            setGraphic(null);
        } else {
            VaccineBatch vaccineBatch = getTableRow().getItem();
            String amountLeft = String.format(
                    "%d/%d (%d%%)",
                    vaccineBatch.getAvailableAmount(),
                    vaccineBatch.getAmount(),
                    Math.round(vaccineBatch.getPercentRemaining())
            );
            setText(amountLeft);
            setStyle("-fx-text-fill: " + getColorFromPercentage((int) vaccineBatch.getPercentRemaining()));
        }
    }

    private String getColorFromPercentage(int percentRemaining) {
        if (percentRemaining <= 100 && percentRemaining > 60) {
            return "rgb(0, 150, 0)";
        } else if (percentRemaining <= 60 && percentRemaining > 40) {
            return "rgb(227, 227, 0)";
        } else if (percentRemaining <= 40 && percentRemaining > 20) {
            return "rgb(230, 130, 0)";
        } else {
            return "rgb(190, 0, 0)";
        }
    }
}
