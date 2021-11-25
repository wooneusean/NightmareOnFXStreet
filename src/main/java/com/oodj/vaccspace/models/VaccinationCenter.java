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

    @Column
    private String centerAddress;

    @Column
    private String centerPostcode;

    @Column
    private String centerState;

    @HasMany(targetKey = "vaccinationCenterId")
    private List<VaccineBatch> vaccineBatches;

    public VaccinationCenter(String vaccinationCenterName, String centerAddress, String centerPostcode, String centerState, CenterStatus centerStatus) {
        this.vaccinationCenterName = vaccinationCenterName;
        this.centerAddress = centerAddress;
        this.centerPostcode = centerPostcode;
        this.centerState = centerState;
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

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterPostcode() {
        return centerPostcode;
    }

    public void setCenterPostcode(String centerPostcode) {
        this.centerPostcode = centerPostcode;
    }

    public String getCenterState() {
        return centerState;
    }

    public void setCenterState(String centerState) {
        this.centerState = centerState;
    }
}
