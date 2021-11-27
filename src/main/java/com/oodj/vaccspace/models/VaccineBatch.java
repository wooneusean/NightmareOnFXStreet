package com.oodj.vaccspace.models;

import textorm.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class VaccineBatch extends Model {

    @HasOne(foreignKey = "vaccineTypeId")
    private VaccineType vaccineType;

    @Column
    private int vaccineTypeId;

    @Column
    private int amount;

    @Column
    private int availableAmount;

    @Column
    private int vaccinationCenterId;

    @HasOne(foreignKey = "vaccinationCenterId")
    private VaccinationCenter vaccinationCenter;

    @Column
    private LocalDate arrivalDate;

    @HasMany(targetKey = "vaccineBatchId")
    private List<Vaccine> vaccines;

    @Column
    private LocalDate expiryDate;

    @Column
    private Boolean isVoided = false;

    public VaccineBatch() {
    }

    public VaccineBatch(
            int vaccineTypeId,
            int amount,
            int availableAmount,
            int vaccinationCenterId,
            LocalDate arrivalDate,
            LocalDate expiryDate
    ) {
        this.vaccineTypeId = vaccineTypeId;
        this.amount = amount;
        this.availableAmount = availableAmount;
        this.vaccinationCenterId = vaccinationCenterId;
        this.arrivalDate = arrivalDate;
        this.expiryDate = expiryDate;
    }

    public static VaccineBatch getNextAvailableVaccineBatch(int vaccineTypeId, int vaccinationCenterId) throws
                                                                                                        IllegalArgumentException,
                                                                                                        NoSuchElementException,
                                                                                                        IndexOutOfBoundsException {
        List<VaccineBatch> vaccineBatches = TextORM.getAll(
                VaccineBatch.class,
                hashMap -> Integer.parseInt(hashMap.get("vaccineTypeId")) == vaccineTypeId &&
                           LocalDate.parse(hashMap.get("expiryDate")).isAfter(LocalDate.now()) &&
                           Integer.parseInt(hashMap.get("vaccinationCenterId")) == vaccinationCenterId &&
                           Integer.parseInt(hashMap.get("availableAmount")) > 0

        );

        if (vaccineBatches == null || vaccineBatches.size() == 0) {
            throw new NoSuchElementException(
                    "There are no usable batches that belong to that vaccine. Please select a different vaccine.");
        }


        vaccineBatches.sort(Comparator.comparing(VaccineBatch::getExpiryDate));

        return vaccineBatches.get(0);
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

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPercentRemaining() {
        return (double) getAvailableAmount() / getAmount() * 100;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    public void setVoided() {
        isVoided = true;
        save();

        include(Vaccine.class);
        getVaccines().forEach(vaccine -> {
            Appointment appointment = TextORM.getOne(
                    Appointment.class,
                    hashMap -> Integer.parseInt(hashMap.get("vaccineId")) == vaccine.getId()
            );

            if (appointment != null && appointment.getAppointmentStatus() != AppointmentStatus.FULFILLED) {
                appointment.cancel();
            }

            vaccine.setVaccineStatus(VaccineStatus.VOIDED);
            vaccine.save();
        });
    }
}
