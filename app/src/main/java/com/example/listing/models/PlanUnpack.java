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
    List<Plan> items = new ArrayList<>();
    Plan stageitem =new Plan();

    public List<Plan> getItems() {
        return items;
    }

    public void setItems(List<Plan> items) {
        this.items = items;
    }

    public PlanUnpack() {

    }

    public PlanUnpack(List<Plan> items) {
        this.items = items;
    }



    @JsonProperty("d")
    public void unpackd(Map<String,List<Plan>> d) {
        if (d.get("results") != null) {
            items = d.get("results");
        }

    }


}
