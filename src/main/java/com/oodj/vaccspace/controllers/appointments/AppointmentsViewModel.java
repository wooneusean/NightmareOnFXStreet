package com.oodj.vaccspace.controllers.appointments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppointmentsViewModel {
    private final StringProperty search = new SimpleStringProperty();

    public String getSearch() {
        return search.get();
    }

    public void setSearch(String search) {
        this.search.set(search);
    }

    public StringProperty searchProperty() {
        return search;
    }
}
