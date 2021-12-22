package com.example.listing.OfflineInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.SAPNote;

import java.util.List;

@Dao
public interface Offline_Notes {

    @Query("SELECT * FROM SAPNote WHERE MaterialOfflineID =:id")
    LiveData<List<SAPNote>> GetItemAll(String id);

    @Insert
    long insertItem(com.example.listing.models.SAPNote stageitem);

    @Update
    void updateItem(com.example.listing.models.SAPNote stageitem);

    @Delete
    void deleteItem(com.example.listing.models.SAPNote items);

    @Query("DELETE FROM SAPNote")
     void nukeTable();

}