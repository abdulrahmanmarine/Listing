package com.example.listing.OfflineInterfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.Driver;

import java.util.List;

@Dao
public interface Offline_Driver {

    @Query("SELECT * FROM DriverTable WHERE MaterialOfflineID =:id")
    List<Driver> GetItemAll(String id);

    @Insert
    void insertDriver(Driver driver);

    @Update
    void UpdateImage(Driver driver);

    @Delete
    void Delete(Driver driver);

    @Query("DELETE FROM DriverTable")
    void nukeTable();
}
