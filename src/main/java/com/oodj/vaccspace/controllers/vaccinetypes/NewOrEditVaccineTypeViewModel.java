package com.oodj.vaccspace.controllers.vaccinetypes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NewOrEditVaccineTypeViewModel {
    private final StringProperty vaccineName = new SimpleStringProperty("");
    private final StringProperty manufacturingCompany = new SimpleStringProperty("");
    private final StringProperty numberOfDoses = new SimpleStringProperty("");

    public NewOrEditVaccineTypeViewModel(
            StringProperty vaccineName,
            StringProperty manufacturingCompany,
            StringProperty numberOfDoses
    ) {
        this.vaccineName.bindBidirectional(vaccineName);
        this.manufacturingCompany.bindBidirectional(manufacturingCompany);
        this.numberOfDoses.bindBidirectional(numberOfDoses);
    }

    public String getVaccineName() {
        return vaccineName.get();
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName.set(vaccineName);
    }

    public StringProperty vaccineNameProperty() {
        return vaccineName;
    }

    public String getManufacturingCompany() {
        return manufacturingCompany.get();
    }

    public void setManufacturingCompany(String manufacturingCompany) {
        this.manufacturingCompany.set(manufacturingCompany);
    }

    public StringProperty manufacturingCompanyProperty() {
        return manufacturingCompany;
    }

    public String getNumberOfDoses() {
        return numberOfDoses.get();
    }

    public void setNumberOfDoses(String numberOfDoses) {
        this.numberOfDoses.set(numberOfDoses);
    }

    public StringProperty numberOfDosesProperty() {
        return numberOfDoses;
    }
}
