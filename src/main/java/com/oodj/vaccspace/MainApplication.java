package com.oodj.vaccspace;

import com.oodj.vaccspace.utils.Navigator;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import textorm.TextORM;

import java.util.Objects;


public class MainApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        TextORM.setStoragePath("storage");
        TextORM.setMetaStoragePath("storage");

        // Set icons
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("images/vaccine_small.png"))));

        // Load fonts
        Font.loadFont(getClass().getResourceAsStream("fonts/Poppins-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("fonts/Poppins-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("fonts/Roboto-Medium.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("fonts/Roboto-Regular.ttf"), 16);

        // Disable resizing
        stage.setResizable(false);

        try {
            Navigator.init(stage, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

