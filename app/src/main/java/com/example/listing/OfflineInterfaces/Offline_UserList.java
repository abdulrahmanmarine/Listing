package com.example.listing.OfflineInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface Offline_UserList {



    @Query("SELECT UserId FROM UserTable WHERE UserId =:id")
    LiveData<com.example.listing.models.User> GetUser(String id);



    @Insert
    void insertUser(com.example.listing.models.User user);

    @Query("DELETE FROM UserTable")
     void nukeTable();


}