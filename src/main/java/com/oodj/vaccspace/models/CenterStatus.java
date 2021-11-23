package com.oodj.vaccspace.models;

public enum CenterStatus {
    OPEN("Open", "#00d31d", "far-check-circle"),
    CLOSED("Closed", "#ff0000", "far-times-circle"),
    CLOSED_COVID("Closed due to COVID", "#ffd100", "fas-exclamation-circle");

    private final String value;
    private final String color;
    private final String icon;

    CenterStatus(String value, String color, String icon) {
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
