package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.siemas.RoomDatabase.Entities.Status;

import java.util.List;

@Dao
public interface StatusDao {
    @Query("Select * FROM status")
    List<Status> getAllStatus();

    @Insert(entity = Status.class, onConflict = OnConflictStrategy.REPLACE)
    void insertStatus(Status status);

    @Query("DELETE FROM status")
    int nukeStatus();
}
