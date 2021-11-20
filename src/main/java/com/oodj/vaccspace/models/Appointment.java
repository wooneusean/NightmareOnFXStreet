package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasOne;
import textorm.Model;
import textorm.Repository;

import java.time.LocalDate;

@Repository
public class Appointment extends Model {

    @HasOne(foreignKey = "personId")
    private Person person;

    @Column
    private int personId;

    @Column
    private int vaccinationCenterId;

    @HasOne(foreignKey = "vaccinationCenterId")
    private VaccinationCenter vaccinationCenter;

    @Column
    private int vaccineId;

    @HasOne(foreignKey = "vaccineId")
    private Vaccine vaccine;

    @Column
    private LocalDate appointmentDate;

    @Column
    private AppointmentStatus appointmentStatus;

    @Column
    private Dose dose;

    public Appointment(int personId, int vaccinationCenterId, int vaccineId, LocalDate appointmentDate, AppointmentStatus appointmentStatus, Dose dose) {
        this.personId = personId;
        this.vaccinationCenterId = vaccinationCenterId;
        this.vaccineId = vaccineId;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus;
        this.dose = dose;
    }

    public Appointment() {
    }

    public VaccinationCenter getVaccinationCenter() {
        this.include(VaccinationCenter.class);
        return vaccinationCenter;
    }

    public void setVaccinationCenter(VaccinationCenter vaccinationCenter) {
        this.vaccinationCenter = vaccinationCenter;
        this.vaccinationCenterId = vaccinationCenter.getId();
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Vaccine getVaccine() {
        this.include(Vaccine.class);
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
        this.vaccineId = vaccine.getId();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.personId = person.getId();
    }

    public int getVaccinationCenterId() {
        return vaccinationCenterId;
    }

    public void setVaccinationCenterId(int vaccinationCenterId) {
        this.vaccinationCenterId = vaccinationCenterId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Dose getDose() {
        return dose;
    }

    public void setDose(Dose dose) {
        this.dose = dose;
    }

    public void confirmAppointment() {
        appointmentStatus = AppointmentStatus.CONFIRMED;
    }

    public void fulfillAppointment() {
        appointmentStatus = AppointmentStatus.FULFILLED;
    }

    public void voidAppointment() {
        appointmentStatus = AppointmentStatus.VOIDED;
    }

    public void absentAppointment() {
        appointmentStatus = AppointmentStatus.ABSENT;
    }

    //
    // For use with table
    //
    public String getAppointmentCenterName() {
        return getVaccinationCenter().getVaccinationCenterName();
    }

    public String getVaccineName() {
        return getVaccine().getVaccineBatch().getVaccineType().getVaccineName();
    }
}
