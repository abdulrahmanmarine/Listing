package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageList {
    List<imagenode> items = new ArrayList<>();

    public ImageList() {

    }

    public ImageList(List<imagenode> items) {
        this.items = items;
    }

    public List<imagenode> getItems() {
        return items;
    }

    public void setItems(List<imagenode> items) {
        this.items = items;
    }

    @JsonProperty("d")
    public void unpackd(Map<String, List<imagenode>> d) {
        if (d.get("results") != null) {
            items = d.get("results");
        }

    }
}