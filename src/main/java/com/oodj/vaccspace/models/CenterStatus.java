package com.oodj.vaccspace.models;

public enum CenterStatus {
    OPEN("Open"),
    CLOSED("Closed"),
    CLOSED_COVID("Closed due to COVID");

    private final String value;

    CenterStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
