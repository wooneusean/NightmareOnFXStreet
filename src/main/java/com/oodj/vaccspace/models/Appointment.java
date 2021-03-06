package com.oodj.vaccspace.models;

import textorm.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class Appointment extends Model {

    @HasOne(foreignKey = "personId")
    private Person person;

    @Column
    private int personId;

    @Column
    private int vaccinationCenterId;

    @HasOne(foreignKey = "vaccinationCenterId")
    private VaccinationCenter vaccinationCenter;

    @Column
    private int vaccineId;

    @HasOne(foreignKey = "vaccineId")
    private Vaccine vaccine;

    @Column
    private LocalDate appointmentDate;

    @Column
    private AppointmentStatus appointmentStatus;

    @Column
    private Dose dose;

    public Appointment(
            int personId,
            int vaccinationCenterId,
            int vaccineId,
            LocalDate appointmentDate,
            AppointmentStatus appointmentStatus,
            Dose dose
    ) {
        this.personId = personId;
        this.vaccinationCenterId = vaccinationCenterId;
        this.vaccineId = vaccineId;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus;
        this.dose = dose;
    }

    public Appointment() {
    }

    public VaccinationCenter getVaccinationCenter() {
        this.include(VaccinationCenter.class);
        return vaccinationCenter;
    }

    public void setVaccinationCenter(VaccinationCenter vaccinationCenter) {
        this.vaccinationCenter = vaccinationCenter;
        this.vaccinationCenterId = vaccinationCenter.getId();
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
        this.vaccineId = vaccine.getId();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.personId = person.getId();
    }

    public int getVaccinationCenterId() {
        return vaccinationCenterId;
    }

    public void setVaccinationCenterId(int vaccinationCenterId) {
        this.vaccinationCenterId = vaccinationCenterId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Dose getDose() {
        return dose;
    }

    public void setDose(Dose dose) {
        this.dose = dose;
    }

    public void confirmAppointment() {
        appointmentStatus = AppointmentStatus.CONFIRMED;
    }

    public void fulfillAppointment() {
        this.include(Vaccine.class);
        this.getVaccine().include(VaccineBatch.class);
        this.getVaccine().getVaccineBatch().include(VaccineType.class);
        int doses = this.getVaccine().getVaccineBatch().getVaccineType().getDosesNeeded();

        include(Person.class);
        if (doses == 1) {
            getPerson().setVaccinationStatus(VaccinationStatus.FULLY_VACCINATED);
        } else {
            List<Appointment> previousAppointments = TextORM.getAll(
                    Appointment.class,
                    hashMap -> Integer.parseInt(hashMap.get("personId")) == getPerson().getId()
            );

            if (previousAppointments.size() > 0) {
                previousAppointments.sort(Comparator.comparing(Appointment::getAppointmentDate));

                boolean hasFulfilled = previousAppointments.stream()
                                                           .anyMatch(
                                                                   appointment -> appointment
                                                                           .getAppointmentStatus()
                                                                           .equals(AppointmentStatus.FULFILLED)
                                                           );
                if (hasFulfilled) {
                    getPerson().setVaccinationStatus(VaccinationStatus.FULLY_VACCINATED);
                } else {
                    getPerson().setVaccinationStatus(VaccinationStatus.AWAITING_SECOND_DOSE);
                }
            } else {
                getPerson().setVaccinationStatus(VaccinationStatus.AWAITING_SECOND_DOSE);
            }
        }

        appointmentStatus = AppointmentStatus.FULFILLED;
        save();
    }

    public void absentAppointment() {
        appointmentStatus = AppointmentStatus.ABSENT;

        include(Vaccine.class);
        getVaccine().include(VaccineBatch.class);
        getVaccine().getVaccineBatch().setAvailableAmount(getVaccine().getVaccineBatch().getAvailableAmount() + 1);

        save();

        include(Person.class);
        Person person = getPerson();
        person.include(Appointment.class);

        List<Appointment> appointments = person.getAppointments()
                                               .stream()
                                               .filter(appointment -> appointment.getAppointmentStatus() ==
                                                                      AppointmentStatus.FULFILLED)
                                               .collect(Collectors.toList());

        boolean hasFulfilledAppointments = appointments.stream()
                                                       .anyMatch(appointment ->
                                                                         appointment.getAppointmentStatus() ==
                                                                         AppointmentStatus.FULFILLED);

        boolean hasConfirmedAppointments = appointments.stream()
                                                       .anyMatch(appointment ->
                                                                         appointment.getAppointmentStatus() ==
                                                                         AppointmentStatus.CONFIRMED);

        if (hasFulfilledAppointments) {
            person.setVaccinationStatus(VaccinationStatus.AWAITING_SECOND_DOSE);
        } else if (hasConfirmedAppointments) {
            person.setVaccinationStatus(VaccinationStatus.AWAITING_FIRST_DOSE);
        } else {
            person.setVaccinationStatus(VaccinationStatus.NOT_REGISTERED);
        }
        person.save();


    }

    //
    // For use with table
    //
    public String getAppointmentCenterName() {
        return getVaccinationCenter().getVaccinationCenterName();
    }

    public String getVaccineName() {
        include(Vaccine.class);
        this.getVaccine().include(VaccineBatch.class);
        this.getVaccine().getVaccineBatch().include(VaccineType.class);
        return this.getVaccine().getVaccineBatch().getVaccineType().getVaccineName();
    }

    public void cancel() {
        // Doing this cuz its a shortcut
        // to include all the needed models
        getVaccineName();

        setAppointmentStatus(AppointmentStatus.VOIDED);
        getVaccine().getVaccineBatch().setAvailableAmount(getVaccine().getVaccineBatch().getAvailableAmount() + 1);
        save();

        getVaccine().setVaccineStatus(VaccineStatus.VOIDED);

        include(Person.class);
        Person person = getPerson();
        person.include(Appointment.class);

        List<Appointment> appointments = person.getAppointments()
                                               .stream()
                                               .filter(appointment -> appointment.getAppointmentStatus() ==
                                                                      AppointmentStatus.FULFILLED)
                                               .collect(Collectors.toList());

        boolean hasFulfilledAppointments = appointments.stream()
                                                       .anyMatch(appointment ->
                                                                         appointment.getAppointmentStatus() ==
                                                                         AppointmentStatus.FULFILLED);

        boolean hasConfirmedAppointments = appointments.stream()
                                                       .anyMatch(appointment ->
                                                                         appointment.getAppointmentStatus() ==
                                                                         AppointmentStatus.CONFIRMED);

        if (hasFulfilledAppointments) {
            person.setVaccinationStatus(VaccinationStatus.AWAITING_SECOND_DOSE);
        } else if (hasConfirmedAppointments) {
            person.setVaccinationStatus(VaccinationStatus.AWAITING_FIRST_DOSE);
        } else {
            person.setVaccinationStatus(VaccinationStatus.NOT_REGISTERED);
        }
        person.save();
    }
}
