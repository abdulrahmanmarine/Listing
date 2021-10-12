package com.example.listing.OfflineInterfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.SAPNote;

import java.util.List;

public interface Offline_Plan {

    @Query("SELECT * FROM Plantable WHERE PlanID =:id")
    List<SAPNote> GetItemAll(String id);

    @Insert
    void insertItem(com.example.listing.models.Plan plan);

    @Update
    void updateItem(com.example.listing.models.Plan plan);

    @Delete
    void deleteItem(com.example.listing.models.Plan plan);

    @Query("DELETE FROM PlanTable")
    void nukeTable();
}
