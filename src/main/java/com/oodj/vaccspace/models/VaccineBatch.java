package com.oodj.vaccspace.models;

import textorm.*;

import java.time.LocalDate;

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
    private Vaccine[] vaccines;

    public VaccineBatch() {
    }

    public VaccineBatch(int vaccineTypeId, int amount, int vaccinationCenterId, LocalDate arrivalDate) {
        this.vaccineTypeId = vaccineTypeId;
        this.amount = amount;
        this.vaccinationCenterId = vaccinationCenterId;
        this.arrivalDate = arrivalDate;
    }

    public VaccineType getVaccineType() {
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

    public Vaccine[] getVaccines() {
        return vaccines;
    }

    public void setVaccines(Vaccine[] vaccines) {
        this.vaccines = vaccines;
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
}
