package com.oodj.vaccspace.controllers.reports;

import com.oodj.vaccspace.controllers.BaseController;
import com.oodj.vaccspace.models.Person;
import com.oodj.vaccspace.models.VaccinationStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import textorm.TextORM;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportsController extends BaseController implements Initializable {

    @FXML
    private PieChart pchCitizenNonCitizen;

    @FXML
    private StackedBarChart<String, Number> stcVaccinationStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupStackedChart();

        setupPieChart();
    }

    private void setupPieChart() {
        // https://www.tutorialspoint.com/javafx/pie_chart.htm
        List<Person> people = TextORM.getAll(Person.class, hashMap -> true);

        if (people.size() == 0) {
            pchCitizenNonCitizen.setTitle(pchCitizenNonCitizen.getTitle() + " (No Data)");
            return;
        }

        int totalPeople = people.size();
        int numCitizens = (int) people.stream().filter(person -> !person.isNonCitizen()).count();
        int numNonCitizens = (int) people.stream().filter(person -> person.isNonCitizen()).count();

        double citizenPercentage = (double) numCitizens / totalPeople * 100D;
        double nonCitizenPercentage = (double) numNonCitizens / totalPeople * 100D;

        ObservableList<PieChart.Data> nonCitizenCitizenData = FXCollections.observableArrayList(
                new PieChart.Data(String.format("Non-Citizen (%.2f%%)", nonCitizenPercentage), nonCitizenPercentage),
                new PieChart.Data(String.format("Citizen (%.2f%%)", citizenPercentage), citizenPercentage)
        );

        pchCitizenNonCitizen.setData(nonCitizenCitizenData);

        pchCitizenNonCitizen.setLabelLineLength(25);

        pchCitizenNonCitizen.setLabelsVisible(true);
    }

    private void setupStackedChart() {
        // https://www.tutorialspoint.com/javafx/stacked_bar_chart.htm
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.stream(VaccinationStatus.values())
                                                                    .map(VaccinationStatus::getValue)
                                                                    .collect(Collectors.toList())));
        xAxis.setLabel("Vaccination Status");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("No. of People");

        List<Person> people = TextORM.getAll(Person.class, hashMap -> true);

        if (people.size() == 0) {
            stcVaccinationStatus.setTitle(stcVaccinationStatus.getTitle() + " (No Data)");
            return;
        }

        int unregisteredCitizens = (int) people.stream()
                                               .filter(person -> !person.isNonCitizen() &&
                                                                 person.getVaccinationStatus() ==
                                                                 VaccinationStatus.NOT_REGISTERED
                                               )
                                               .count();

        int firstDoseCitizens = (int) people.stream()
                                            .filter(person -> !person.isNonCitizen() &&
                                                              person.getVaccinationStatus() ==
                                                              VaccinationStatus.AWAITING_FIRST_DOSE
                                            )
                                            .count();

        int secondDoseCitizens = (int) people.stream()
                                             .filter(person -> !person.isNonCitizen() &&
                                                               person.getVaccinationStatus() ==
                                                               VaccinationStatus.AWAITING_SECOND_DOSE
                                             )
                                             .count();

        int vaccinatedCitizens = (int) people.stream()
                                             .filter(person -> !person.isNonCitizen() &&
                                                               person.getVaccinationStatus() ==
                                                               VaccinationStatus.FULLY_VACCINATED
                                             )
                                             .count();

        XYChart.Series<String, Number> citizenSeries = new XYChart.Series<>();
        citizenSeries.setName("Citizen");
        citizenSeries.getData().add(new XYChart.Data<>("Not Registered", unregisteredCitizens));
        citizenSeries.getData().add(new XYChart.Data<>("Awaiting First Dose", firstDoseCitizens));
        citizenSeries.getData().add(new XYChart.Data<>("Awaiting Second Dose", secondDoseCitizens));
        citizenSeries.getData().add(new XYChart.Data<>("Fully Vaccinated", vaccinatedCitizens));

        int unregisteredNonCitizens = (int) people.stream()
                                                  .filter(person -> person.isNonCitizen() &&
                                                                    person.getVaccinationStatus() ==
                                                                    VaccinationStatus.NOT_REGISTERED
                                                  )
                                                  .count();

        int firstDoseNonCitizens = (int) people.stream()
                                               .filter(person -> person.isNonCitizen() &&
                                                                 person.getVaccinationStatus() ==
                                                                 VaccinationStatus.AWAITING_FIRST_DOSE
                                               )
                                               .count();

        int secondDoseNonCitizens = (int) people.stream()
                                                .filter(person -> person.isNonCitizen() &&
                                                                  person.getVaccinationStatus() ==
                                                                  VaccinationStatus.AWAITING_SECOND_DOSE
                                                )
                                                .count();

        int vaccinatedNonCitizens = (int) people.stream()
                                                .filter(person -> person.isNonCitizen() &&
                                                                  person.getVaccinationStatus() ==
                                                                  VaccinationStatus.FULLY_VACCINATED
                                                )
                                                .count();

        XYChart.Series<String, Number> nonCitizenSeries = new XYChart.Series<>();
        nonCitizenSeries.setName("Non-Citizen");
        nonCitizenSeries.getData().add(new XYChart.Data<>("Not Registered", unregisteredNonCitizens));
        nonCitizenSeries.getData().add(new XYChart.Data<>("Awaiting First Dose", firstDoseNonCitizens));
        nonCitizenSeries.getData().add(new XYChart.Data<>("Awaiting Second Dose", secondDoseNonCitizens));
        nonCitizenSeries.getData().add(new XYChart.Data<>("Fully Vaccinated", vaccinatedNonCitizens));

        stcVaccinationStatus.getData().addAll(citizenSeries, nonCitizenSeries);
    }
}
