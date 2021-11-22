package com.oodj.vaccspace.controllers.vaccines;

import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Table;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import textorm.TextORM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VaccinesController implements Initializable {
    VaccinesViewModel vm = new VaccinesViewModel();

    FilteredList<VaccineType> filteredList;

    @FXML
    private TableView<VaccineType> tblVaccines;

    @FXML
    private MFXTextField txtSearch;

    @FXML
    void onSearchChanged(KeyEvent event) {
        if (!vm.getSearch().isBlank()) {
            filteredList.setPredicate(
                    vaccineType -> vaccineType.getVaccineName()
                                              .toLowerCase()
                                              .contains(vm.getSearch().toLowerCase()) ||
                                   String.valueOf(vaccineType.getDosesNeeded())
                                         .equals(vm.getSearch().toLowerCase()));
        } else {
            filteredList.setPredicate(vaccineType -> true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vm.searchProperty().bindBidirectional(txtSearch.textProperty());

        TableColumn<VaccineType, String> vaccineNameColumn = new TableColumn<>("Vaccine Name");
        vaccineNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccineName"));

        TableColumn<VaccineType, Integer> vaccineDoseColumn = new TableColumn<>("Doses Needed");
        vaccineDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dosesNeeded"));

        List<VaccineType> vaccineTypes = TextORM.getAll(VaccineType.class, hashMap -> true);

        tblVaccines.getColumns().addAll(vaccineNameColumn, vaccineDoseColumn);

        tblVaccines.setRowFactory(tableView -> {
            TableRow<VaccineType> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    VaccineType vaccineType = row.getItem();
                    System.out.println(vaccineType.getVaccineName());
                }
            });
            return row;
        });

        Table.autoSizeColumns(tblVaccines);

        filteredList = new FilteredList<>(FXCollections.observableArrayList(vaccineTypes));

        tblVaccines.setItems(filteredList);
    }
}

