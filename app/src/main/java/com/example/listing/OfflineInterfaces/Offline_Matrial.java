package com.example.listing.OfflineInterfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.Material;
import com.example.listing.models.imagenode;

import java.util.List;


@Dao
public interface Offline_Matrial {


    @Query("SELECT * FROM MaterialTable WHERE PlanOfflineID =:id")
    List<imagenode> GetItemAll(String id);

    @Insert
    void insertImage(Material material);

    @Update
    void UpdateImage(Material material);

    @Delete
    void Delete(Material material);

    @Query("DELETE FROM MaterialTable")
    void nukeTable();

}
