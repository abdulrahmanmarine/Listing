package com.example.listing.Utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.listing.OfflineInterfaces.Offline_Driver;
import com.example.listing.OfflineInterfaces.Offline_Image;
import com.example.listing.OfflineInterfaces.Offline_LoadAction;
import com.example.listing.OfflineInterfaces.Offline_Matrial;
import com.example.listing.OfflineInterfaces.Offline_Notes;
import com.example.listing.OfflineInterfaces.Offline_Plan;
import com.example.listing.OfflineInterfaces.Offline_UserList;
import com.example.listing.OfflineInterfaces.Offline_Vehicle;
import com.example.listing.models.Driver;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Material;
import com.example.listing.models.Plan;
import com.example.listing.models.SAPNote;
import com.example.listing.models.Vehicle;
import com.example.listing.models.imagenode;

@Database(entities = {Plan.class, Material.class, LoadAction.class, imagenode.class, Driver.class,
        Vehicle.class,
        com.example.listing.models.User.class, SAPNote.class} , version = 11)
public abstract class OfflineDatabaseClient extends RoomDatabase {
    private static final String DB_Name ="App_db";
    private static OfflineDatabaseClient instance;

    public static synchronized OfflineDatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),OfflineDatabaseClient.class, DB_Name).
                       fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract Offline_Plan planitem();
    public abstract Offline_Matrial Matrial();
    public abstract Offline_Driver MatrialDrivers();
    public abstract Offline_Vehicle MatrialVehicles();
    public abstract Offline_LoadAction MatrialLoadAction();
    public abstract Offline_UserList Users();
    public abstract Offline_Notes Notes();
    public abstract Offline_Image MatrialImage();


}
