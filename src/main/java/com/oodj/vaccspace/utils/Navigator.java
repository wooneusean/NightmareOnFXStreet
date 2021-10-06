package com.oodj.vaccspace.utils;

import com.oodj.vaccspace.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
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

    /**
     * Initialize the {@link Navigator} with the primary stage and optional initial route
     * @param stage Initialize the navigator on a specific page.
     * @param initialRoute Optional: Set the initial route.
     */
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

    /**
     * Navigate to given in the current window.
     *
     * @param route Route declared in {@link #routeMap}
     */
    public static void navigate(String route) {
        if (tryLoadScene(route, null)) return;

        primaryStage.setScene(scene);
    }

    /**
     * Opens route in new window
     *
     * @param route Route declared in {@link #routeMap}
     */
    public static void openInNewWindow(String route) {
        Stage newStage = createStage();

        if (tryLoadScene(route, newStage)) return;

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
    }

    public static void setWindowTitle(Stage targetStage, String title) {
        Stage stage = targetStage;

        if (stage == null) {
            stage = primaryStage;
        }

        stage.setTitle(String.format("%s | VaccSpace", title));
    }

    private static boolean tryLoadScene(String route, Stage newStage) {
        Page destination = routeMap.get(route);

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(destination.getPath()));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        if (root == null) {
            throw new IllegalArgumentException("Route does not exist!");
        }

        if (destination.getScene() == null) {
            destination.setScene(new Scene(root));
        }

        scene = destination.getScene();

        setWindowTitle(newStage, destination.getDisplayName());
        return false;
    }

    private static void setupOnCloseRequest() {
        primaryStage.setOnCloseRequest(event -> {
            // Add dispose methods here
            Platform.exit(); // GUI thread
            System.exit(0); // Normal exit, kills JVM
        });
    }

    private static Stage createStage() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("images/vaccine.png"))));
        return stage;
    }
}

