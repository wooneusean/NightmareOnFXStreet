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
    private int dosesNeeded;

    @HasMany(targetKey = "vaccineTypeId")
    private List<VaccineBatch> vaccineBatches;

    public VaccineType() {
    }

    public VaccineType(String vaccineName, int dosesNeeded) {
        this.vaccineName = vaccineName;
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

    public int getDosesNeeded() {
        return dosesNeeded;
    }

    public void setDosesNeeded(int dosesNeeded) {
        this.dosesNeeded = dosesNeeded;
    }
}
