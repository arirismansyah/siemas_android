package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.Entities.Status;

import java.util.List;

@Dao
public interface PeriodeDao {

    @Insert(entity = Periode.class, onConflict = OnConflictStrategy.REPLACE)
    void insert_periode(List<Periode> periodeList);

    @Query("SELECT * From Periode")
    List<Periode> getPeriode();
}
