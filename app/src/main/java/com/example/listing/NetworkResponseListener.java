package com.example.listing;

import com.example.listing.Plan.Plan;

import org.json.JSONException;

import java.util.ArrayList;

public interface NetworkResponseListener{
    void successData(ArrayList<Plan> data) throws JSONException;
    void failedData();
    }
