package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Model;
import textorm.Repository;

@Repository
public class VaccineType extends Model {

    @Column
    private String vaccineName;

    @Column
    private int dosesNeeded;
    @HasMany(targetKey = "vaccineTypeId")
    private VaccineBatch[] vaccineBatches;

    public VaccineType() {
    }

    public VaccineType(String vaccineName, int dosesNeeded) {
        this.vaccineName = vaccineName;
        this.dosesNeeded = dosesNeeded;
    }

    public VaccineBatch[] getVaccineBatches() {
        return vaccineBatches;
    }

    public void setVaccineBatches(VaccineBatch[] vaccineBatches) {
        this.vaccineBatches = vaccineBatches;
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
