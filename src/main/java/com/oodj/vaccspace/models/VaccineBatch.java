package com.oodj.vaccspace.models;

import textorm.*;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VaccineBatch extends Model {

    @HasOne(foreignKey = "vaccineTypeId")
    private VaccineType vaccineType;

    @Column
    private int vaccineTypeId;

    @Column
    private int amount;

    @Column
    private int vaccinationCenterId;

    @HasOne(foreignKey = "vaccinationCenterId")
    private VaccinationCenter vaccinationCenter;

    @Column
    private LocalDate arrivalDate;

    @HasMany(targetKey = "vaccineBatchId")
    private List<Vaccine> vaccines;

    @Column
    private int availableAmount;

    @Column
    private LocalDate expiryDate;

    public VaccineBatch() {
    }

    public VaccineBatch(int vaccineTypeId, int amount, int vaccinationCenterId, LocalDate arrivalDate, LocalDate expiryDate, int availableAmount) {
        this.vaccineTypeId = vaccineTypeId;
        this.amount = amount;
        this.vaccinationCenterId = vaccinationCenterId;
        this.arrivalDate = arrivalDate;
        this.expiryDate = expiryDate;
        this.availableAmount = availableAmount;
    }

    public VaccineType getVaccineType() {
        this.include(VaccineType.class);
        return vaccineType;
    }

    public void setVaccineType(VaccineType vaccineType) {
        this.vaccineType = vaccineType;
        this.vaccineTypeId = vaccineType.getId();
    }

    public int getVaccineTypeId() {
        return vaccineTypeId;
    }

    public void setVaccineTypeId(int vaccineTypeId) {
        this.vaccineTypeId = vaccineTypeId;
    }

    public VaccinationCenter getVaccinationCenter() {
        return vaccinationCenter;
    }

    public void setVaccinationCenter(VaccinationCenter vaccinationCenter) {
        this.vaccinationCenter = vaccinationCenter;
        this.vaccinationCenterId = vaccinationCenter.getId();
    }

    public List<Vaccine> getVaccines() {
        return vaccines;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getVaccinationCenterId() {
        return vaccinationCenterId;
    }

    public void setVaccinationCenterId(int vaccinationCenterId) {
        this.vaccinationCenterId = vaccinationCenterId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getAvailableAmount() { return availableAmount; }

    public void setAvailableAmount(int availableAmount) { this.availableAmount = availableAmount; }

    public LocalDate getExpiryDate() { return expiryDate; }

    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}
