package com.example.listing.OfflineInterfaces;
        import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;
        import androidx.room.Update;
        import com.example.listing.models.VechAssignLoader;
        import com.example.listing.models.VehAssign;

        import java.util.List;

@Dao
public interface Offline_VechLoad {
    

    @Query("SELECT * FROM MatrialLoaderTable WHERE ZuphrLpid=:LPID ")
    LiveData<List<VechAssignLoader>> GetItemAll(String LPID);

    @Query("SELECT * FROM MatrialLoaderTable WHERE ZuphrLpid=:LPID And AddToDB=:flag ")
    LiveData<List<VechAssignLoader>> GetItemtoPost(String LPID,Boolean flag);

    @Insert
    long insertAssginment(VechAssignLoader assgin);

    @Update
    void UpdateV(VechAssignLoader assgin);

    @Query("DELETE FROM MatrialLoaderTable WHERE  ZuphrLpid=:LPID")
    void DeleteByPlanID(String LPID);

    @Query("DELETE FROM VehicleTable")
    void nukeTable();


    @Delete()
    void DeleteByObj(VechAssignLoader assign);
}
