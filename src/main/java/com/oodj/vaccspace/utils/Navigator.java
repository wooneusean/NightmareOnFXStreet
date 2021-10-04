package com.oodj.vaccspace.utils;

import com.oodj.vaccspace.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Navigator {
    private static final HashMap<String, Page> routeMap = new HashMap<>() {{
        put("base", new Page("views/base-view.fxml", "No Page"));
        put("login", new Page("views/login-view.fxml", "Login"));
        put("register", new Page("views/register-view.fxml", "Register"));
    }};

    private static Stage primaryStage;
    private static Scene scene = null;
    private static boolean hasBeenInitialized = false;

    public static void navigate(String route) {
        Page destination = routeMap.get(route);

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(destination.getPath()));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (root == null) {
            throw new IllegalArgumentException("Route does not exist!");
        }

        setWindowTitle(destination.getDisplayName());

        if (destination.getScene() == null) {
            destination.setScene(new Scene(root));
        }

        scene = destination.getScene();

        primaryStage.setScene(scene);
    }

    public static void init(Stage stage, String initialRoute) {
        if (hasBeenInitialized) return;

        primaryStage = stage;

        String routeToLoad = initialRoute != null ? initialRoute : "base";

        navigate(routeToLoad);

        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("styles/main.css")).toExternalForm());

        primaryStage.show();

        hasBeenInitialized = true;

        setupOnCloseRequest();
    }

    private static void setupOnCloseRequest() {
        primaryStage.setOnCloseRequest(event -> {
            // Add dispose methods here
            Platform.exit(); // GUI thread
            System.exit(0); // Normal exit, kills JVM
        });
    }

    public static void setWindowTitle(String title) {
        primaryStage.setTitle(String.format("%s | VaccSpace", title));
    }
}

