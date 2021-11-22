package com.oodj.vaccspace.controllers.vaccinecenters;

import com.oodj.vaccspace.models.CenterStatus;
import com.oodj.vaccspace.models.VaccinationCenter;
import com.oodj.vaccspace.utils.ColorHelper;
import com.oodj.vaccspace.utils.Table;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import textorm.TextORM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VaccineCentersController implements Initializable {

    @FXML
    private TableView<VaccinationCenter> tblVaccineCenters;

    @FXML
    private MFXTextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<VaccinationCenter, String> vaccineCenterNameColumn = new TableColumn<>("Vaccine Center Name");
        vaccineCenterNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationCenterName"));

        TableColumn<VaccinationCenter, CenterStatus> vaccineCenterStatusColumn = new TableColumn<>("Status");
        vaccineCenterStatusColumn.setCellValueFactory(new PropertyValueFactory<>("centerStatus"));
        vaccineCenterStatusColumn.setCellFactory(statusColumn -> new CenterStatusIndicator());

        List<VaccinationCenter> vaccineTypes = TextORM.getAll(VaccinationCenter.class, hashMap -> true);

        tblVaccineCenters.getColumns().addAll(vaccineCenterNameColumn, vaccineCenterStatusColumn);

        Table.autoSizeColumns(tblVaccineCenters);

        tblVaccineCenters.setItems(FXCollections.observableArrayList(vaccineTypes));
    }
}

