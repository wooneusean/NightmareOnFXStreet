package com.oodj.vaccspace.models;

public enum VaccinationStatus {
    NOT_REGISTERED("Not Registered", "#FF7B7B", "fas-user-check"),
    AWAITING_FIRST_DOSE("Awaiting First Dose", "#FCC07B", "fas-syringe"),
    AWAITING_SECOND_DOSE("Awaiting Second Dose", "#E7FF87", "fas-syringe"),
    FULLY_VACCINATED("Fully Vaccinated", "#8EFF58", "far-check-circle");

    private final String value;
    private final String color;
    private final String icon;

    VaccinationStatus(String value, String color, String icon) {
        this.value = value;
        this.color = color;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }
}
