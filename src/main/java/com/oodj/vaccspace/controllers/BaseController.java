package com.oodj.vaccspace.controllers;

import io.github.palexdev.materialfx.controls.MFXStageDialog;

public abstract class BaseController {
    private MFXStageDialog stageDialog;
    private Object userData;

    public MFXStageDialog getStageDialog() {
        return stageDialog;
    }

    public void setStageDialog(MFXStageDialog stageDialog) {
        this.stageDialog = stageDialog;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}
