package com.oodj.vaccspace;

import com.oodj.vaccspace.models.*;
import com.oodj.vaccspace.utils.Navigator;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import textorm.TextORM;

import java.nio.file.Files;
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

        seedModels();

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
        if (Files.exists(TextORM.getMetaStoragePath())) {
            return;
        }

        // Citizen
        Citizen citizen1 = new Citizen("qwe", "0123456789", "qwe@mail.com", "qwe", VaccinationStatus.NOT_REGISTERED, "010203040506");
        citizen1.save();

        Citizen citizen2 = new Citizen("asd", "0123456789", "asd@mail.com", "asd", VaccinationStatus.AWAITING_FIRST_DOSE, "010203040506");
        citizen2.save();

        // Center

        VaccinationCenter bukitJalil = new VaccinationCenter("Bukit Jalil", CenterStatus.OPEN);
        bukitJalil.save();

        VaccinationCenter movenpick = new VaccinationCenter("Movenpick", CenterStatus.OPEN);
        movenpick.save();

        // Types

        VaccineType sinovac = new VaccineType("Sinovac", 2);
        sinovac.save();

        VaccineType pfizer = new VaccineType("Pfizer", 2);
        pfizer.save();

        VaccineType johnson = new VaccineType("Johnson & Johnson", 2);
        johnson.save();

        // Batches

        VaccineBatch sinovacFirstBatch = new VaccineBatch(sinovac.getId(), 200, 200, bukitJalil.getId(), LocalDate.now(), LocalDate.now().plusYears(3));
        sinovacFirstBatch.save();

        VaccineBatch pfizerBatch1 = new VaccineBatch(pfizer.getId(), 250, 250, bukitJalil.getId(), LocalDate.now(), LocalDate.now().plusYears(3));
        pfizerBatch1.save();

        VaccineBatch pfizerBatch2 = new VaccineBatch(pfizer.getId(), 250, 250, movenpick.getId(), LocalDate.now(), LocalDate.now().plusYears(3));
        pfizerBatch2.save();

        VaccineBatch johnsonBatch1 = new VaccineBatch(johnson.getId(), 125, 125, movenpick.getId(), LocalDate.now(), LocalDate.now().plusYears(3));
        johnsonBatch1.save();

        // Vaccines

        Vaccine sinovacVaccine = new Vaccine(sinovacFirstBatch.getId(), VaccineStatus.AVAILABLE);
        sinovacVaccine.save();

        Vaccine pfizerVaccine = new Vaccine(pfizerBatch1.getId(), VaccineStatus.AVAILABLE);
        pfizerVaccine.save();

        Vaccine johnsonVaccine = new Vaccine(johnsonBatch1.getId(), VaccineStatus.USED);
        johnsonVaccine.save();

        new Appointment(citizen1.getId(), bukitJalil.getId(), sinovacVaccine.getId(), LocalDate.now(), AppointmentStatus.CONFIRMED, Dose.FIRST).save();

        new Appointment(citizen2.getId(), movenpick.getId(), johnsonVaccine.getId(), LocalDate.now(), AppointmentStatus.AWAITING_CONFIRMATION, Dose.FIRST).save();
    }
}

