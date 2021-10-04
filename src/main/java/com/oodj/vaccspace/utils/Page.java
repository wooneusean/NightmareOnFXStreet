package com.oodj.vaccspace.utils;

import javafx.scene.Scene;

class Page {
    private String path;
    private String displayName;
    private Scene scene;

    public Page(String path, String displayName) {
        this.path = path;
        this.displayName = displayName;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
