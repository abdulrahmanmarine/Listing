package com.example.listing.OfflineInterfaces;

        import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;
        import androidx.room.Update;
      import com.example.listing.models.VehAssign;
        import java.util.List;

@Dao
public interface Offline_VechAssign {

    @Query("SELECT * FROM MatrialDispatchingTable WHERE ZuphrLpid=:LPID ")
    LiveData<List<VehAssign>> GetItemAll(String LPID);

    @Query("SELECT * FROM MatrialDispatchingTable WHERE ZuphrLpid=:LPID And AddToDB=:flag ")
    LiveData<List<VehAssign>> GetItemtoPost(String LPID,Boolean flag);

    @Insert
    long insertAssginment(VehAssign assgin);

    @Update
    void UpdateV(VehAssign assgin);

    @Query("DELETE FROM MatrialDispatchingTable WHERE  ZuphrLpid=:LPID")
    void DeleteByPlanID(String LPID);

    @Query("DELETE FROM VehicleTable")
    void nukeTable();


    @Delete()
    void DeleteByObj(VehAssign assign);
}
