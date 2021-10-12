package com.example.listing.OfflineInterfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface Offline_UserList {

    @Query("SELECT UserId FROM UserListTable WHERE UserId =:user")
   com.example.listing.models.UserList GetUser(String user);

    @Insert
    void insertUser(com.example.listing.models.UserList user);

    @Query("DELETE FROM UserListTable")
     void nukeTable();


}