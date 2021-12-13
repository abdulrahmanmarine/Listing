package com.example.listing.models;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "PlanTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan implements Serializable {

    @Expose(serialize = false,deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int PlanId;

    @JsonProperty("ZuphrActquan")
    @ColumnInfo(name="ZuphrActquan")
    public String ZuphrActquan;

    @JsonIgnore
    @Ignore
    LoadAction ZuphrLoada;


    @JsonProperty("ZuphrLoada")
    @Ignore
    public void unpackload(Map<String, List<LoadAction>> d) {
       Log.i("loadaction",d.keySet().toString());
    }

    @JsonProperty("ZuphrLoadtype")
    @ColumnInfo(name="ZuphrLoadtype")
    public String ZuphrLoadtype;


    @JsonProperty("ZpuhrMovtype")
    @ColumnInfo(name="ZpuhrMovtype")
     public String ZpuhrMovtype;

    @JsonProperty("ZuphrFreestock")
    @ColumnInfo(name="ZuphrFreestock")
    public boolean ZuphrFreestock;



    @JsonProperty("Modified")
    @ColumnInfo(name="Modified")
     public boolean Modified;

    @JsonProperty("ZuphrActtype")
    @ColumnInfo(name="ZuphrActtype")
    public String ZuphrActtype;



    @JsonProperty("ZuphrContents")
    @ColumnInfo(name="ZuphrContents")
    public String ZuphrContents;


    @JsonProperty("ZuphrHeight")
    @ColumnInfo(name="ZuphrHeight")
    public String ZuphrHeight;

    @JsonProperty("ZuphrLength")
    @ColumnInfo(name="ZuphrLength")
    public String ZuphrLength;


    @JsonProperty("ZuphrWidth")
    @ColumnInfo(name="ZuphrWidth")
    public String ZuphrWidth;

    @JsonProperty("ZuphrTo")
    @ColumnInfo(name="ZuphrTo")
    public String ZuphrOffshore;

    @JsonProperty("ZuphrVolmeins")
    @ColumnInfo(name="ZuphrVolmeins")
    public String ZuphrVolmeins;


    @JsonProperty("ZuphrMatnr")
    @ColumnInfo(name="ZuphrMatnr")
    public String ZuphrMatnr;



    @JsonProperty("ZuphrMjahr")
    @ColumnInfo(name="ZuphrMjahr")
    public String ZuphrMjahr;


    @JsonProperty("ZuphrClass")
    @ColumnInfo(name="ZuphrClass")
    public String ZuphrClass;


    @JsonProperty("ZuphrMattype")
    @ColumnInfo(name="ZuphrMattype")
    public String ZuphrMattype;

    @JsonProperty("ZuphrDeleted")
    @ColumnInfo(name="ZuphrDeleted")
    public boolean ZuphrDeleted;


    @JsonProperty("ZuphrCostcen")
    @ColumnInfo(name="ZuphrCostcen")
    public String ZuphrCostcen;

    @JsonProperty("ZuphrGl")
    @ColumnInfo(name="ZuphrGl")
    public String ZuphrGl;

    @JsonProperty("ZuphrBitstatus")
    @ColumnInfo(name="ZuphrBitstatus")
    public String ZuphrBitstatus;

    @JsonProperty("ZuphrBitType")
    @ColumnInfo(name="ZuphrBitType")
    public String ZuphrBitType;

    @JsonProperty("ZuphrMfrpn")
    @ColumnInfo(name="ZuphrMfrpn")
    public String ZuphrMfrpn;

    @JsonProperty("ZuphrTicketno")
    @ColumnInfo(name="ZuphrTicketno")
    public String ZuphrTicketno;


    @JsonProperty("ZuphrCnumber")
    @ColumnInfo(name="ZuphrCnumber")
    public String ZuphrCnumber;

    @JsonProperty("ZuphrEquipnumber")
    @ColumnInfo(name="ZuphrEquipnumber")
    public String ZuphrEquipnumber;

    @JsonProperty("ZuphrWellnm")
    @ColumnInfo(name="ZuphrWellnm")
    public String ZuphrWellnm;


    @JsonProperty("ZuphrActtime")
    @ColumnInfo(name="ZuphrActtime")
    public String ZuphrActtime;

    @JsonProperty("ZuphrActdate")
    @ColumnInfo(name="ZuphrActdate")
    public String ZuphrActdate;

    @JsonProperty("ZuphrActuname")
    @ColumnInfo(name="ZuphrActuname")
    public String ZuphrActuname;

    @JsonProperty("ZuphrSent")
    @ColumnInfo(name="ZuphrSent")
    public String ZuphrSent;

    @JsonProperty("ZuphrActrole")
    @ColumnInfo(name="ZuphrActrole")
    public String ZuphrActrole;

    @JsonProperty("ZuphrActposgrp")
    @ColumnInfo(name="ZuphrActposgrp")
    public String ZuphrActposgrp;


    @JsonProperty("ZuphrAutoact")
    @ColumnInfo(name="ZuphrAutoact")
    public String ZuphrAutoact;


    @JsonProperty("ZuphrSrlno")
    @ColumnInfo(name="ZuphrSrlno")
    public String ZuphrSrlno;

    @JsonProperty("ZuphrMblpo")
    @ColumnInfo(name="ZuphrMblpo")
    public String ZuphrMblpo;


    @JsonProperty("ZuphrStgid")
    @ColumnInfo(name="ZuphrStgid")
    public String ZuphrStgid;

    @JsonProperty("ZuphrReqid")
    @ColumnInfo(name="ZuphrReqid")
    public String ZuphrReqid;

    @JsonProperty("ZuphrReqitm")
    @ColumnInfo(name="ZuphrReqitm")
    public String ZuphrReqitm;

    @JsonProperty("ZuphrShortxt")
    @ColumnInfo(name="ZuphrShortxt")
    public String ZuphrShortxt;


    @JsonProperty("ZuphrDescrip")
    @ColumnInfo(name="ZuphrDescrip")
    public String ZuphrDescrip;

    @JsonProperty("ZuphrQuan")
    @ColumnInfo(name="ZuphrQuan")
    public String ZuphrQuan;


    @JsonProperty("Meins")
    @ColumnInfo(name="Meins")
    public String Meins;

    @JsonProperty("ZuphrOffshore")
    @ColumnInfo(name="ZuphrOffshore")
    public String ZuphrOffshore2;



    @JsonProperty("ZuphrAreacode")
    @ColumnInfo(name="ZuphrAreacode")
    public String ZuphrAreacode;

    @JsonProperty("ZuphrStatus")
    @ColumnInfo(name="ZuphrStatus")
    public String ZuphrStatus;






    @JsonProperty("ZuphrVesselName")
    @ColumnInfo(name="ZuphrVesselName")
    public String ZuphrVesselName;

    @JsonProperty("ZuphrCaptain")
    @ColumnInfo(name="ZuphrCaptain")
    public String ZuphrCaptain;

    @JsonProperty("ZuphrStation")
    @ColumnInfo(name="ZuphrStation")
    public String ZuphrStation;

    @JsonProperty("ZuphrLpid")
    @ColumnInfo(name="ZuphrLpid")
    public String ZuphrLpid;

    @JsonProperty("ZuphrObjecte")
    @ColumnInfo(name="ZuphrObjecte")
    public String ZuphrObjecte;


    @JsonProperty("ZuphrFrom")
    @ColumnInfo(name="ZuphrFrom")
    public String ZuphrFrom;


    @JsonProperty("Majhr")
    @ColumnInfo(name="Majhr")
    public String Majhr;

    @JsonProperty("ZuphrLpname")
    @ColumnInfo(name="ZuphrLpname")
    public String ZuphrLpname;

    @JsonProperty("ZuphrProfid")
    @ColumnInfo(name="ZuphrProfid")
    public String ZuphrProfid;


    @JsonProperty("ZuphrRqtype")
    @ColumnInfo(name="ZuphrRqtype")
    public String ZuphrRqtype;


    @JsonProperty("ZuphrLpdate")
    @ColumnInfo(name="ZuphrLpdate")
    public String ZuphrLpdate;

    @JsonProperty("ZuphrLptime")
    @ColumnInfo(name="ZuphrLptime")
    public String ZuphrLptime;

    @JsonProperty("ZuphrLpuser")
    @ColumnInfo(name="ZuphrLpuser")
    public String ZuphrLpuser;

    @JsonProperty("ZuphrLifnr")
    @ColumnInfo(name="ZuphrLifnr")
    public String ZuphrLifnr;

    @JsonProperty("ZuphrVessel")
    @ColumnInfo(name="ZuphrVessel")
    public String ZuphrVessel;


    @JsonProperty("ZuphrRealeased")
    @ColumnInfo(name="ZuphrRealeased")
    public boolean ZuphrRealeased;


    @JsonProperty("ZuphrFpDate")
    @ColumnInfo(name="ZuphrFpDate")
    public String ZuphrFpDate;

    @JsonProperty("ZuphrFpTime")
    @ColumnInfo(name="ZuphrFpTime")
    public String ZuphrFpTime;

    @JsonProperty("ZuphrFpName")
    @ColumnInfo(name="ZuphrFpName")
    public String ZuphrFpName;



    @JsonProperty("ZuphrSeq")
    @ColumnInfo(name="ZuphrSeq")
    public String ZuphrSeq;


    @JsonProperty("ZuphrSchtask")
    @ColumnInfo(name="ZuphrSchtask")
    public String ZuphrSchtask;



    @Ignore
    @JsonIgnore
    List<Material> PlanToItems ;

    @JsonProperty("PlanToItems")
    public void unpackd(Map<String, List<Material>> d) {
        if (d.get("results") != null) {
            PlanToItems = d.get("results");
        }

    }





    public Plan() {

    }

    public Plan(String zuphrLoadtype, Boolean modified, String zuphrActtype, String zuphrStatus,
                 String zuphrVesselName, String zuphrCaptain, String zuphrStation, String zuphrLpid,
                 String majhr, String zuphrLpname, String zuphrProfid, String zuphrRqtype, String zuphrLpdate,
                 String zuphrLptime, String zuphrLpuser, String zuphrLifnr, String zuphrVessel,
                 Boolean zuphrRealeased, Boolean zuphrDeleted, String zuphrFpDate, String zuphrFpTime,
                 String zuphrFpName, List<Material> planToItems) {
        ZuphrLoadtype = zuphrLoadtype;
        Modified = modified;
        ZuphrActtype = zuphrActtype;
        ZuphrStatus = zuphrStatus;
        ZuphrVesselName = zuphrVesselName;
        ZuphrCaptain = zuphrCaptain;
        ZuphrStation = zuphrStation;
        ZuphrLpid = zuphrLpid;
        Majhr = majhr;
        ZuphrLpname = zuphrLpname;
        ZuphrProfid = zuphrProfid;
        ZuphrRqtype = zuphrRqtype;
        ZuphrLpdate = zuphrLpdate;
        ZuphrLptime = zuphrLptime;
        ZuphrLpuser = zuphrLpuser;
        ZuphrLifnr = zuphrLifnr;
        ZuphrVessel = zuphrVessel;
        ZuphrRealeased = zuphrRealeased;
        ZuphrDeleted = zuphrDeleted;
        ZuphrFpDate = zuphrFpDate;
        ZuphrFpTime = zuphrFpTime;
        ZuphrFpName = zuphrFpName;
        PlanToItems = planToItems;
    }

    public String getZuphrLoadtype() {
        return ZuphrLoadtype;
    }

    public void setZuphrLoadtype(String zuphrLoadtype) {
        ZuphrLoadtype = zuphrLoadtype;
    }

    public Boolean getModified() {
        return Modified;
    }

    public void setModified(Boolean modified) {
        Modified = modified;
    }

    public String getZuphrActtype() {
        return ZuphrActtype;
    }

    public void setZuphrActtype(String zuphrActtype) {
        ZuphrActtype = zuphrActtype;
    }

    public String getZuphrStatus() {
        return ZuphrStatus;
    }

    public void setZuphrStatus(String zuphrStatus) {
        ZuphrStatus = zuphrStatus;
    }

    public String getZuphrVesselName() {
        return ZuphrVesselName;
    }

    public void setZuphrVesselName(String zuphrVesselName) {
        ZuphrVesselName = zuphrVesselName;
    }

    public String getZuphrCaptain() {
        return ZuphrCaptain;
    }

    public void setZuphrCaptain(String zuphrCaptain) {
        ZuphrCaptain = zuphrCaptain;
    }

    public String getZuphrStation() {
        return ZuphrStation;
    }

    public void setZuphrStation(String zuphrStation) {
        ZuphrStation = zuphrStation;
    }

    public String getZuphrLpid() {
        return ZuphrLpid;
    }

    public void setZuphrLpid(String zuphrLpid) {
        ZuphrLpid = zuphrLpid;
    }

    public String getMajhr() {
        return Majhr;
    }

    public void setMajhr(String majhr) {
        Majhr = majhr;
    }

    public String getZuphrLpname() {
        return ZuphrLpname;
    }

    public void setZuphrLpname(String zuphrLpname) {
        ZuphrLpname = zuphrLpname;
    }

    public String getZuphrProfid() {
        return ZuphrProfid;
    }

    public void setZuphrProfid(String zuphrProfid) {
        ZuphrProfid = zuphrProfid;
    }

    public String getZuphrRqtype() {
        return ZuphrRqtype;
    }

    public void setZuphrRqtype(String zuphrRqtype) {
        ZuphrRqtype = zuphrRqtype;
    }

    public String getZuphrLpdate() {
        return ZuphrLpdate;
    }

    public void setZuphrLpdate(String zuphrLpdate) {
        ZuphrLpdate = zuphrLpdate;
    }

    public String getZuphrLptime() {
        return ZuphrLptime;
    }

    public void setZuphrLptime(String zuphrLptime) {
        ZuphrLptime = zuphrLptime;
    }

    public String getZuphrLpuser() {
        return ZuphrLpuser;
    }

    public void setZuphrLpuser(String zuphrLpuser) {
        ZuphrLpuser = zuphrLpuser;
    }

    public String getZuphrLifnr() {
        return ZuphrLifnr;
    }

    public void setZuphrLifnr(String zuphrLifnr) {
        ZuphrLifnr = zuphrLifnr;
    }

    public String getZuphrVessel() {
        return ZuphrVessel;
    }

    public void setZuphrVessel(String zuphrVessel) {
        ZuphrVessel = zuphrVessel;
    }

    public Boolean getZuphrRealeased() {
        return ZuphrRealeased;
    }

    public void setZuphrRealeased(Boolean zuphrRealeased) {
        ZuphrRealeased = zuphrRealeased;
    }

    public Boolean getZuphrDeleted() {
        return ZuphrDeleted;
    }

    public void setZuphrDeleted(Boolean zuphrDeleted) {
        ZuphrDeleted = zuphrDeleted;
    }

    public String getZuphrFpDate() {
        return ZuphrFpDate;
    }

    public void setZuphrFpDate(String zuphrFpDate) {
        ZuphrFpDate = zuphrFpDate;
    }

    public String getZuphrFpTime() {
        return ZuphrFpTime;
    }

    public void setZuphrFpTime(String zuphrFpTime) {
        ZuphrFpTime = zuphrFpTime;
    }

    public String getZuphrFpName() {
        return ZuphrFpName;
    }

    public void setZuphrFpName(String zuphrFpName) {
        ZuphrFpName = zuphrFpName;
    }

    public List<Material> getPlanToItems() {
        return PlanToItems;
    }

    public void setPlanToItems(List<Material> planToItems) {
        PlanToItems = planToItems;
    }

    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }


}

