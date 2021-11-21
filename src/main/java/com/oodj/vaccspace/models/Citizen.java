package com.oodj.vaccspace.models;

import textorm.Repository;

@Repository
public class Citizen extends Person {

    public Citizen(
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

    public Citizen() {
    }

    @Override
    public String getIdentification() {
        return this.identificationNumber;
    }

    @Override
    public void setIdentification(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
}