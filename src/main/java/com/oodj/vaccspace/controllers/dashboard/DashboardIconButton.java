package com.oodj.vaccspace.controllers.dashboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class DashboardIconButton {
    private String iconLiteral;
    private String fxmlId;
    private EventHandler<ActionEvent> eventHandler;

    public DashboardIconButton(String iconLiteral, String fxmlId, EventHandler<ActionEvent> eventHandler) {
        this.iconLiteral = iconLiteral;
        this.fxmlId = fxmlId;
        this.eventHandler = eventHandler;
    }

    public String getIconLiteral() {
        return iconLiteral;
    }

    public void setIconLiteral(String iconLiteral) {
        this.iconLiteral = iconLiteral;
    }

    public String getFxmlId() {
        return fxmlId;
    }

    public void setFxmlId(String fxmlId) {
        this.fxmlId = fxmlId;
    }

    public EventHandler<ActionEvent> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }
}
