package com.example.listing.OfflineInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.LoadAction;

import java.util.List;

@Dao
public interface Offline_LoadAction {



    @Query("SELECT * FROM LoadActionTable WHERE MatiralOfflineID =:matrialid")
    LiveData<LoadAction> GetItemAll(String matrialid);

    @Insert
    long insertLoadAction(LoadAction loadAction);

    @Update
    void UpdateLoadAction(LoadAction loadAction);

    @Delete
    void DeleteLoadAction(LoadAction loadAction);

    @Query("DELETE FROM LoadActionTable")
    void nukeTable();


}
