package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Jadwal212;

import java.util.List;

@Dao
public interface Jadwal212Dao {
    @Query("Select * FROM jadwal212")
    List<Jadwal212> getListJadwal212();

    @Query("Select * FROM jadwal212 WHERE tanggal = :tanggal")
    Jadwal212 getJadwalByTanggal(String tanggal);

    @Insert(entity = Jadwal212.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListJadwal(List<Jadwal212> jadwal212List);

    @Insert(entity = Jadwal212.class, onConflict = OnConflictStrategy.REPLACE)
    void insertJadwal212(Jadwal212 jadwal212);

    @Query("DELETE FROM jadwal212")
    int nukeJadwal212();
}
