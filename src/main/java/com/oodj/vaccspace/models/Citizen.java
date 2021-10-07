package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.Repository;

@Repository
public class Citizen extends People {

    @Column
    private String IC;

    public Citizen(String name, String phone, String email, String password, VaccinationStatus vaccinationStatus, String IC) {
        super(name, phone, email, password, vaccinationStatus);
        this.IC = IC;
    }

    public Citizen() {
    }

    @Override
    public String getIdentification() {
        return this.IC;
    }
}
