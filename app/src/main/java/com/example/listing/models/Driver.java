package com.example.listing.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Entity(tableName = "DriverTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver implements Serializable, Comparable<Driver> {

    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int DriverId;
    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "MaterialOfflineID")
    @JsonIgnore
    public String VechileId;
    @JsonProperty("ZuphrDriverid")
    @ColumnInfo(name = "ZuphrDriverid")
    String ZuphrDriverid;
    @JsonProperty("ZuphrDrvrName")
    @ColumnInfo(name = "ZuphrDrvrName")
    String ZuphrdrvrName;
    @JsonProperty("ZuphrSpecial")
    @ColumnInfo(name = "ZuphrSpecial")
    String ZuphrSpecial;
    @JsonProperty("ZuphrLicNo")
    @ColumnInfo(name = "ZuphrLicNo")
    String ZuphrLicNo;
    @JsonProperty("ZuphrNation")
    @ColumnInfo(name = "ZuphrNation")
    String ZuphrNation;
    @JsonProperty("Phonenumber")
    @ColumnInfo(name = "Phonenumber")
    String Phonenumber;
    @JsonProperty("ZuphrEmail")
    @ColumnInfo(name = "ZuphrEmail")
    String ZuphrEmail;


    public Driver(String zuphrDriverid, String zuphrdrvrName, String zuphrSpecial,
                  String zuphrLicNo, String zuphrNation, String phonenumber, String zuphrEmail) {
        ZuphrDriverid = zuphrDriverid;
        ZuphrdrvrName = zuphrdrvrName;
        ZuphrSpecial = zuphrSpecial;
        ZuphrLicNo = zuphrLicNo;
        ZuphrNation = zuphrNation;
        Phonenumber = phonenumber;
        ZuphrEmail = zuphrEmail;
    }

    public Driver() {

    }

    public String getZuphrDriverid() {
        return ZuphrDriverid;
    }

    public void setZuphrDriverid(String zuphrDriverid) {
        ZuphrDriverid = zuphrDriverid;
    }

    public String getZuphrdrvrName() {
        return ZuphrdrvrName;
    }

    public void setZuphrdrvrName(String zuphrdrvrName) {
        ZuphrdrvrName = zuphrdrvrName;
    }

    public String getZuphrSpecial() {
        return ZuphrSpecial;
    }

    public void setZuphrSpecial(String zuphrSpecial) {
        ZuphrSpecial = zuphrSpecial;
    }

    public String getZuphrLicNo() {
        return ZuphrLicNo;
    }

    public void setZuphrLicNo(String zuphrLicNo) {
        ZuphrLicNo = zuphrLicNo;
    }

    public String getZuphrNation() {
        return ZuphrNation;
    }

    public void setZuphrNation(String zuphrNation) {
        ZuphrNation = zuphrNation;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getZuphrEmail() {
        return ZuphrEmail;
    }

    public void setZuphrEmail(String zuphrEmail) {
        ZuphrEmail = zuphrEmail;
    }

    public String getVechileId() {
        return VechileId;
    }

    public void setVechileId(String vechileId) {
        VechileId = vechileId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        final Driver other = (Driver) obj;
        if (this.getZuphrDriverid().equals(other.ZuphrDriverid)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Driver o) {
        return this.getZuphrDriverid().compareTo(o.ZuphrDriverid);
    }
}
