package com.example.listing.models;

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


@Entity(tableName = "MatrialDispatchingTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatrialDispatching implements Serializable {

    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int Dispatch;

    @JsonProperty("ZuphrLpid")
    @ColumnInfo(name = "ZuphrLpid")
    String ZuphrLpid;


    @JsonProperty("Zuphr")
    @ColumnInfo(name = "Zuphr")
    String Zuphr;


    @JsonProperty(" NavLpToVehAssign")
    @Ignore
    List<VehAssign> Vehassignment;

    @JsonProperty("NavLpToReturn")
    @ColumnInfo(name = "NavLpToReturn")
    List<String> NavLpToReturn;


    public MatrialDispatching(String zuphrLpid, String zuphr, List<VehAssign> vehassign) {
        ZuphrLpid = zuphrLpid;
        Zuphr = zuphr;
        Vehassignment = vehassign;
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

    public String getZuphr() {
        return Zuphr;
    }

    public void setZuphr(String zuphr) {
        Zuphr = zuphr;
    }

    public List<VehAssign> getVehassign() {
        return Vehassignment;
    }

    public void setVehassign(List<VehAssign> vehassign) {
        Vehassignment = vehassign;
    }
}

