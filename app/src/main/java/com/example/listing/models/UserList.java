package com.example.listing.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "UserListTable")

//  UserOffline Data Model  //
public class UserList implements Serializable {
    @PrimaryKey()
    @NonNull
    public String UserId; //  logged user UserId

    // Offline create constructor
    public UserList() {
    }

    @Ignore
    // Online Login store userid  constructor
    public UserList(String UserId) {
        this.UserId=UserId;
    }


    //  Data Getter
    @NonNull
    public String getUserId() {
        return UserId;
    }
    ////////////////////////////////////////
}
