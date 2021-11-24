package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Model;
import textorm.Repository;

import java.util.List;

@Repository
public class VaccineType extends Model {

    @Column
    private String vaccineName;

    @Column
    private String manufacturingCompany;

    @Column
    private int dosesNeeded;

    @HasMany(targetKey = "vaccineTypeId")
    private List<VaccineBatch> vaccineBatches;

    public VaccineType() {
    }

    public VaccineType(String vaccineName, String manufacturingCompany, int dosesNeeded) {
        this.vaccineName = vaccineName;
        this.manufacturingCompany = manufacturingCompany;
        this.dosesNeeded = dosesNeeded;
    }

    @Override
    public String toString() {
        return vaccineName;
    }

    public List<VaccineBatch> getVaccineBatches() {
        return vaccineBatches;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getManufacturingCompany() {
        return manufacturingCompany;
    }

    public void setManufacturingCompany(String manufacturingCompany) {
        this.manufacturingCompany = manufacturingCompany;
    }

    public int getDosesNeeded() {
        return dosesNeeded;
    }

    public void setDosesNeeded(int dosesNeeded) {
        this.dosesNeeded = dosesNeeded;
    }
}
