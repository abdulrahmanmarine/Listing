package com.example.listing.models;
import androidx.room.ColumnInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class imagenode implements Serializable {

    @ColumnInfo(name = "ZuphrId")
    @JsonProperty("ZuphrId")
    @SerializedName("ZuphrId")
    String ZuphrId ="";

    @ColumnInfo(name = "AppPrefix")
    @JsonProperty("AppPrefix")
    @SerializedName("AppPrefix")
    String AppPrefix ="STG";

    @ColumnInfo(name = "Item")
    @JsonProperty("Item")
    @SerializedName("Item")
    String Item ="";

    @ColumnInfo(name = "Order")
    @JsonProperty("Order")
    @SerializedName("Order")
    String Order ="";


    @ColumnInfo(name = "Type")
    @JsonProperty("Type")
    @SerializedName("Type")
    String Type;

    @ColumnInfo(name = "Name")
    @JsonProperty("Name")
    @SerializedName("Name")
    String Name;

    @ColumnInfo(name = "Contents")
    @JsonProperty("Contents")
    @SerializedName("Contents")
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
