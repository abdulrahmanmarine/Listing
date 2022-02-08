package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignmentUnpack {

    MatrialDispatching Assignment;

    public AssignmentUnpack() {

    }

    public MatrialDispatching getAssignment() {
        return Assignment;
    }

    @JsonProperty("d")
    public void unpackd(MatrialDispatching d) {

        Assignment = d;
    }

}