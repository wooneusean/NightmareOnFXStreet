package com.oodj.vaccspace.models;

import textorm.Repository;

@Repository
public class NonCitizen extends Person {

    public NonCitizen(
            String name,
            String phone,
            String email,
            String password,
            VaccinationStatus vaccinationStatus,
            String identificationNumber,
            String passportNumber,
            boolean isNonCitizen
    ) {
        super(name, phone, email, password, vaccinationStatus, identificationNumber, passportNumber, isNonCitizen);
    }

    public NonCitizen() {
    }

    @Override
    public String getIdentification() {
        return this.passportNumber;
    }

    @Override
    public void setIdentification(String identificationNumber) {
        this.passportNumber = identificationNumber;
    }
}
