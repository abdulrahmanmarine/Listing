package com.example.listing.OfflineInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.Plan;

import java.util.List;

@Dao
public interface Offline_Plan {

    @Query("SELECT * FROM Plantable WHERE ZuphrFpName =:userid")
    LiveData<List<Plan>> GetItemAll(String userid);

    @Insert
    long insertplan(com.example.listing.models.Plan plan);

    @Update
    void updateplan(com.example.listing.models.Plan plan);

    @Delete
    void deleteplan(com.example.listing.models.Plan plan);

    @Query("DELETE FROM PlanTable")
    void nukeTable();
}
