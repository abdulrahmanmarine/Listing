package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver implements Serializable{



    @JsonProperty("ZuphrDriverid")
    String ZuphrDriverid;

    @JsonProperty("ZuphrdrvrName")
    String ZuphrdrvrName;


    @JsonProperty("ZuphrSpecial")
    String ZuphrSpecial;


    @JsonProperty("ZuphrLicNo")
    String ZuphrLicNo;

    @JsonProperty("ZuphrNation")
    String ZuphrNation;


    @JsonProperty("Phonenumber")
    String Phonenumber;


    @JsonProperty("ZuphrEmail")
    String ZuphrEmail;

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


}