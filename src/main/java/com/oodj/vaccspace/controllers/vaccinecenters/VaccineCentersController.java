package com.oodj.vaccspace.controllers.vaccinecenters;

import com.oodj.vaccspace.Global;
import com.oodj.vaccspace.models.CenterStatus;
import com.oodj.vaccspace.models.VaccinationCenter;
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

public class VaccineCentersController implements Initializable {

    VaccineCentersViewModel vm = new VaccineCentersViewModel();

    ObservableList<VaccinationCenter> masterData;

    FilteredList<VaccinationCenter> filteredData;

    SortedList<VaccinationCenter> sortableData;

    VaccinationCenter selectedVaccinationCenter = new VaccinationCenter();

    @FXML
    private TableView<VaccinationCenter> tblVaccineCenters;

    @FXML
    private MFXTextField txtSearch;

    @FXML
    private MFXPillButton btnAddCenter;

    @FXML
    void onAddCenterPressed(ActionEvent event) {
        Navigator.showInDialog(btnAddCenter.getScene().getWindow(), "new_vaccine_center", this);
    }

    @FXML
    void onSearchChanged(KeyEvent event) {
    }

    private Predicate<VaccinationCenter> getFilterVaccinationCenterPredicate() {
        return vaccinationCenter -> {
            return StringHelper.containsIgnoreCase(vaccinationCenter.getVaccinationCenterName(), vm.getSearch()) ||
                   StringHelper.containsIgnoreCase(vaccinationCenter.getCenterStatus().getValue(), vm.getSearch()) ||
                   StringHelper.containsIgnoreCase(vaccinationCenter.getCenterState(), vm.getSearch());
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!Global.isCommittee()) {
            btnAddCenter.setManaged(false);
        }

        vm.searchProperty().bindBidirectional(txtSearch.textProperty());

        vm.searchProperty().addListener((observableValue, s, t1) -> {
            filteredData.setPredicate(getFilterVaccinationCenterPredicate());
        });

        TableColumn<VaccinationCenter, String> vaccineCenterNameColumn = new TableColumn<>("Vaccine Center Name");
        vaccineCenterNameColumn.setCellValueFactory(new PropertyValueFactory<>("vaccinationCenterName"));

        TableColumn<VaccinationCenter, CenterStatus> vaccineCenterStatusColumn = new TableColumn<>("Status");
        vaccineCenterStatusColumn.setCellValueFactory(new PropertyValueFactory<>("centerStatus"));
        vaccineCenterStatusColumn.setCellFactory(statusColumn -> new CenterStatusIndicatorCell());


        TableColumn<VaccinationCenter, String> vaccineCenterStateColumn = new TableColumn<>("State");
        vaccineCenterStateColumn.setCellValueFactory(new PropertyValueFactory<>("centerState"));

        tblVaccineCenters.getColumns().addAll(
                vaccineCenterNameColumn,
                vaccineCenterStateColumn,
                vaccineCenterStatusColumn
        );

        tblVaccineCenters.setRowFactory(tableView -> {
            TableRow<VaccinationCenter> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedVaccinationCenter = row.getItem();
                    Navigator.showInDialog(tblVaccineCenters.getScene().getWindow(), "view_vaccine_center", this);
                }
            });
            return row;
        });

        TableHelper.autoSizeColumns(tblVaccineCenters);

        refresh();
    }

    public void refresh() {
        List<VaccinationCenter> vaccineCenters = TextORM.getAll(VaccinationCenter.class, hashMap -> true);

        masterData = FXCollections.observableArrayList(vaccineCenters);
        filteredData = new FilteredList<>(masterData);
        sortableData = new SortedList<>(filteredData);

        tblVaccineCenters.setItems(sortableData);

        sortableData.comparatorProperty().bind(tblVaccineCenters.comparatorProperty());
    }
}


