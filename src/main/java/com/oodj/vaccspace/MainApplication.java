package com.oodj.vaccspace;

import javafx.application.Application;
import javafx.stage.Stage;
import textorm.TextORM;

import java.io.IOException;


public class MainApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        TextORM.setStoragePath("storage");
        TextORM.setMetaStoragePath("storage");

        try {
            Navigator.init(stage, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

