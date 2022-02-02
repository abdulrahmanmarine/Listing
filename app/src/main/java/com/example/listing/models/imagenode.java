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


@Entity(tableName = "ImageTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class imagenode implements Serializable {


    @Expose(serialize = false,deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @JsonIgnore
    public int Imageid;

    @Expose(serialize = false,deserialize = false)
    @ColumnInfo(name = "MaterialOfflineID")
    @JsonIgnore
    public  String MaterialOfflineID;


    @ColumnInfo(name = "ZuphrId")
    @JsonProperty("ZuphrId")
    String ZuphrId ="";

    @ColumnInfo(name = "AppPrefix")
    @JsonProperty("AppPrefix")

    String AppPrefix ="STG";

    @ColumnInfo(name = "Item")
    @JsonProperty("Item")

    String Item ="";

    @ColumnInfo(name = "Order")
    @JsonProperty("Order")
    String Order ="";


    @ColumnInfo(name = "Type")
    @JsonProperty("Type")
    String Type;

    @ColumnInfo(name = "Name")
    @JsonProperty("Name")
    String Name;

    @ColumnInfo(name = "Contents")
    @JsonProperty("Contents")
    String Contents;



    public imagenode() {

    }

    public imagenode(String zuphrStgid, String type, String name,String order ,String item,String contents) {
        ZuphrId = zuphrStgid;
        Type = type;
        Name = name;
        Item=item;
        Order=order;
        Contents = contents;
    }



    public String getZuphrId() {
        return ZuphrId;
    }

    public void setZuphrId(String zuphrId) {
        ZuphrId = zuphrId;
    }

    public String getAppPrefix() {
        return AppPrefix;
    }

    public void setAppPrefix(String appPrefix) {
        AppPrefix = appPrefix;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

}
