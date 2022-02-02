package com.example.listing.OfflineInterfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.Vehicle;

import java.util.List;

@Dao
public interface Offline_Vehicle {

    @Query("SELECT * FROM VehicleTable WHERE MaterialOfflineID =:id")
    List<Vehicle> GetItemAll(String id);

    @Insert
    long insertV(Vehicle material);

    @Update
    void UpdateV(Vehicle material);

    @Delete
    void Delete(Vehicle material);

    @Query("DELETE FROM VehicleTable")
    void nukeTable();
}
