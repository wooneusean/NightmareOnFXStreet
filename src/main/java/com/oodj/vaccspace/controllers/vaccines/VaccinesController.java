package com.oodj.vaccspace.controllers.vaccines;

import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Table;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import textorm.TextORM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VaccinesController implements Initializable {

    @FXML
    private TableView<VaccineType> tblVaccines;

    @FXML
    private MFXTextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<VaccineType, String> vaccineNameColumn = new TableColumn<>("Vaccine Name");
        vaccineNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccineName"));

        TableColumn<VaccineType, Integer> vaccineDoseColumn = new TableColumn<>("Doses Needed");
        vaccineDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dosesNeeded"));

        List<VaccineType> vaccineTypes = TextORM.getAll(VaccineType.class, hashMap -> true);

        tblVaccines.getColumns().addAll(vaccineNameColumn, vaccineDoseColumn);

        Table.autoSizeColumns(tblVaccines);

        tblVaccines.setItems(FXCollections.observableArrayList(vaccineTypes));
    }
}

