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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    String Zuphr="";


    @JsonProperty(value = "NavLpToVehAssign",access = JsonProperty.Access.READ_ONLY)
    @Ignore
    List<VehAssign> Vehassignment;

    @JsonProperty(value = "NavLpToVehAssign",access = JsonProperty.Access.WRITE_ONLY)
    @Ignore
    public void unpackVehAssign(Map<String ,List<VehAssign>> d) {
        Vehassignment=d.get("results");
    }

    @JsonProperty(value = "NavLpToReturn", access = JsonProperty.Access.READ_ONLY)
    @Ignore
    List<String> NavLpToReturn=new ArrayList<>();



    @JsonPropertyOrder({"ZuphrLpid","Zuphr","NavLpToVehAssign","NavLpToReturn"})

    public MatrialDispatching() {

    }

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




