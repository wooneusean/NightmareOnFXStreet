package com.oodj.vaccspace.utils;

import io.github.palexdev.materialfx.controls.MFXStageDialog;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Window;

public class Page {
    private String path;
    private String displayName;

    public Page(String path, String displayName) {
        this.path = path;
        this.displayName = displayName;
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

    public static void showDialog(Window owner, DialogType type, String title, String content) {
        MFXStageDialog dialog = new MFXStageDialog(type, title, content);
        dialog.setOwner(owner);

        dialog.setAnimationMillis(250);
        dialog.setAnimate(true);

        dialog.setModality(Modality.APPLICATION_MODAL);

        dialog.setScrimBackground(true);
        dialog.setScrimOpacity(0.75);

        dialog.setCenterInOwner(true);
        dialog.show();
    }
}
