package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleUnpack {
    List<Vehicle> items = new ArrayList<>();

    public List<Vehicle> getItems() {
        return items;
    }

    public void setItems(List<Vehicle> items) {
        this.items = items;
    }

    public VehicleUnpack() {

    }

    public VehicleUnpack(List<Vehicle> items) {
        this.items = items;
    }



    @JsonProperty("d")
    public void unpackd(Map<String,List<Vehicle>> d) {
        if (d.get("results") != null) {
            items = d.get("results");
        }

    }


}
