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
    void insertDsbs(Dsbs dsbs);

    @Insert(entity = Dsbs.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListDsbs(List<Dsbs> listDsbs);

    @Query("Select * FROM dsbs WHERE tahun = :tahun AND semester = :semester ")
    LiveData<List<Dsbs>> getDsbsLive(String tahun, int semester);

    @Query("Select * FROM dsbs WHERE tahun = :tahun AND semester = :semester")
    List<Dsbs> getDsbsList(String tahun, int semester);

    @Query("Select * FROM dsbs WHERE id_bs = :idBS")
    Dsbs getDsbsById(String idBS);

    @Query("Select * FROM dsbs WHERE pencacah = :pencacah")
    Dsbs getDsbsPcl(String pencacah);

    @Query("Select * FROM dsbs WHERE pengawas = :pengawas")
    Dsbs getDsbsPml(String pengawas);

    @Query("Select * FROM dsbs WHERE status = :status")
    Dsbs getDsbsByStatus(int status);

    @Update
    void updateDsbs(Dsbs dsbs);

    @Query("DELETE FROM dsbs")
    int nukeDsbs();
}
