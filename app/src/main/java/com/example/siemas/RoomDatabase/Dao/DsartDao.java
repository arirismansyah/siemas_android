package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.siemas.RoomDatabase.Entities.Dsart;

@Dao
public interface DsartDao {

    @Insert(entity = Dsart.class, onConflict = OnConflictStrategy.REPLACE)
    Void insert_dsart(Dsart dsart);
}
