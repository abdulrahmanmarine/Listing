package com.example.listing.models;


        import com.fasterxml.jackson.annotation.JsonProperty;

        import java.util.List;
        import java.util.Map;

public class VehicleUnpack {

    List<Vehicle> vehicle;

    public VehicleUnpack() {

    }

    public List<Vehicle>  getvehiclelist() {
        return vehicle;
    }

    @JsonProperty("d")
    public void unpackd(Map<String, List<Vehicle>> d) {
        vehicle = d.get("results");
    }

}