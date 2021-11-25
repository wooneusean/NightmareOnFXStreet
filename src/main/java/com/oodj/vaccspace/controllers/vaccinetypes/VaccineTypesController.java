package com.oodj.vaccspace.controllers.vaccinetypes;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.StringHelper;
import com.oodj.vaccspace.utils.TableHelper;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import java.util.function.Predicate;

public class VaccineTypesController implements Initializable {
    VaccineTypesViewModel vm = new VaccineTypesViewModel();

    ObservableList<VaccineType> masterData;

    FilteredList<VaccineType> filteredData;

    SortedList<VaccineType> sortableData;

    VaccineType selectedVaccineType;

    @FXML
    private TableView<VaccineType> tblVaccineTypes;

    @FXML
    private MFXTextField txtSearch;

    @FXML
    private MFXPillButton btnAddVaccineType;

    @FXML
    void onAddVaccineTypePressed(ActionEvent event) {
        Navigator.showInDialog(btnAddVaccineType.getScene().getWindow(), "new_vaccine_center", this);
    }

    @FXML
    void onSearchChanged(KeyEvent event) {

    }

    private Predicate<VaccineType> getFilterVaccineTypePredicate() {
        return vaccineType -> {
            return StringHelper.containsIgnoreCase(vaccineType.getVaccineName(), vm.getSearch().toLowerCase()) ||
                   String.valueOf(vaccineType.getDosesNeeded()).equals(vm.getSearch().toLowerCase());
        };
    }

    public void refresh() {
        List<VaccineType> vaccineTypes = TextORM.getAll(VaccineType.class, hashMap -> true);

        masterData = FXCollections.observableArrayList(vaccineTypes);
        filteredData = new FilteredList<>(masterData);
        sortableData = new SortedList<>(filteredData);

        tblVaccineTypes.setItems(sortableData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!Global.isCommittee()) {
            btnAddVaccineType.setManaged(false);
        }

        vm.searchProperty().bindBidirectional(txtSearch.textProperty());

        vm.searchProperty().addListener((observableValue, s, t1) -> {
            filteredData.setPredicate(getFilterVaccineTypePredicate());
        });

        TableColumn<VaccineType, String> vaccineNameColumn = new TableColumn<>("Vaccine Name");
        vaccineNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccineName"));

        TableColumn<VaccineType, String> ManufacturingCompanyColumn = new TableColumn<>("Manufacturing Company");
        ManufacturingCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturingCompany"));

        TableColumn<VaccineType, Integer> vaccineDoseColumn = new TableColumn<>("Doses Needed");
        vaccineDoseColumn.setCellValueFactory(new PropertyValueFactory<>("dosesNeeded"));

        tblVaccineTypes.getColumns().addAll(vaccineNameColumn, vaccineDoseColumn);

        tblVaccineTypes.setRowFactory(tableView -> {
            TableRow<VaccineType> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedVaccineType = row.getItem();
                    Navigator.showInDialog(tblVaccineTypes.getScene().getWindow(), "view_vaccine_type", this);
                }
            });
            return row;
        });

        TableHelper.autoSizeColumns(tblVaccineTypes);

        refresh();

        sortableData.comparatorProperty().bind(tblVaccineTypes.comparatorProperty());

    }
}

