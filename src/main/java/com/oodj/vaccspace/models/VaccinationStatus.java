package com.oodj.vaccspace.models;

public enum VaccinationStatus {
    NOT_REGISTERED("Not Registered", "#FF7B7B"),
    REGISTERED("Registered", "#FCC07B"),
    AWAITING_FIRST_DOSE("Awaiting First Dose", "#FFF7AC"),
    AWAITING_SECOND_DOSE("Awaiting Second Dose", "#E7FF87"),
    FULLY_VACCINATED("Fully Vaccinated", "#8EFF58");


    private final String value;
    private final String color;

    VaccinationStatus(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }
}
