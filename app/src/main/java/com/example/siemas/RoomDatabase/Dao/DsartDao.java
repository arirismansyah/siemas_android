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

    @Query("DELETE FROM Dsart WHERE tahun = :tahun AND semester = :semester AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND nu_rt = :nu_rt")
    int nukeDsartbyid(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt);

    @Query("SELECT * FROM Dsart WHERE tahun = :tahun AND semester = :semester AND kd_kab =:kd_kab " +
            "AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND nu_rt = :nu_rt")
    List<Dsart> getDsartListbyid(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt);

    @Query("SELECT * FROM Dsart WHERE tahun = :tahun AND semester = :semester AND kd_kab =:kd_kab " +
            "AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND nu_rt = :nu_rt")
    List<Dsart> getDsartListlamabyid(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt);

}
