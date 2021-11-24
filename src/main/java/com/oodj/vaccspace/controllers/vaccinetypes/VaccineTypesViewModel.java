package com.oodj.vaccspace.controllers.vaccinetypes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VaccineTypesViewModel {
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
