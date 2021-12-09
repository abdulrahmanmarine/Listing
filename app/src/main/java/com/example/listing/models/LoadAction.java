package com.example.listing.models;

import android.icu.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "LoadActionTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoadAction implements Serializable {

    @Expose(serialize = false,deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int LoadActionId;

    @Expose(serialize = false,deserialize = false)
    @ColumnInfo(name = "PlanOfflineID")
    @JsonIgnore
    public  String PlanOfflineID;


    @JsonProperty("zuphrLpid")
    @ColumnInfo(name="zuphrLpid")
    String zuphrLpid = "";

    @JsonProperty("zuphrMjahr")
    @ColumnInfo(name="zuphrMjahr")
    String zuphrMjahr = "";


    @JsonProperty("zuphrMblpo")
    @ColumnInfo(name="zuphrMblpo")
    String zuphrMblpo = "";


    @JsonProperty("zuphrLoadid")
    @ColumnInfo(name="zuphrLoadid")
    String zuphrLoadid = "";

    @JsonProperty("zuphrActtype")
    @ColumnInfo(name="zuphrActtype")
    String zuphrActtype = "";


    @JsonProperty("zuphrAsinQuan")
    @ColumnInfo(name="zuphrAsinQuan")
    String assignedQuan = 0.0 + "";


    @JsonProperty("zuphrMeins")
    @ColumnInfo(name="zuphrMeins")
    String materialUnit = "";

    @JsonIgnore
    @Ignore
    Boolean zuphrReady = false;


    @JsonProperty("zuphrFpDate")
    @ColumnInfo(name="zuphrFpDate")
    String FpDate = "";


    @JsonProperty("zuphrFpTime")
    @ColumnInfo(name="zuphrFpTime")
    String FpTime = "";
    @JsonProperty("zuphrFpName")
    @ColumnInfo(name="FpName")
    String FpName = "";

    @JsonProperty("zuphrVehType")
    @Ignore
    List<Vehicle> vehicle = new ArrayList<>();

    @JsonIgnore
    @ColumnInfo(name="zuphrStatus")
    String status = "";

    @JsonIgnore
    @Ignore
    String zuphrSize = "";
    @JsonIgnore
    @Ignore
    String confirmedQuan = 0.0 + "";
    @JsonIgnore
    @Ignore
    String zuphrAct = "";
    @JsonIgnore
    @Ignore
    List<Driver> driver = new ArrayList<>();
    @JsonIgnore
    @Ignore
    String weight = "";



    public LoadAction(String zuphrLpid, String zuphrMjahr, String zuphrMblpo, String zuphrLoadid,
                      String zuphrActtype, String assignedQuan, String materialUnit, Boolean zuphrReady,
                      String fpDate, String fpTime, String fpName, String zuphrSize, String confirmedQuan,
                      String zuphrAct, List<Driver> driver, String weight, List<Vehicle> vehicle, String status) {
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


    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Vehicle> vehicle) {
        this.vehicle = vehicle;
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
