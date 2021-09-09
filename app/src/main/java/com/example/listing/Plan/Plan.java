package com.example.listing.Plan;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.listing.Material.Material;

import java.util.ArrayList;
import java.util.List;

public class Plan implements Parcelable {

    private String  status;
    private String req_name;
    private String destination;
    private String date;
    private String time;
    private String deets;
    private String vessel_num;
    private String driver;
    private List<Material> materials;

    private int img;

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }



    public Plan(String status, String req_name, String date, String time, String deets, String vessel_num, String driver, ArrayList<Material> materials) {
        this.status = status;
        this.req_name = req_name;
        this.date = date;
        this.time = time;
        this.deets = deets;
        this.vessel_num = vessel_num;
        this.driver = driver;
        this.materials = materials;
    }

    public Plan(String status, String req_name, String date, String time, String vessel_num, String driver, ArrayList<Material> materials) {
        this.status = status;
        this.req_name = req_name;
        this.date = date;
        this.time = time;
        this.vessel_num = vessel_num;
        this.driver = driver;
        this.materials = materials;
    }

        protected Plan(Parcel in) {
        status = in.readString();
        req_name = in.readString();
        date = in.readString();
        time = in.readString();
        vessel_num = in.readString();
        driver = in.readString();
        materials = in.createTypedArrayList(Material.CREATOR);

        img = in.readInt();
    }


    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReq_name() {
        return req_name;
    }

    public void setReq_name(String req_name) {
        this.req_name = req_name;
    }

    public String getVessel_num() {
        return vessel_num;
    }

    public void setVessel_num(String vessel_num) {
        this.vessel_num = vessel_num;
    }

    public String getDeets() {
        return deets;
    }

    public void setDeets(String deets) {
        this.deets = deets;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(req_name);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(vessel_num);
        dest.writeString(driver);
        dest.writeList(materials);


    }
}
