package com.example.listing.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Plan2 implements Serializable {

    @JsonProperty("ZuphrLoadtype")
    String ZuphrLoadtype;

    @JsonProperty("Modified")
    Boolean Modified;

    @JsonProperty("ZuphrActtype")
    String ZuphrActtype;

    @JsonProperty("ZuphrStatus")
    String ZuphrStatus;

    @JsonProperty("ZuphrVesselName")
    String ZuphrVesselName;

    @JsonProperty("ZuphrCaptain")
    String ZuphrCaptain;

    @JsonProperty("ZuphrStation")
    String ZuphrStation;

    @JsonProperty("ZuphrLpid")
    String ZuphrLpid;


    @JsonProperty("Majhr")
    String Majhr;

    @JsonProperty("ZuphrLpname")
    String ZuphrLpname;

    @JsonProperty("ZuphrProfid")
    String ZuphrProfid;


    @JsonProperty("ZuphrRqtype")
    String ZuphrRqtype;


    @JsonProperty("ZuphrLpdate")
    String ZuphrLpdate;

    @JsonProperty("ZuphrLptime")
    String ZuphrLptime;

    @JsonProperty("ZuphrLpuser")
    String ZuphrLpuser;

    @JsonProperty("ZuphrLifnr")
    String ZuphrLifnr;

    @JsonProperty("ZuphrVessel")
    String ZuphrVessel;
    @JsonProperty("ZuphrRealeased")
    Boolean ZuphrRealeased;


    @JsonProperty("ZuphrDeleted")
    Boolean ZuphrDeleted;

    @JsonProperty("ZuphrFpDate")
    String ZuphrFpDate;

    @JsonProperty("ZuphrFpTime")
    String ZuphrFpTime;

    @JsonProperty("ZuphrFpName")
    String ZuphrFpName;


    @JsonProperty("PlanToItems")
    List<Material2> PlanToItems =new ArrayList<>();

    public Plan2() {

    }

    public Plan2(String zuphrLoadtype, Boolean modified, String zuphrActtype, String zuphrStatus,
                 String zuphrVesselName, String zuphrCaptain, String zuphrStation, String zuphrLpid,
                 String majhr, String zuphrLpname, String zuphrProfid, String zuphrRqtype, String zuphrLpdate,
                 String zuphrLptime, String zuphrLpuser, String zuphrLifnr, String zuphrVessel,
                 Boolean zuphrRealeased, Boolean zuphrDeleted, String zuphrFpDate, String zuphrFpTime,
                 String zuphrFpName, List<Material2> planToItems) {
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

    public List<Material2> getPlanToItems() {
        return PlanToItems;
    }

    public void setPlanToItems(List<Material2> planToItems) {
        PlanToItems = planToItems;
    }

    public class PlanUnpack {
        List<Plan2> items = new ArrayList<>();
        Plan2 stageitem =new Plan2();

        public List<Plan2> getItems() {
            return items;
        }

        public void setItems(List<Plan2> items) {
            this.items = items;
        }

        public PlanUnpack() {

        }

        public PlanUnpack(List<Plan2> items) {
            this.items = items;
        }


        @JsonProperty("d")
        public void unpackd(Map<String,List<Plan2>> d) {
            if (d.get("results") != null) {
                items = d.get("results");
            }

        }


    }

}
