package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasOne;
import textorm.Model;
import textorm.Repository;

import java.time.LocalDate;

@Repository
public class Vaccine extends Model {
    @Column
    private int vaccineBatchId;

    @HasOne(foreignKey = "vaccineBatchId")
    private VaccineBatch vaccineBatch;
    @Column
    private LocalDate expiryDate;
    @Column
    private VaccineStatus vaccineStatus;

    public Vaccine(int vaccineBatchId, LocalDate expiryDate, VaccineStatus vaccineStatus) {
        this.vaccineBatchId = vaccineBatchId;
        this.expiryDate = expiryDate;
        this.vaccineStatus = vaccineStatus;
    }

    public Vaccine() {
    }

    public VaccineBatch getVaccineBatch() {
        return vaccineBatch;
    }

    public void setVaccineBatch(VaccineBatch vaccineBatch) {
        this.vaccineBatch = vaccineBatch;
        this.vaccineBatchId = vaccineBatch.getId();
    }

    public int getVaccineBatchId() {
        return vaccineBatchId;
    }

    public void setVaccineBatchId(int vaccineBatchId) {
        this.vaccineBatchId = vaccineBatchId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public VaccineStatus getVaccineStatus() {
        return vaccineStatus;
    }

    public void setVaccineStatus(VaccineStatus vaccineStatus) {
        this.vaccineStatus = vaccineStatus;
    }
}
