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
    String passportNumber;

    @Column
    boolean isNonCitizen;

    @HasMany(targetKey = "personId")
    private List<Appointment> appointments;

    public Person(
            String name,
            String phone,
            String email,
            String password,
            VaccinationStatus vaccinationStatus,
            String identificationNumber,
            String passportNumber,
            boolean isNonCitizen
    ) {
        super(name, phone, email, password);
        this.vaccinationStatus = vaccinationStatus;
        this.identificationNumber = identificationNumber;
        this.passportNumber = passportNumber;
        this.isNonCitizen = isNonCitizen;
    }

    public Person() {
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
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
        return "This is not a Citizen or Non-Citizen";
    }

    public void setIdentification(String identification) {
        // nothing
    }
}
