package com.oodj.vaccspace.models;

public enum Dose {
    FIRST("First"),
    SECOND("Second"),
    THIRD("Third");

    private final String value;

    Dose(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
