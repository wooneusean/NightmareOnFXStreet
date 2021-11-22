package com.oodj.vaccspace.utils;

import javafx.scene.control.TableView;

public class Table {
    public static void autoSizeColumns(TableView<?> table) {
        table.widthProperty().addListener((observableValue, number, t1) -> {
            int numCol = table.getColumns().size();
            for (var col :
                    table.getColumns()) {
                col.setPrefWidth((t1.doubleValue() - numCol) / numCol);
            }
        });
    }
}
