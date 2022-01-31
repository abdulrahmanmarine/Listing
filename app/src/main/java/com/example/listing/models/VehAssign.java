package com.example.listing.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;


@Entity(tableName = "MatrialDispatchingTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehAssign implements Serializable {

    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int Dispatch;

    @JsonProperty("ZuphrLpid")
    @ColumnInfo(name = "ZuphrLpid")
    String ZuphrLpid;

    @JsonProperty("ZuphrMjahr")
    @ColumnInfo(name = "ZuphrMjahr")
    String ZuphrMjahr;

    @JsonProperty("ZuphrMblpo")
    @ColumnInfo(name = "ZuphrMblpo")
    String ZuphrMblpo;


    @JsonProperty("ZuphrStgid")
    @ColumnInfo(name = "ZuphrStgid")
    String ZuphrStgid;

    @JsonProperty("ZuphrMatnr")
    @ColumnInfo(name = "ZuphrMatnr")
    String ZuphrMatnr;

    @JsonProperty("ZuphrReqid")
    @ColumnInfo(name = "ZuphrReqid")
    String ZuphrReqid;

    @JsonProperty("ZuphrReqitm")
    @ColumnInfo(name = "ZuphrReqitm")
    String ZuphrReqitm;


    @JsonProperty("ZuphrShortxt")
    @ColumnInfo(name = "ZuphrShortxt")
    String ZuphrShortxt;


    @JsonProperty("ZuphrDescrip")
    @ColumnInfo(name = "ZuphrDescrip")
    String ZuphrDescrip;


    @JsonProperty("ZuphrOffshore")
    @ColumnInfo(name = "ZuphrOffshore")
    String ZuphrOffshore;


    @JsonProperty("ZuphrDriverid")
    @ColumnInfo(name = "ZuphrDriverid")
    String ZuphrDriverid;

   // @JsonProperty("ZUphrDrvrName")
    @JsonProperty("ZUphrDrvrName")
    @ColumnInfo(name = "ZUphrDrvrName")
    String ZuphrDriverName;


    @JsonProperty("ZuphrVehid")
    @ColumnInfo(name = "ZuphrVehid")
    String ZuphrVehid;

   // @JsonProperty("ZphrVehType")
    @JsonProperty("ZphrVehType")
    @ColumnInfo(name = "ZphrVehType")
    String ZuphrVehType;


    @JsonProperty("ZuphrLoad")
    @ColumnInfo(name = "ZuphrLoad")
    String ZuphrLoad;

    @JsonProperty("ZuphrUload")
    @ColumnInfo(name = "ZuphrUload")
    String ZuphrUload;

    @JsonProperty("ZuphrNfound")
    @ColumnInfo(name = "ZuphrNfound")
    String ZuphrNfound;


    @JsonProperty("ZuphrProc")
    @ColumnInfo(name = "ZuphrProc")
    String ZuphrProc;

    public VehAssign( String zuphrLpid, String zuphrMjahr, String zuphrMblpo, String zuphrStgid,
                     String zuphrMatnr, String zuphrReqid, String zuphrReqitm, String zuphrShortxt,
                     String zuphrDescrip, String zuphrOffshore, String zuphrDriverid,String DriverName, String zuphrVehid,String VechType,
                     String zuphrLoad, String zuphrUload, String zuphrNfound, String zuphrProc) {
        ZuphrLpid = zuphrLpid;
        ZuphrMjahr = zuphrMjahr;
        ZuphrMblpo = zuphrMblpo;
        ZuphrStgid = zuphrStgid;
        ZuphrMatnr = zuphrMatnr;
        ZuphrReqid = zuphrReqid;
        ZuphrReqitm = zuphrReqitm;
        ZuphrShortxt = zuphrShortxt;
        ZuphrDescrip = zuphrDescrip;
        ZuphrOffshore = zuphrOffshore;
        ZuphrDriverid = zuphrDriverid;
        ZuphrDriverName=DriverName;
        ZuphrVehid = zuphrVehid;
        ZuphrVehType=VechType;
        ZuphrLoad = zuphrLoad;
        ZuphrUload = zuphrUload;
        ZuphrNfound = zuphrNfound;
        ZuphrProc = zuphrProc;
    }

    public VehAssign() {

    }

    public int getDispatch() {
        return Dispatch;
    }

    public void setDispatch(int dispatch) {
        Dispatch = dispatch;
    }

    public String getZuphrLpid() {
        return ZuphrLpid;
    }

    public void setZuphrLpid(String zuphrLpid) {
        ZuphrLpid = zuphrLpid;
    }

    public String getZuphrMjahr() {
        return ZuphrMjahr;
    }

    public void setZuphrMjahr(String zuphrMjahr) {
        ZuphrMjahr = zuphrMjahr;
    }

    public String getZuphrMblpo() {
        return ZuphrMblpo;
    }

    public void setZuphrMblpo(String zuphrMblpo) {
        ZuphrMblpo = zuphrMblpo;
    }

    public String getZuphrStgid() {
        return ZuphrStgid;
    }

    public void setZuphrStgid(String zuphrStgid) {
        ZuphrStgid = zuphrStgid;
    }

    public String getZuphrMatnr() {
        return ZuphrMatnr;
    }

    public void setZuphrMatnr(String zuphrMatnr) {
        ZuphrMatnr = zuphrMatnr;
    }

    public String getZuphrReqid() {
        return ZuphrReqid;
    }

    public void setZuphrReqid(String zuphrReqid) {
        ZuphrReqid = zuphrReqid;
    }

    public String getZuphrReqitm() {
        return ZuphrReqitm;
    }

    public void setZuphrReqitm(String zuphrReqitm) {
        ZuphrReqitm = zuphrReqitm;
    }

    public String getZuphrShortxt() {
        return ZuphrShortxt;
    }

    public void setZuphrShortxt(String zuphrShortxt) {
        ZuphrShortxt = zuphrShortxt;
    }

    public String getZuphrDescrip() {
        return ZuphrDescrip;
    }

    public void setZuphrDescrip(String zuphrDescrip) {
        ZuphrDescrip = zuphrDescrip;
    }

    public String getZuphrOffshore() {
        return ZuphrOffshore;
    }

    public void setZuphrOffshore(String zuphrOffshore) {
        ZuphrOffshore = zuphrOffshore;
    }

    public String getZuphrDriverid() {
        return ZuphrDriverid;
    }

    public String getZuphrDriverName() { return ZuphrDriverName; }

    public void setZuphrDriverName(String zuphrDriverName) { ZuphrDriverName = zuphrDriverName; }

    public void setZuphrDriverid(String zuphrDriverid) {
        ZuphrDriverid = zuphrDriverid;
    }

    public String getZuphrVehid() {
        return ZuphrVehid;
    }

    public String getZuphrVehType() { return ZuphrVehType; }

    public void setZuphrVehType(String zuphrVehType) { ZuphrVehType = zuphrVehType; }

    public void setZuphrVehid(String zuphrVehid) {
        ZuphrVehid = zuphrVehid;
    }

    public String getZuphrLoad() {
        return ZuphrLoad;
    }

    public void setZuphrLoad(String zuphrLoad) {
        ZuphrLoad = zuphrLoad;
    }

    public String getZuphrUload() {
        return ZuphrUload;
    }

    public void setZuphrUload(String zuphrUload) {
        ZuphrUload = zuphrUload;
    }

    public String getZuphrNfound() {
        return ZuphrNfound;
    }

    public void setZuphrNfound(String zuphrNfound) {
        ZuphrNfound = zuphrNfound;
    }

    public String getZuphrProc() {
        return ZuphrProc;
    }

    public void setZuphrProc(String zuphrDone) {
        ZuphrProc = zuphrDone;
    }
}


