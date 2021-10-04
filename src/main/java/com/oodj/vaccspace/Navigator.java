package com.oodj.vaccspace;

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

    static Scene navigate(String route) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(routeMap.get(route).getPath()));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (root == null) {
            throw new IllegalArgumentException("Route does not exist!");
        }

        setWindowTitle(routeMap.get(route).getDisplayName());

        scene = new Scene(root);
        primaryStage.setScene(scene);
        return scene;
    }

    static void init(Stage stage, String initialRoute) {
        if (hasBeenInitialized) return;

        primaryStage = stage;

        String routeToLoad = initialRoute != null ? initialRoute : "base";

        navigate(routeToLoad);

        scene.getStylesheets().add(Objects.requireNonNull(Navigator.class.getResource("styles/main.css")).toExternalForm());

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

class Page {
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
}