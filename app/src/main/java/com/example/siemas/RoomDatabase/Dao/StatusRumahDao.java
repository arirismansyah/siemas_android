package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.StatusRumah;
import com.example.siemas.RoomDatabase.Entities.User;

import java.util.List;

@Dao
public interface StatusRumahDao {
    @Query("Select * FROM StatusRumah")
    List<StatusRumah> getAllStatusRumah();

    @Insert(entity = StatusRumah.class, onConflict = OnConflictStrategy.REPLACE)
    void insertStatusRumah(StatusRumah statusRumah);

    @Query("DELETE FROM StatusRumah")
    int nukeStatusRumah();
}
