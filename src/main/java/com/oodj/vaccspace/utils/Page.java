package com.oodj.vaccspace.utils;

import io.github.palexdev.materialfx.controls.MFXStageDialog;
import io.github.palexdev.materialfx.controls.enums.ButtonType;
import io.github.palexdev.materialfx.controls.enums.DialogType;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.util.Optional;

public class Page {
    private String path;
    private String displayName;

    public Page(String path, String displayName) {
        this.path = path;
        this.displayName = displayName;
    }

    /**
     * Shows a dialog over the owner window with specified type, title and content
     *
     * @param owner   {@link Window} to instantiate the dialog.
     * @param type    {@link DialogType} of the dialog.
     * @param title   Title for the dialog.
     * @param content Content of the dialog.
     */
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

    public static <R> Optional<R> showDialogAndWait(Window owner,String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(owner);
        alert.initModality(Modality.APPLICATION_MODAL);
        return (Optional<R>) alert.showAndWait();
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
