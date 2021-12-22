package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


public class Deviceunpack {

    Device device;

    public Deviceunpack() {

    }

    public Device getdevice() {
        return device;
    }

    @JsonProperty("d")
    public void unpackd(Map<String, List<Device>> d) {
        device = d.get("results").get(0);
    }

}