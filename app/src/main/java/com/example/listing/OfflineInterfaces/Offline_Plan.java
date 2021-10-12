package com.example.listing.OfflineInterfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.Plan;

import java.util.List;

@Dao
public interface Offline_Plan {

    @Query("SELECT * FROM Plantable WHERE PlanID =:id")
    List<Plan> GetItemAll(String id);

    @Insert
    void insertItem(com.example.listing.models.Plan plan);

    @Update
    void updateItem(com.example.listing.models.Plan plan);

    @Delete
    void deleteItem(com.example.listing.models.Plan plan);

    @Query("DELETE FROM PlanTable")
    void nukeTable();
}
