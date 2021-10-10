package com.oodj.vaccspace;

import com.oodj.vaccspace.models.*;
import com.oodj.vaccspace.utils.Navigator;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import textorm.TextORM;

import java.time.LocalDate;
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

    private void seedModels() {
        Citizen citizen = new Citizen("qwe", "0123456789", "qwe@mail.com", "qwe", VaccinationStatus.NOT_REGISTERED, "010203040506");
        citizen.save();

        VaccinationCenter vc = new VaccinationCenter("Bukit Jalil", CenterStatus.OPEN);
        vc.save();

        VaccineType vt = new VaccineType("Sinovac", 2);
        vt.save();

        VaccineBatch vb = new VaccineBatch(vt.getId(), 200, vc.getId(), LocalDate.now());
        vb.save();

        Vaccine vaccine = new Vaccine(vb.getId(), LocalDate.now(), VaccineStatus.AVAILABLE);
        vaccine.save();

        new Appointment(citizen.getId(), vc.getId(), vaccine.getId(), LocalDate.now(), AppointmentStatus.CONFIRMED, Dose.FIRST).save();
    }
}

