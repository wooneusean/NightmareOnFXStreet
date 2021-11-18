package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Repository;

import java.util.List;

@Repository
public class People extends User {

    @Column
    VaccinationStatus vaccinationStatus;

    @HasMany(targetKey = "personId")
    private List<Appointment> appointments;

    public People(String name, String phone, String email, String password, VaccinationStatus vaccinationStatus) {
        super(name, phone, email, password);
        this.vaccinationStatus = vaccinationStatus;
    }

    public People() {
    }

    public List<Appointment> getAppointments() {
        return appointments;
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
}
