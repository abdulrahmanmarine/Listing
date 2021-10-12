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
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "SAPNote")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public
class SAPNote implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false,deserialize = false)
    @NonNull
    @JsonIgnore
    public int NotesID;

    @Expose(serialize = false,deserialize = false)
    @ColumnInfo(name = "MaterialOfflineID")
    @JsonIgnore
    public  String MaterialOfflineID;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Expose(serialize = false,deserialize = false)
    @ColumnInfo(name = "status")
    @JsonIgnore
    public  Boolean status=true;

    @ColumnInfo(name ="ZuphrMjahr")
    @JsonProperty("ZuphrMjahr")
    @SerializedName("ZuphrMjahr")
    public String ZuphrMjahr="";

    @ColumnInfo(name ="ZuphrType")
    @JsonProperty("ZuphrType")
    @SerializedName("ZuphrType")
    public String ZuphrType= "";

    @ColumnInfo(name ="ZuphrNoteId")
    @JsonProperty("ZuphrNoteId")
    @SerializedName("ZuphrNoteId")
    public String ZuphrNoteId ="";

    @ColumnInfo(name ="ZuphrContent")
    @JsonProperty("ZuphrContent")
    @SerializedName("ZuphrContent")
    public String ZuphrContent =null;

    @SerializedName("ZuphrId1")
    @ColumnInfo(name ="ZuphrId1")
    @JsonProperty("ZuphrId1")
    public String ZuphrId1 =null;



    @SerializedName("ZuphrId2")
    @ColumnInfo(name ="ZuphrId2")
    @JsonProperty("ZuphrId2")
    public String ZuphrId2 =null;



    @SerializedName("ZuphrId3")
    @ColumnInfo(name ="ZuphrId3")
    @JsonProperty("ZuphrId3")
    public String ZuphrId3 =null;

    @SerializedName("ZuphrContentType")
    @ColumnInfo(name ="ZuphrContentType")
    @JsonProperty("ZuphrContentType")
    public String ZuphrContentType =null;


    @SerializedName("Lat")
    @ColumnInfo(name ="Lat")
    @JsonProperty("Lat")
    public String Lat =null;


    @SerializedName("Lon")
    @ColumnInfo(name ="Lon")
    @JsonProperty("Lon")
    public String Lon =null;

    @SerializedName("ZuphrFpName")
    @ColumnInfo(name ="ZuphrFpName")
    @JsonProperty("ZuphrFpName")
    public String ZuphrFpName =null;


    @SerializedName("ZuphrFpTime")
    @ColumnInfo(name ="ZuphrFpTime")
    @JsonProperty("ZuphrFpTime")
    public String ZuphrFpTime =null;


    @SerializedName("ZuphrFpDate")
    @ColumnInfo(name ="ZuphrFpDate")
    @JsonProperty("ZuphrFpDate")
    public String ZuphrFpDate =null;


    public SAPNote() {
    }

    public int getNotesID() {
        return NotesID;
    }

    public SAPNote(String zuphrMjahr, String zuphrType, String zuphrNoteId, String zuphrContent,
                   String zuphrId1, String zuphrId2, String zuphrId3, String zuphrContentType,
                   String lat, String lon, String zuphrFpName, String zuphrFpTime, String zuphrFpDate) {
        ZuphrMjahr = zuphrMjahr;
        ZuphrType = zuphrType;
        ZuphrNoteId = zuphrNoteId;
        ZuphrContent = zuphrContent;
        ZuphrId1 = zuphrId1;
        ZuphrId2 = zuphrId2;
        ZuphrId3 = zuphrId3;
        ZuphrContentType = zuphrContentType;
        Lat = lat;
        Lon = lon;
        ZuphrFpName = zuphrFpName;
        ZuphrFpTime = zuphrFpTime;
        ZuphrFpDate = zuphrFpDate;
    }





    public void setNotesID(int notesID) {
        NotesID = notesID;
    }

    public String getMaterialOfflineID() {
        return MaterialOfflineID;
    }

    public void setMaterialOfflineID(String materialOfflineID) {
        MaterialOfflineID = materialOfflineID;
    }

    public String getZuphrMjahr() {
        return ZuphrMjahr;
    }

    public void setZuphrMjahr(String zuphrMjahr) {
        ZuphrMjahr = zuphrMjahr;
    }

    public String getZuphrType() {
        return ZuphrType;
    }

    public void setZuphrType(String zuphrType) {
        ZuphrType = zuphrType;
    }

    public String getZuphrNoteId() {
        return ZuphrNoteId;
    }

    public void setZuphrNoteId(String zuphrNoteId) {
        ZuphrNoteId = zuphrNoteId;
    }

    public String getZuphrContent() {
        return ZuphrContent;
    }

    public void setZuphrContent(String zuphrContent) {
        ZuphrContent = zuphrContent;
    }

    public String getZuphrId1() {
        return ZuphrId1;
    }

    public void setZuphrId1(String zuphrId1) {
        ZuphrId1 = zuphrId1;
    }

    public String getZuphrId2() {
        return ZuphrId2;
    }

    public void setZuphrId2(String zuphrId2) {
        ZuphrId2 = zuphrId2;
    }

    public String getZuphrId3() {
        return ZuphrId3;
    }

    public void setZuphrId3(String zuphrId3) {
        ZuphrId3 = zuphrId3;
    }

    public String getZuphrContentType() {
        return ZuphrContentType;
    }

    public void setZuphrContentType(String zuphrContentType) {
        ZuphrContentType = zuphrContentType;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }

    public String getZuphrFpName() {
        return ZuphrFpName;
    }

    public void setZuphrFpName(String zuphrFpName) {
        ZuphrFpName = zuphrFpName;
    }

    public String getZuphrFpTime() {
        return ZuphrFpTime;
    }

    public void setZuphrFpTime(String zuphrFpTime) {
        ZuphrFpTime = zuphrFpTime;
    }

    public String getZuphrFpDate() {
        return ZuphrFpDate;
    }

    public void setZuphrFpDate(String zuphrFpDate) {
        ZuphrFpDate = zuphrFpDate;
    }
}
