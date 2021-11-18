package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Model;
import textorm.Repository;

import java.util.List;

@Repository
public class VaccinationCenter extends Model {

    @Column
    private String vaccinationCenterName;
    @Column
    private CenterStatus centerStatus;
    //    @Column
//    private Address address;
    @HasMany(targetKey = "vaccinationCenterId")
    private List<VaccineBatch> vaccineBatches;

    public VaccinationCenter(String vaccinationCenterName, CenterStatus centerStatus) {
        this.vaccinationCenterName = vaccinationCenterName;
        this.centerStatus = centerStatus;
    }

    public VaccinationCenter() {
    }

    public List<VaccineBatch> getVaccineBatches() {
        return vaccineBatches;
    }

    @Override
    public String toString() {
        return vaccinationCenterName;
    }

    public String getVaccinationCenterName() {
        return vaccinationCenterName;
    }

    public void setVaccinationCenterName(String vaccinationCenterName) {
        this.vaccinationCenterName = vaccinationCenterName;
    }

    public CenterStatus getCenterStatus() {
        return centerStatus;
    }

    public void setCenterStatus(CenterStatus centerStatus) {
        this.centerStatus = centerStatus;
    }
}
