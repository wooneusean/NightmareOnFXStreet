package com.oodj.vaccspace.controllers.vaccinebatches;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.VaccinationCenter;
import com.oodj.vaccspace.models.VaccineBatch;
import com.oodj.vaccspace.models.VaccineType;
import com.oodj.vaccspace.utils.Navigator;
import com.oodj.vaccspace.utils.StringHelper;
import com.oodj.vaccspace.utils.TableHelper;
import io.github.euseanwoon.MFXPillButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import textorm.TextORM;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class VaccineBatchesController extends BaseController implements Initializable {

    VaccineBatchesViewModel vm = new VaccineBatchesViewModel();

    ObservableList<VaccineBatch> masterData;

    FilteredList<VaccineBatch> filteredData;

    SortedList<VaccineBatch> sortableData;

    VaccineBatch selectedVaccineBatch = new VaccineBatch();

    @FXML
    private MFXPillButton btnAddBatch;

    @FXML
    private MFXPillButton btnReset;

    @FXML
    private MFXPillButton btnSearch;

    @FXML
    private DatePicker dtpArrivalEndDate;

    @FXML
    private DatePicker dtpArrivalStartDate;

    @FXML
    private DatePicker dtpExpiryEndDate;

    @FXML
    private DatePicker dtpExpiryStartDate;

    @FXML
    private VBox vbxAdvancedFilters;

    @FXML
    private Slider sldMinimumAmount;

    @FXML
    private TableView<VaccineBatch> tblVaccineBatches;

    @FXML
    private Hyperlink lnkShowAdvanced;

    @FXML
    private MFXTextField txtSearch;

    @FXML
    void onSearchPressed(ActionEvent event) {
        filteredData.setPredicate(getSearchPredicate());
    }

    @FXML
    void onAddBatchPressed(ActionEvent event) {
        Navigator.showInDialog(btnAddBatch.getScene().getWindow(), "new_batch", this);
    }

    @FXML
    void onResetPressed(ActionEvent event) {
        vm.setSearch("");
        vm.setMinimumValue(100);
        vm.setArrivalStartDate(null);
        vm.setArrivalEndDate(null);
        vm.setExpiryStartDate(LocalDate.now().minusDays(1));
        vm.setExpiryEndDate(null);

        onSearchPressed(event);
    }

    @FXML
    void onShowAdvancedFiltersPressed(ActionEvent event) {
        vm.setAdvancedFiltersShown(!vm.isAdvancedFiltersShown());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupViewModel();

        setupTable();

        vm.searchProperty().addListener((observableValue, oldValue, newValue) -> filteredData.setPredicate(
                getSearchPredicate()
        ));
    }

    private void setupViewModel() {
        vm.searchProperty().bindBidirectional(txtSearch.textProperty());
        vm.minimumValueProperty().bindBidirectional(sldMinimumAmount.valueProperty());
        vm.arrivalStartDateProperty().bindBidirectional(dtpArrivalStartDate.valueProperty());
        vm.arrivalEndDateProperty().bindBidirectional(dtpArrivalEndDate.valueProperty());
        vm.expiryStartDateProperty().bindBidirectional(dtpExpiryStartDate.valueProperty());
        vm.expiryEndDateProperty().bindBidirectional(dtpExpiryEndDate.valueProperty());

        vm.setExpiryStartDate(LocalDate.now().minusDays(1));

        vbxAdvancedFilters.managedProperty().bindBidirectional(vm.advancedFiltersShownProperty());
        vbxAdvancedFilters.visibleProperty().bindBidirectional(vm.advancedFiltersShownProperty());
    }

    private void setupTable() {
        TableColumn<VaccineBatch, String> vaccineTypeColumn = new TableColumn<>("Vaccine Type");
        vaccineTypeColumn.setCellValueFactory(rowData -> {
            rowData.getValue().include(VaccineType.class);
            return new SimpleStringProperty(rowData.getValue().getVaccineType().getVaccineName());
        });

        TableColumn<VaccineBatch, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellFactory(amountTableColumn -> new AmountRemainingCell());
        amountColumn.setCellValueFactory(rowData -> {
            VaccineBatch vaccineBatch = rowData.getValue();
            return new SimpleStringProperty(String.valueOf(vaccineBatch.getPercentRemaining()));
        });
        amountColumn.setComparator((o1, o2) -> {
            Double o1Percent = Double.parseDouble(o1);
            Double o2Percent = Double.parseDouble(o2);
            return o1Percent.compareTo(o2Percent);
        });

        TableColumn<VaccineBatch, String> vaccineCenterNameColumn = new TableColumn<>("Vaccine Center");
        vaccineCenterNameColumn.setCellValueFactory(rowData -> {
            VaccineBatch batch = rowData.getValue();
            batch.include(VaccinationCenter.class);
            return new SimpleStringProperty(batch.getVaccinationCenter().getVaccinationCenterName());
        });

        TableColumn<VaccineBatch, LocalDate> arrivalDateColumn = new TableColumn<>("Arrival Date");
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));

        TableColumn<VaccineBatch, LocalDate> expiryDateColumn = new TableColumn<>("Expiry Date");
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        tblVaccineBatches.getColumns().addAll(
                vaccineTypeColumn,
                amountColumn,
                vaccineCenterNameColumn,
                arrivalDateColumn,
                expiryDateColumn
        );

        tblVaccineBatches.setRowFactory(tableView -> {
            TableRow<VaccineBatch> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedVaccineBatch = row.getItem();
                    Navigator.showInDialog(tblVaccineBatches.getScene().getWindow(), "view_batch", this);
                }
            });
            return row;
        });

        TableHelper.autoSizeColumns(tblVaccineBatches);

        refresh();
    }

    public void refresh() {
        List<VaccineBatch> vaccineBatches = TextORM.getAll(
                VaccineBatch.class,
                hashMap -> Objects.equals(hashMap.get("isVoided"), "false")
        );

        masterData = FXCollections.observableArrayList(vaccineBatches);
        filteredData = new FilteredList<>(masterData);
        sortableData = new SortedList<>(filteredData);
        tblVaccineBatches.setItems(sortableData);

        sortableData.comparatorProperty().bind(tblVaccineBatches.comparatorProperty());
    }

    Predicate<VaccineBatch> getSearchPredicate() {
        return vaccineBatch -> {
            vaccineBatch.include(VaccineType.class);
            String vaccineName = vaccineBatch.getVaccineType().getVaccineName();

            vaccineBatch.include(VaccinationCenter.class);
            String vaccineCenterName = vaccineBatch.getVaccinationCenter().getVaccinationCenterName();

            LocalDate arrivalStartDate = vm.getArrivalStartDate() == null ? LocalDate.MIN : vm.getArrivalStartDate();
            LocalDate arrivalEndDate = vm.getArrivalEndDate() == null ? LocalDate.MAX : vm.getArrivalEndDate();
            LocalDate expiryStartDate = vm.getExpiryStartDate() == null ? LocalDate.MIN : vm.getExpiryStartDate();
            LocalDate expiryEndDate = vm.getExpiryEndDate() == null ? LocalDate.MAX : vm.getExpiryEndDate();

            boolean isExpiryDateInRange = vaccineBatch.getExpiryDate().isAfter(expiryStartDate) &&
                                          vaccineBatch.getExpiryDate().isBefore(expiryEndDate);

            boolean isArrivalDateInRange = vaccineBatch.getArrivalDate().isAfter(arrivalStartDate) &&
                                           vaccineBatch.getArrivalDate().isBefore(arrivalEndDate);

            boolean isRemainingEnough = vaccineBatch.getPercentRemaining() <= vm.getMinimumValue();

            return (StringHelper.containsIgnoreCase(vaccineName, vm.getSearch()) ||
                    StringHelper.containsIgnoreCase(vaccineCenterName, vm.getSearch())) &&
                   (isRemainingEnough && isExpiryDateInRange && isArrivalDateInRange);
        };
    }
}

