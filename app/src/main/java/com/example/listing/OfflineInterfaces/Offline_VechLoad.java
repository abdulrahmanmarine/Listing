package com.example.listing.OfflineInterfaces;
        import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;
        import androidx.room.Update;
        import com.example.listing.models.VechAssignLoader;
        import java.util.List;

@Dao
public interface Offline_VechLoad {

    @Query("SELECT * FROM MatrialLoaderTable WHERE ZuphrLpid=:LPID")
    LiveData<List<VechAssignLoader>> GetItemAll(String LPID);

    @Insert
    long insertAssginment(VechAssignLoader loader);

    @Update
    void UpdateV(VechAssignLoader loader);

    @Query("DELETE FROM MatrialDispatchingTable WHERE  ZuphrLpid=:LPID ")
    void Delete(String LPID);


    @Query("DELETE FROM MatrialLoaderTable")
    void nukeTable();
}
