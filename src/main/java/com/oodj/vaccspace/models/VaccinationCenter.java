package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.HasMany;
import textorm.Model;
import textorm.Repository;

@Repository
public class VaccinationCenter extends Model {

    @Column
    private String vaccinationCenterName;

//    @Column
//    private Address address;

    @Column
    private CenterStatus centerStatus;

    @HasMany(targetKey = "vaccinationCenterId")
    private VaccineBatch[] vaccineBatches;

    public VaccinationCenter(String vaccinationCenterName, CenterStatus centerStatus) {
        this.vaccinationCenterName = vaccinationCenterName;
        this.centerStatus = centerStatus;
    }

    public VaccinationCenter() {
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
