package com.example.listing.OfflineInterfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.imagenode;

import java.util.List;

@Dao
public interface Offline_Image {


    @Query("SELECT * FROM ImageTable WHERE MaterialOfflineID =:id")
    List<imagenode> GetItemAll(String id);

    @Insert
    void insertImage(imagenode image);

    @Update
    void UpdateImage(imagenode image);

    @Delete
    void Delete(imagenode image);

    @Query("DELETE FROM ImageTable")
    void nukeTable();

}
