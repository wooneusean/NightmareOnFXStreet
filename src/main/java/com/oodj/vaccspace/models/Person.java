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
    String identification;

    @Column
    boolean isNonCitizen;

    @HasMany(targetKey = "personId")
    private List<Appointment> appointments;

    /**
     * Creates a new Person object
     *
     * @param name              name for the user
     * @param phone             phone number of the user
     * @param email             email for the user
     * @param password          password to login, if {@code null} value provided, defaults to {@code email#xxxx} where
     *                          {@code xxxx} is the last 4 digits of phone number
     * @param vaccinationStatus status of vaccination
     * @param identification    IC number if citizen and passport number if non-citizen
     * @param isNonCitizen      flag to differentiate citizen to non-citizen
     */
    public Person(
            String name,
            String phone,
            String email,
            String password,
            VaccinationStatus vaccinationStatus,
            String identification,
            boolean isNonCitizen
    ) {
        super(name, phone, email, password);
        this.vaccinationStatus = vaccinationStatus;
        this.identification = identification;
        this.isNonCitizen = isNonCitizen;
    }

    public Person() {
    }

    public boolean isNonCitizen() {
        return isNonCitizen;
    }

    public void setNonCitizen(boolean nonCitizen) {
        isNonCitizen = nonCitizen;
    }

    public String getIdentification() {
        return this.identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
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
}
