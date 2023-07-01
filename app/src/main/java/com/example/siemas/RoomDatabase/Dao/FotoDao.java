package com.example.siemas.RoomDatabase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM Foto WHERE id_bs = :idbs AND tahun = :tahun AND semester = :semester")
    LiveData<List<Foto>> getLiveDataFotoByIdBs(String idbs, String tahun, int semester);

    @Query("UPDATE foto SET status_foto = :status WHERE id = :idDsrt")
    void updateStatusFoto(int idDsrt, int status);

}
