package com.oodj.vaccspace.controllers.vaccinebatches;

import javafx.beans.property.*;

import java.time.LocalDate;

public class VaccineBatchesViewModel {
    BooleanProperty advancedFiltersShown = new SimpleBooleanProperty(false);
    StringProperty search = new SimpleStringProperty();
    DoubleProperty minimumValue = new SimpleDoubleProperty(0);
    ObjectProperty<LocalDate> arrivalStartDate = new SimpleObjectProperty<>();
    ObjectProperty<LocalDate> arrivalEndDate = new SimpleObjectProperty<>();
    ObjectProperty<LocalDate> expiryStartDate = new SimpleObjectProperty<>();
    ObjectProperty<LocalDate> expiryEndDate = new SimpleObjectProperty<>();

    public LocalDate getExpiryStartDate() {
        return expiryStartDate.get();
    }

    public void setExpiryStartDate(LocalDate expiryStartDate) {
        this.expiryStartDate.set(expiryStartDate);
    }

    public ObjectProperty<LocalDate> expiryStartDateProperty() {
        return expiryStartDate;
    }

    public LocalDate getExpiryEndDate() {
        return expiryEndDate.get();
    }

    public void setExpiryEndDate(LocalDate expiryEndDate) {
        this.expiryEndDate.set(expiryEndDate);
    }

    public ObjectProperty<LocalDate> expiryEndDateProperty() {
        return expiryEndDate;
    }

    public LocalDate getArrivalStartDate() {
        return arrivalStartDate.get();
    }

    public void setArrivalStartDate(LocalDate arrivalStartDate) {
        this.arrivalStartDate.set(arrivalStartDate);
    }

    public ObjectProperty<LocalDate> arrivalStartDateProperty() {
        return arrivalStartDate;
    }

    public LocalDate getArrivalEndDate() {
        return arrivalEndDate.get();
    }

    public void setArrivalEndDate(LocalDate arrivalEndDate) {
        this.arrivalEndDate.set(arrivalEndDate);
    }

    public ObjectProperty<LocalDate> arrivalEndDateProperty() {
        return arrivalEndDate;
    }

    public double getMinimumValue() {
        return minimumValue.get();
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue.set(minimumValue);
    }

    public DoubleProperty minimumValueProperty() {
        return minimumValue;
    }

    public String getSearch() {
        return search.get();
    }

    public void setSearch(String search) {
        this.search.set(search);
    }

    public StringProperty searchProperty() {
        return search;
    }

    public boolean isAdvancedFiltersShown() {
        return advancedFiltersShown.get();
    }

    public void setAdvancedFiltersShown(boolean advancedFiltersShown) {
        this.advancedFiltersShown.set(advancedFiltersShown);
    }

    public BooleanProperty advancedFiltersShownProperty() {
        return advancedFiltersShown;
    }
}
