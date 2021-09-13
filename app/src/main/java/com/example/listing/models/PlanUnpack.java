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
public class PlanUnpack {
    List<Plan2> items = new ArrayList<>();
    Plan2 stageitem =new Plan2();

    public List<Plan2> getItems() {
        return items;
    }

    public void setItems(List<Plan2> items) {
        this.items = items;
    }

    public PlanUnpack() {

    }

    public PlanUnpack(List<Plan2> items) {
        this.items = items;
    }



    @JsonProperty("d")
    public void unpackd(Map<String,List<Plan2>> d) {
        if (d.get("results") != null) {
            items = d.get("results");
        }

    }


}
