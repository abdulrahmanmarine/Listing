package com.example.listing.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

@Entity(tableName = "VehicleTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle implements Serializable, Comparable<Vehicle> {

    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int VehicleId;
    @JsonProperty("VehId")
    @ColumnInfo(name = "VehId")
    String Vehid;
    @JsonProperty("Category")
    @ColumnInfo(name = "Category")
    String Category;
    @JsonProperty("VehType")
    @ColumnInfo(name = "VehType")
    String VehType;
    @JsonProperty("Identifier")
    @ColumnInfo(name = "Identifier")
    String Identifier;
    @JsonProperty("MaxWeight")
    @ColumnInfo(name = "MaxWeight")
    String MaxWeight;
    @JsonProperty("Color")
    @ColumnInfo(name = "Color")
    String Color;
    @JsonProperty("Model")
    @ColumnInfo(name = "Model")
    String Model;
    @JsonProperty("MfgYear")
    @ColumnInfo(name = "MfgYear")
    String MfgYear;
    @JsonProperty("PlateNo")
    @ColumnInfo(name = "PlateNo")
    String PlateNo;
    @Ignore
    @JsonIgnore
    List<Driver> loaders = new ArrayList<>();

    public Vehicle() {

    }

    public Vehicle(String vehid, String category, String vehType, String identifier, String maxWeight, String color, String model,
                   String mfgYear, String plateNo, List<Driver> laoders) {
        Vehid = vehid;
        Category = category;
        VehType = vehType;
        Identifier = identifier;
        MaxWeight = maxWeight;
        Color = color;
        Model = model;
        MfgYear = mfgYear;
        PlateNo = plateNo;
        loaders = laoders;

    }

    public String getVehid() {
        return Vehid;
    }

    public void setVehid(String vehid) {
        Vehid = vehid;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getVehType() {
        return VehType;
    }

    public void setVehType(String vehType) {
        VehType = vehType;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public String getMaxWeight() {
        return MaxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        MaxWeight = maxWeight;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getMfgYear() {
        return MfgYear;
    }

    public void setMfgYear(String mfgYear) {
        MfgYear = mfgYear;
    }

    public String getPlateNo() {
        return PlateNo;
    }

    public void setPlateNo(String plateNo) {
        PlateNo = plateNo;
    }

    public List<Driver> getLoaders() {
        return loaders;
    }

    public void setLoaders(List<Driver> loaders) {
        this.loaders = loaders;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        final Vehicle other = (Vehicle) obj;
        if (this.getVehid().equals(other.Vehid)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Vehicle o) {
        return this.getVehid().compareTo(o.Vehid);
    }


}
