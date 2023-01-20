package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.siemas.RoomDatabase.Entities.Periode;

@Dao
public interface PeriodeDao {

    @Insert(entity = Periode.class, onConflict = OnConflictStrategy.REPLACE)
    Void insert_periode(Periode periode);
}
