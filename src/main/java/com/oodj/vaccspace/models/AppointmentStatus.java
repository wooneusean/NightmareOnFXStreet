package com.oodj.vaccspace.models;

public enum AppointmentStatus {
    CONFIRMED("Confirmed"),
    FULFILLED("Fulfilled"),
    VOIDED("Voided"),
    ABSENT("Absent");

    private final String value;

    AppointmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
