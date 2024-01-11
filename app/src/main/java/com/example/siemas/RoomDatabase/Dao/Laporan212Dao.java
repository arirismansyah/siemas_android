package com.example.siemas.RoomDatabase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.Laporan212;

import java.util.List;

@Dao
public interface Laporan212Dao {

    @Query("Select * FROM laporan212")
    LiveData<List<Laporan212>> getLiveDataLaporan212();


    @Query("Select * FROM laporan212 WHERE tahun =:tahun AND semester =:semester AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND  kd_kab = :kd_bs  AND status = :status")
    List<Laporan212> getListLaporan212ByIdBsStatus(int status,String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Query("Select * FROM laporan212 WHERE tahun =:tahun AND semester =:semester AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND  kd_kab = :kd_bs  AND status > :status")
    List<Laporan212> getListLaporan212ByIdBsStatusUp(int status,String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Insert(entity = Laporan212.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListLaporan(List<Laporan212> laporan212List);

    @Insert(entity = Laporan212.class, onConflict = OnConflictStrategy.REPLACE)
    void insertlaporan(Laporan212 laporan212);

    @Query("UPDATE laporan212 SET status = :status WHERE tahun =:tahun AND semester =:semester AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND  kd_bs = :kd_bs AND nu_rt = :nuRT")
    void updateStatus(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT );

    @Query("DELETE FROM laporan212 WHERE tahun =:tahun AND semester =:semester AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND  kd_kab = :kd_bs AND nu_rt = :nuRT")
    int deleteLaporan(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT);

    @Query("DELETE FROM laporan212")
    int nukeLaporan();
}
