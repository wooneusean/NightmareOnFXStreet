package com.oodj.vaccspace.models;

public enum AppointmentStatus {
    CONFIRMED("Confirmed", "#ffd100", "far-calendar-check"),
    FULFILLED("Fulfilled", "#00FF00", "far-check-circle"),
    VOIDED("Voided", "#000000", "far-times-circle"),
    ABSENT("Absent", "#FF0000", "fas-user-times");

    private final String value;
    private final String color;
    private final String icon;

    AppointmentStatus(String value, String color, String icon) {
        this.value = value;
        this.color = color;
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public String getValue() {
        return value;
    }
}
