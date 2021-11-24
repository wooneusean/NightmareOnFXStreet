package com.oodj.vaccspace.controllers.vaccinetypes;

import javafx.beans.property.*;

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

    public StringProperty vaccineNameProperty() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName.set(vaccineName);
    }

    public String getManufacturingCompany() {
        return manufacturingCompany.get();
    }

    public StringProperty manufacturingCompanyProperty() {
        return manufacturingCompany;
    }

    public void setManufacturingCompany(String manufacturingCompany) {
        this.manufacturingCompany.set(manufacturingCompany);
    }

    public String getNumberOfDoses() {
        return numberOfDoses.get();
    }

    public StringProperty numberOfDosesProperty() {
        return numberOfDoses;
    }

    public void setNumberOfDoses(String numberOfDoses) {
        this.numberOfDoses.set(numberOfDoses);
    }
}
