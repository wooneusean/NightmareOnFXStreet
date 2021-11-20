package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Repository;

import java.util.List;

@Repository
public class Person extends User {

    @Column
    VaccinationStatus vaccinationStatus;

    @Column
    String identificationNumber;

    @Column
    boolean isNonCitizen;

    @HasMany(targetKey = "personId")
    private List<Appointment> appointments;

    public Person(String name, String phone, String email, String password, VaccinationStatus vaccinationStatus, String identificationNumber, boolean isNonCitizen) {
        super(name, phone, email, password);
        this.vaccinationStatus = vaccinationStatus;
        this.identificationNumber = identificationNumber;
        this.isNonCitizen = isNonCitizen;
    }

    public Person() {
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public boolean isNonCitizen() {
        return isNonCitizen;
    }

    public void setNonCitizen(boolean nonCitizen) {
        isNonCitizen = nonCitizen;
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
