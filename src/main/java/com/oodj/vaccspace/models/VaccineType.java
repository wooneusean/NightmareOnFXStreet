package com.oodj.vaccspace.models;

import textorm.*;

import java.util.List;
import java.util.Objects;

@Repository
public class VaccineType extends Model {

    @Column
    private String vaccineName;

    @Column
    private String manufacturingCompany;

    @Column
    private int dosesNeeded;

    @Column
    private Boolean isVoided = false;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VaccineType that = (VaccineType) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaccineName);
    }

    public void setVoided() {
        isVoided = true;
        save();

        include(VaccineBatch.class);
        List<VaccineBatch> vaccineBatches = TextORM.getAll(
                VaccineBatch.class,
                hashMap -> Integer.parseInt(hashMap.get("vaccineTypeId")) == getId()
        );

        vaccineBatches.forEach(VaccineBatch::setVoided);
    }
}
