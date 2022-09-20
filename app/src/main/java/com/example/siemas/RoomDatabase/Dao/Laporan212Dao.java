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
    List<Laporan212> getListLaporan212();

    @Query("Select * FROM laporan212")
    LiveData<List<Laporan212>> getLiveDataLaporan212();

    @Query("Select * FROM laporan212 WHERE id_bs = :idBs")
    List<Laporan212> getListLaporan212ByIdBs(String idBs);

    @Query("Select * FROM laporan212 WHERE id_bs = :idBs AND status = :status")
    List<Laporan212> getListLaporan212ByIdBsStatus(String idBs, int status);

    @Query("Select * FROM laporan212 WHERE id_bs = :idBs AND status > :status")
    List<Laporan212> getListLaporan212ByIdBsStatusUp(String idBs, int status);

    @Insert(entity = Laporan212.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListLaporan(List<Laporan212> laporan212List);

    @Insert(entity = Laporan212.class, onConflict = OnConflictStrategy.REPLACE)
    void insertlaporan(Laporan212 laporan212);

    @Query("UPDATE laporan212 SET status = :status WHERE id_bs = :idBs AND nu_rt = :nuRT")
    void updateStatus(String idBs, int nuRT, int status);

    @Query("DELETE FROM laporan212 WHERE id_bs = :idBs AND nu_rt = :nuRt")
    int deleteLaporan(String idBs, int nuRt);

    @Query("DELETE FROM laporan212")
    int nukeLaporan();
}
