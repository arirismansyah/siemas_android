package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.Pendidikan;

import java.util.List;

@Dao
public interface PendidikanDao {
    @Query("Select * FROM Pendidikan")
    List<Pendidikan> getAllPendidikan();

    @Insert(entity = Pendidikan.class, onConflict = OnConflictStrategy.REPLACE)
    void insertPendidikan(Pendidikan pendidikan);

    @Query("DELETE FROM Pendidikan")
    int nukePendidikan();
}
