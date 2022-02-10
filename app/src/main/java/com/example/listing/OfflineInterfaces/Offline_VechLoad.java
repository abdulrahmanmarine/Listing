package com.example.listing.OfflineInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.listing.models.VechAssignLoader;

import java.util.List;

@Dao
public interface Offline_VechLoad {


    @Query("SELECT * FROM MatrialLoaderTable WHERE ZuphrLpid=:LPID And ZuphrDriverid=:Did ")
    LiveData<List<VechAssignLoader>> GetItemAll(String LPID, String Did);

    @Query("SELECT * FROM MatrialLoaderTable WHERE ZuphrLpid=:LPID And ZuphrDriverid=:Did And AddToDB=:addedtodb")
    LiveData<List<VechAssignLoader>> GetItemtoPost(String LPID, String Did, Boolean addedtodb);

    @Insert
    long insertAssginment(VechAssignLoader assgin);

    @Update
    void UpdateV(VechAssignLoader assgin);

    @Query("DELETE FROM MatrialLoaderTable WHERE  ZuphrLpid=:LPID")
    int DeleteByPlanID(String LPID);

    @Query("DELETE FROM VehicleTable")
    void nukeTable();


    @Query("DELETE FROM MatrialLoaderTable WHERE  ZuphrLpid=:LPID AND ZuphrDriverid=:driverid AND ZuphrMblpo=:Mid AND ZuphrMjahr=:mjhr ")
    int DeleteByID(String LPID, String driverid, String Mid, String mjhr);
}
