package com.example.listing.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class DriverUnpack {

    List<Driver> driver;

    public DriverUnpack() {

    }

    public List<Driver>  getriverlist() {
        return driver;
    }

    @JsonProperty("d")
    public void unpackd(Map<String, List<Driver>> d) {
        driver = d.get("results");
    }

}