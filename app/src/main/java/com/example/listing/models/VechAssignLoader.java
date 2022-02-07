package com.example.listing.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.listing.Kotlin.Loader;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;


@Entity(tableName = "MatrialLoaderTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VechAssignLoader implements Serializable {

    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int Loaderid=0;

    @JsonProperty("ZuphrLpid")
    @ColumnInfo(name = "ZuphrLpid")
    String ZuphrLpid;

    @JsonProperty("ZuphrMjahr")
    @ColumnInfo(name = "ZuphrMjahr")
     String ZuphrMjahr;

    @JsonProperty("ZuphrMblpo")
    @ColumnInfo(name = "ZuphrMblpo")
    String ZuphrMblpo;


    @JsonProperty("ZuphrDriverid")
    @ColumnInfo(name = "ZuphrDriverid")
    String ZuphrDriverid;


    @JsonProperty("ZuphrVehid")
    @ColumnInfo(name = "ZuphrVehid")
    String ZuphrVehid;


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



    @JsonIgnore
    @ColumnInfo(name = "AddToDB")
    public boolean addtoDB=true;

    public VechAssignLoader( String zuphrLpid, String zuphrMjahr, String zuphrMblpo,  String zuphrDriverid
            , String zuphrVehid, String zuphrLoad, String zuphrUload, String zuphrNfound, String zuphrProc) {
        ZuphrLpid = zuphrLpid;
        ZuphrMjahr = zuphrMjahr;
        ZuphrMblpo = zuphrMblpo;
        ZuphrDriverid = zuphrDriverid;
        ZuphrVehid = zuphrVehid;
        ZuphrLoad = zuphrLoad;
        ZuphrUload = zuphrUload;
        ZuphrNfound = zuphrNfound;
        ZuphrProc = zuphrProc;
    }

    public VechAssignLoader() {

    }

    public int getDispatch() {
        return Loaderid;
    }

    public void setDispatch(int dispatch) {
        Loaderid = dispatch;
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


    public String getZuphrDriverid() {
        return ZuphrDriverid;
    }

    public void setZuphrDriverid(String zuphrDriverid) {
        ZuphrDriverid = zuphrDriverid;
    }

    public String getZuphrVehid() {
        return ZuphrVehid;
    }


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

    @JsonIgnore
    @Ignore
    public boolean isAddedtoDB() {
        return addtoDB;
    }
    @JsonIgnore
    @Ignore
    public void AddtoDB(boolean addtoDB) {
        addtoDB = addtoDB;
    }
}


