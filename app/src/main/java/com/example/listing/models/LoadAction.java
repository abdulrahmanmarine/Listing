package com.example.listing.models;

import android.icu.util.Calendar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadAction implements Serializable {

    @JsonProperty("zuphrLpid")
    String zuphrLpid = "";

    @JsonProperty("zuphrMjahr")
    String zuphrMjahr = "";
    @JsonProperty("zuphrMblpo")
    String zuphrMblpo = "";
    @JsonProperty("zuphrLoadid")
    String zuphrLoadid = "";
    @JsonProperty("zuphrActtype")
    String zuphrActtype = "";
    @JsonProperty("zuphrAsinQuan")
    String assignedQuan = 0.0 + "";
    @JsonProperty("zuphrMeins")
    String materialUnit = "";

    @JsonIgnore
    Boolean zuphrReady = false;
    @JsonProperty("zuphrFpDate")
    String FpDate = "";
    @JsonProperty("zuphrFpTime")
    String FpTime = "";
    @JsonProperty("zuphrFpName")
    String FpName = "";
    @JsonProperty("zuphrSize")
    String zuphrSize = "";
    @JsonProperty("zuphrConfQuan")
    String confirmedQuan = 0.0 + "";
    @JsonProperty("zuphrAct")
    String zuphrAct = "";
    @JsonProperty("zuphrDriver")
    String driver = "";
    @JsonProperty("zuphrWeight")
    String weight = "";
    @JsonProperty("zuphrVehType")
    String vehicle = "";
    @JsonProperty("zuphrStatus")
    String status = "";
    @JsonProperty("zuphrContents")
    String content = "";

    public LoadAction(String zuphrLpid, String zuphrMjahr, String zuphrMblpo, String zuphrLoadid,
                      String zuphrActtype, String assignedQuan, String materialUnit, Boolean zuphrReady,
                      String fpDate, String fpTime, String fpName, String zuphrSize, String confirmedQuan,
                      String zuphrAct, String driver, String weight, String vehicle, String status, String content) {
        this.zuphrLpid = zuphrLpid;
        this.zuphrMjahr = zuphrMjahr;
        this.zuphrMblpo = zuphrMblpo;
        this.zuphrLoadid = zuphrLoadid;
        this.zuphrActtype = zuphrActtype;
        this.assignedQuan = assignedQuan;
        this.materialUnit = materialUnit;
        this.zuphrReady = zuphrReady;
        FpDate = fpDate;
        FpTime = fpTime;
        FpName = fpName;
        this.zuphrSize = zuphrSize;
        this.confirmedQuan = confirmedQuan;
        this.zuphrAct = zuphrAct;
        this.driver = driver;
        this.weight = weight;
        this.vehicle = vehicle;
        this.status = status;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("zuphrReady")
    private void setReady(String flag) {
        if (flag == "X") {
            zuphrReady = true;
        }

    }

    public String getZuphrLpid() {
        return zuphrLpid;
    }

    public void setZuphrLpid(String zuphrLpid) {
        this.zuphrLpid = zuphrLpid;
    }

    public String getZuphrMjahr() {
        return zuphrMjahr;
    }

    public void setZuphrMjahr(String zuphrMjahr) {
        this.zuphrMjahr = zuphrMjahr;
    }

    public String getZuphrMblpo() {
        return zuphrMblpo;
    }

    public void setZuphrMblpo(String zuphrMblpo) {
        this.zuphrMblpo = zuphrMblpo;
    }

    public String getZuphrLoadid() {
        return zuphrLoadid;
    }

    public void setZuphrLoadid(String zuphrLoadid) {
        this.zuphrLoadid = zuphrLoadid;
    }

    public String getZuphrActtype() {
        return zuphrActtype;
    }

    public void setZuphrActtype(String zuphrActtype) {
        this.zuphrActtype = zuphrActtype;
    }

    public String getAssignedQuan() {
        return assignedQuan;
    }

    public void setAssignedQuan(String assignedQuan) {
        this.assignedQuan = assignedQuan;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public Boolean getZuphrReady() {
        return zuphrReady;
    }

    public void setZuphrReady(Boolean zuphrReady) {
        this.zuphrReady = zuphrReady;
    }

    public String getFpDate() {
        return FpDate;
    }

    public void setFpDate(String fpDate) {
        FpDate = fpDate;
    }

    public String getFpTime() {
        return FpTime;
    }

    public void setFpTime(String fpTime) {
        FpTime = fpTime;
    }

    public String getFpName() {
        return FpName;
    }

    public void setFpName(String fpName) {
        FpName = fpName;
    }

    public LoadAction() {
    }
}
