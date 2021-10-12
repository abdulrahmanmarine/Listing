package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
//  UserOffline Data Model  //
public class User implements Serializable {

    @JsonProperty("Userid")
    public String UserId = null;

    public void setName(String name) {
        Name = name;
    }

    public String Password = null;

    @JsonProperty("Vorna")
    public String Name = null;

    @JsonProperty("ZuphrProfid")
    public String Profileid = null;

    @JsonProperty("Email")
    public String Email = null;

    //  Data Constructor for usage
    public User() {
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setProfileid(String profileid) {
        Profileid = profileid;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return Name;
    }

    public String getProfileid() {
        return Profileid;
    }

    public String getEmail() {
        return Email;
    }
    ////////////////////////////////////////
}
