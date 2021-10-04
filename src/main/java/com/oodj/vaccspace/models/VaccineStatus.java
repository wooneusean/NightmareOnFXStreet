package com.oodj.vaccspace.models;

public enum VaccineStatus {
    USED("Used"),
    EXPIRED("Expired"),
    VOIDED("Voided"),
    AVAILABLE("Available");

    private final String value;

    VaccineStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
