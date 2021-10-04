package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.Repository;

@Repository
public class NonCitizen extends People {
    @Column
    private String passportNumber;

    public NonCitizen() {
    }

    public NonCitizen(String name, String phone, String email, String password, VaccinationStatus vaccinationStatus, String passportNumber) {
        super(name, phone, email, password, vaccinationStatus);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
