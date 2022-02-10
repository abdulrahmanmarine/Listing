package com.example.listing.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Entity(tableName = "UserTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
//  UserOffline Data Model  //
public class User implements Serializable {


    @PrimaryKey()
    @Expose(serialize = false, deserialize = false)
    @NonNull
    @JsonProperty("Userid")
    public String UserId = null;


    public String Password = null;


    @ColumnInfo(name = "Name")
    @Expose(serialize = false, deserialize = false)
    @JsonProperty("Vorna")
    public String Name = null;


    @ColumnInfo(name = "Profileid")
    @Expose(serialize = false, deserialize = false)
    @JsonProperty("ZuphrProfid")
    public String Profileid = null;


    @ColumnInfo(name = "Email")
    @Expose(serialize = false, deserialize = false)
    @JsonProperty("Email")
    public String Email = null;

    //  Data Constructor for usage
    public User() {
    }

    public User(@NonNull String userId, String name, String profileid, String email) {
        UserId = userId;
        Name = name;
        Profileid = profileid;
        Email = email;
    }

    //  Data Constructor for login
    public User(String UserId, String Password) {
        this.UserId = UserId;
        this.Password = Password;
    }


    //  Data Getter
    public String c() {
        return UserId;
    }

    @NonNull
    public String getUserId() {
        return UserId;
    }

    public void setUserId(@NonNull String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfileid() {
        return Profileid;
    }

    public void setProfileid(String profileid) {
        Profileid = profileid;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
    ////////////////////////////////////////
}
