package com.example.listing;

import com.example.listing.Plan.Plan;
import com.example.listing.models.Plan2;

import org.json.JSONException;

import java.util.ArrayList;

public interface NetworkResponseListener{
    void successData(ArrayList<Plan2> data) throws JSONException;
    void failedData();
    }
