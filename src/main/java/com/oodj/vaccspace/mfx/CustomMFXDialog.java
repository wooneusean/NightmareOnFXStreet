package com.oodj.vaccspace.mfx;


import io.github.palexdev.materialfx.controls.base.AbstractMFXDialog;
import javafx.scene.layout.HBox;

public class CustomMFXDialog extends AbstractMFXDialog {
    @Override
    public void show() {
    }

    @Override
    public void close() {
        getCloseHandler().handle(null);
    }

    @Override
    public AbstractMFXDialog setActions(HBox actionsBox) {
        return null;
    }

    @Override
    public void computeSizeAndPosition() {
    }
}
