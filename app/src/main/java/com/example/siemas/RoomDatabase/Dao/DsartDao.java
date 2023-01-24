package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.Dsart;

import java.util.List;

@Dao
public interface DsartDao {
    @Insert(entity = Dsart.class, onConflict = OnConflictStrategy.REPLACE)
    Void insert_listdsart(List<Dsart> dsartList);

    @Query("DELETE FROM Dsart WHERE id_bs = :id_bs AND tahun = :tahun AND semester = :semester AND nu_rt = :nu_rt")
    int nukeDsartbyid(String id_bs, String tahun, int semester, int nu_rt);

    @Query("SELECT * FROM Dsart WHERE id_bs = :id_bs AND tahun = :tahun AND semester = :semester AND nu_rt = :nu_rt")
    List<Dsart> getDsartListbyid(String id_bs, String tahun, int semester, int nu_rt);


}
