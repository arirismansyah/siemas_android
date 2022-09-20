package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;

import java.util.List;

@Dao
public interface KegiatanUtamaDao {
    @Query("Select * FROM KegiatanUtama")
    List<KegiatanUtama> getAllKegiatan();

    @Insert(entity = KegiatanUtama.class, onConflict = OnConflictStrategy.REPLACE)
    void insertKegiatan(KegiatanUtama kegiatanUtama);

    @Query("DELETE FROM KegiatanUtama")
    int nukeKegiatan();
}
