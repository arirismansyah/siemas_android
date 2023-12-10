package com.example.siemas.RoomDatabase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Foto;

import java.util.List;

@Dao
public interface FotoDao {
    @Insert(entity = Foto.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListFoto(List<Foto> fotoList);

    @Query("UPDATE foto SET foto = :fileFoto WHERE id = :idDsrt")
    void updateFotoRumah(int idDsrt, byte[] fileFoto);

    @Update(entity = Foto.class)
    void updateFoto(Foto foto);

    @Query("SELECT * FROM Foto WHERE kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND tahun = :tahun AND semester = :semester ORDER BY nu_rt")
    LiveData<List<Foto>> getLiveDataFotoByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Query("UPDATE foto SET status_foto = :status WHERE id = :idDsrt")
    void updateStatusFoto(int idDsrt, int status);

    @Query("Select *  FROM foto WHERE id = :idDsrt")
    Foto getFotoById(int idDsrt);
}
