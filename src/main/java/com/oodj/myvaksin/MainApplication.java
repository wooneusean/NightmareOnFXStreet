package com.oodj.myvaksin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {
    public static Stage parentWindow;

    @Override
    public void start(Stage stage) throws IOException {
        parentWindow = stage;
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("hello-view.fxml"));

        Scene scene = new Scene(root);
        parentWindow.setTitle("Hello!");
        parentWindow.setScene(scene);
        parentWindow.show();

        parentWindow.setOnCloseRequest(event -> {
            // Add dispose methods here
            Platform.exit(); // GUI thread
            System.exit(0); // Normal exit, kills JVM
        });
    }

    public static void main(String[] args) {
        launch();
    }
}

