package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Userunpack {

    User user;

    public Userunpack() {

    }

    public User getUser() {
        return user;
    }

    @JsonProperty("d")
    public void unpackd(Map<String, List<User>> d) {
        user = d.get("results").get(0);
    }

}