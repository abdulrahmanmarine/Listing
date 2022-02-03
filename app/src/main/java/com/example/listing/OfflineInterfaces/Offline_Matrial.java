package com.example.listing.OfflineInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.Material;

import java.util.List;


@Dao
public interface Offline_Matrial {


    @Query("SELECT * FROM MaterialTable WHERE PlanOfflineID =:planid")
    LiveData<List<Material>> GetItemAll(String planid);


    @Insert
    long insertMatrial(Material material);

    @Update
    void UpdateImageMatrial(Material material);


    @Query("DELETE FROM MaterialTable WHERE  ZuphrFpName=:FNAME ")
    void Delete(String FNAME);

    @Query("DELETE FROM MaterialTable")
    void nukeTable();

}
