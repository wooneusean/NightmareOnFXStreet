package com.oodj.vaccspace.models;

import textorm.Repository;

@Repository
public class NonCitizen extends Person {

    public NonCitizen() {
    }

    public NonCitizen(String name, String phone, String email, String password, VaccinationStatus vaccinationStatus, String identificationNumber, boolean isNonCitizen) {
        super(name, phone, email, password, vaccinationStatus, identificationNumber, isNonCitizen);
    }

    @Override
    public String getIdentification() {
        return this.identificationNumber;
    }
}
