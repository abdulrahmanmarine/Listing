package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class AssignmentUnpack {

    MatrialDispatching Assignment;

    public AssignmentUnpack() {

    }
    public MatrialDispatching getAssignment() {
        return Assignment;
    }

    @JsonProperty("d")
    public void unpackd(MatrialDispatching d) {
        Assignment =d;
    }


}