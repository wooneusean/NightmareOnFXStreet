package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Repository;

import java.time.LocalDate;

@Repository
public class People extends User {

    @Column
    VaccinationStatus vaccinationStatus;

    @HasMany(targetKey = "personId")
    private Appointment[] appointments;

    public People(String name, String phone, String email, String password, VaccinationStatus vaccinationStatus) {
        super(name, phone, email, password);
        this.vaccinationStatus = vaccinationStatus;
    }

    public People() {
    }

    public Appointment[] getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointment[] appointments) {
        this.appointments = appointments;
    }

    public VaccinationStatus getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(VaccinationStatus vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getIdentification() {
        return this.getEmail();
    }

    public Appointment registerVaccination(int vaccinationCenterId, int vaccineId, LocalDate appointmentDate) {
        Appointment newAppointment = new Appointment(getId(), vaccinationCenterId, vaccineId, appointmentDate, AppointmentStatus.AWAITING_CONFIRMATION, Dose.FIRST);
        newAppointment.save();
        return newAppointment;
    }
}
