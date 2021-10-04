package com.oodj.vaccspace.models;

public enum VaccinationStatus {
    NOT_REGISTERED("Not Registered"),
    REGISTERED("Registered"),
    AWAITING_FIRST_DOSE("Awaiting First Dose"),
    AWAITING_SECOND_DOSE("Awaiting Second Dose"),
    FULLY_VACCINATED("Fully Vaccinated");


    private final String value;

    VaccinationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
