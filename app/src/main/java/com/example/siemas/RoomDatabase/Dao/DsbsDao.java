package com.example.siemas.RoomDatabase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.siemas.RoomDatabase.Entities.Dsbs;

import java.util.List;

@Dao
public interface DsbsDao {
    @Insert(entity = Dsbs.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListDsbs(List<Dsbs> listDsbs);

    @Query("Select * FROM dsbs WHERE tahun = :tahun AND semester = :semester ")
    LiveData<List<Dsbs>> getDsbsLive(String tahun, int semester);

    @Query("Select * FROM dsbs WHERE tahun = :tahun AND semester = :semester")
    List<Dsbs> getDsbsList(String tahun, int semester);

    @Query("DELETE FROM dsbs")
    int nukeDsbs();
}
