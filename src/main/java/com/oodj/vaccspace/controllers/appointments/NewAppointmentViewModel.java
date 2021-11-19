package com.oodj.vaccspace.controllers.appointments;

import com.oodj.vaccspace.models.VaccinationCenter;
import com.oodj.vaccspace.models.VaccineType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;

import java.time.LocalDate;

public class NewAppointmentViewModel {
    private final ListProperty<VaccinationCenter> vaccinationCenterListProperty = new SimpleListProperty<>();
    private final ListProperty<VaccineType> vaccineTypeListProperty = new SimpleListProperty<>();

    private final ObjectProperty<SingleSelectionModel<VaccinationCenter>> selectedVaccinationCenterProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<SingleSelectionModel<VaccineType>> selectedVaccineTypeProperty = new SimpleObjectProperty<>();

    private final ObjectProperty<LocalDate> localDateObjectProperty = new SimpleObjectProperty<>();

    public NewAppointmentViewModel() {
    }

    public LocalDate getLocalDateObjectProperty() {
        return localDateObjectProperty.get();
    }

    public void setLocalDateObjectProperty(LocalDate localDateObjectProperty) {
        this.localDateObjectProperty.set(localDateObjectProperty);
    }

    public ObjectProperty<LocalDate> localDateObjectPropertyProperty() {
        return localDateObjectProperty;
    }

    public ObservableList<VaccinationCenter> getVaccinationCenterListProperty() {
        return vaccinationCenterListProperty.get();
    }

    public void setVaccinationCenterListProperty(ObservableList<VaccinationCenter> vaccinationCenterListProperty) {
        this.vaccinationCenterListProperty.set(vaccinationCenterListProperty);
    }

    public ListProperty<VaccinationCenter> vaccinationCenterListPropertyProperty() {
        return vaccinationCenterListProperty;
    }

    public ObservableList<VaccineType> getVaccineTypeListProperty() {
        return vaccineTypeListProperty.get();
    }

    public void setVaccineTypeListProperty(ObservableList<VaccineType> vaccineTypeListProperty) {
        this.vaccineTypeListProperty.set(vaccineTypeListProperty);
    }

    public ListProperty<VaccineType> vaccineTypeListPropertyProperty() {
        return vaccineTypeListProperty;
    }

    public SingleSelectionModel<VaccinationCenter> getSelectedVaccinationCenterProperty() {
        return selectedVaccinationCenterProperty.get();
    }

    public void setSelectedVaccinationCenterProperty(SingleSelectionModel<VaccinationCenter> selectedVaccinationCenterProperty) {
        this.selectedVaccinationCenterProperty.set(selectedVaccinationCenterProperty);
    }

    public ObjectProperty<SingleSelectionModel<VaccinationCenter>> selectedVaccinationCenterPropertyProperty() {
        return selectedVaccinationCenterProperty;
    }

    public SingleSelectionModel<VaccineType> getSelectedVaccineTypeProperty() {
        return selectedVaccineTypeProperty.get();
    }

    public void setSelectedVaccineTypeProperty(SingleSelectionModel<VaccineType> selectedVaccineTypeProperty) {
        this.selectedVaccineTypeProperty.set(selectedVaccineTypeProperty);
    }

    public ObjectProperty<SingleSelectionModel<VaccineType>> selectedVaccineTypePropertyProperty() {
        return selectedVaccineTypeProperty;
    }

}
