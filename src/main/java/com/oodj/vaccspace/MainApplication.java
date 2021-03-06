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
        stage.getIcons()
             .add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("images/vaccine_small.png"))));

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

        // Committee
        Committee committee = new Committee("Khom Mee Tee", "69420", "admin@vaccspace.com", "password", 2600);
        committee.save();

        // Citizen
        Person citizen = new Person(
                "Siti Zheng",
                "0123456789",
                "siti.z@mail.com",
                "password",
                VaccinationStatus.AWAITING_FIRST_DOSE,
                "010203040506",
                false
        );
        citizen.save();

        Person noncitizen = new Person(
                "Naan Siti Zhen",
                "0123456789",
                "n.siti@mail.com",
                "password",
                VaccinationStatus.AWAITING_FIRST_DOSE,
                "010203040506",
                true
        );
        noncitizen.save();

        // Center

        VaccinationCenter bukitJalil = new VaccinationCenter(
                "Bukit Jalil",
                "123",
                "123123",
                "Labuan",
                CenterStatus.OPEN
        );
        bukitJalil.save();

        VaccinationCenter movenpick = new VaccinationCenter(
                "Movenpick",
                "123",
                "123123",
                "Labuan",
                CenterStatus.CLOSED
        );
        movenpick.save();

        VaccinationCenter serdangHospital = new VaccinationCenter(
                "Serdang Hospital",
                "123",
                "123123",
                "Labuan",
                CenterStatus.CLOSED_COVID
        );
        serdangHospital.save();

        // Types

        VaccineType sinovac = new VaccineType("Sinovac", "Sinovac Biotech Ltd", 2);
        sinovac.save();

        VaccineType pfizer = new VaccineType("Pfizer", "Pfizer Inc.", 2);
        pfizer.save();

        VaccineType johnson = new VaccineType("Johnson & Johnson", "Janssen Pharmaceutica", 2);
        johnson.save();

        // Batches

        VaccineBatch sinovacFirstBatch = new VaccineBatch(
                sinovac.getId(),
                200,
                199,
                bukitJalil.getId(),
                LocalDate.now(),
                LocalDate.now().plusYears(3)
        );
        sinovacFirstBatch.save();

        VaccineBatch pfizerBatch1 = new VaccineBatch(
                pfizer.getId(),
                250,
                250,
                bukitJalil.getId(),
                LocalDate.now(),
                LocalDate.now().plusYears(3)
        );
        pfizerBatch1.save();

        VaccineBatch pfizerBatch2 = new VaccineBatch(
                pfizer.getId(),
                250,
                249,
                movenpick.getId(),
                LocalDate.now(),
                LocalDate.now().plusYears(3)
        );
        pfizerBatch2.save();

        VaccineBatch johnsonBatch1 = new VaccineBatch(
                johnson.getId(),
                125,
                125,
                movenpick.getId(),
                LocalDate.now(),
                LocalDate.now().plusYears(3)
        );
        johnsonBatch1.save();

        // Vaccines

        Vaccine sinovacVaccine = new Vaccine(sinovacFirstBatch.getId(), VaccineStatus.AVAILABLE);
        sinovacVaccine.save();

        Vaccine pfizerVaccine = new Vaccine(pfizerBatch1.getId(), VaccineStatus.AVAILABLE);
        pfizerVaccine.save();

        Vaccine johnsonVaccine = new Vaccine(johnsonBatch1.getId(), VaccineStatus.USED);
        johnsonVaccine.save();

        new Appointment(
                citizen.getId(),
                bukitJalil.getId(),
                sinovacVaccine.getId(),
                LocalDate.now().plusDays((long) (Math.random() * 365) + 1),
                AppointmentStatus.CONFIRMED,
                Dose.FIRST
        ).save();

        new Appointment(
                noncitizen.getId(),
                movenpick.getId(),
                pfizer.getId(),
                LocalDate.now().plusDays((long) (Math.random() * 365) + 1),
                AppointmentStatus.CONFIRMED,
                Dose.FIRST
        ).save();
    }
}

