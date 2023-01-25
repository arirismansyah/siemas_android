package com.example.siemas.RoomDatabase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;

import java.util.List;

@Dao
public interface DsrtDao {
    @Insert(entity = Dsrt.class, onConflict = OnConflictStrategy.REPLACE)
    void insertDsrt(Dsrt dsrt);
    @Insert(entity = Dsrt.class, onConflict = OnConflictStrategy.REPLACE)
    void insertListDsrt(List<Dsrt> listDsrt);
    @Query("Select * FROM dsrt WHERE tahun = :tahun AND semester = :semester ")
    LiveData<List<Dsrt>> getDsrtLive(String tahun, int semester);
    @Query("Select * FROM dsrt WHERE id = :idDsrt")
    Dsrt getDsrtById(int idDsrt);
    @Query("Select * FROM dsrt  WHERE tahun = :tahun AND semester = :semester ")
    List<Dsrt> getDsrtList(String tahun, int semester);

    @Query("Select * FROM dsrt WHERE id_bs = :idBS AND nks = :nks AND nu_rt = :nu_rt")
    Dsrt getDsrtByIdBSNksNuRt(String idBS, String nks, int nu_rt);

    @Query("Select * FROM dsrt WHERE id_bs = :idBS AND nu_rt = :nu_rt AND tahun = :tahun AND semester = :semester  ")
    Dsrt getDsrtByIdBSNuRt(String idBS, int nu_rt, String tahun, int semester);

    @Query("Select * FROM dsrt WHERE pencacah = :pencacah")
    Dsrt getDsrtPcl(String pencacah);

    @Query("Select * FROM dsrt WHERE pengawas = :pengawas")
    Dsrt getDsrtPml(String pengawas);

    @Query("Select * FROM dsrt WHERE status_pencacahan = :status_pencacahan")
    Dsrt getDsrtByStatus(int status_pencacahan);

    @Query("Select * FROM dsrt WHERE status_pencacahan = :status_pencacahan AND id_bs= :idBs")
    LiveData<List<Dsrt>> getLiveDsrtByStatusIdBs(int status_pencacahan, String idBs);

    @Query("Select * FROM dsrt WHERE status_pencacahan < :status_pencacahan AND id_bs = :idBs AND tahun = :tahun AND semester = :semester")
    List<Dsrt> getListDsrtByIdBsStatusLw(String idBs, int status_pencacahan, String tahun, int semester);

    @Query("Select * FROM dsrt WHERE status_pencacahan > :status_pencacahan AND id_bs = :idBs AND tahun = :tahun AND semester = :semester")
    List<Dsrt> getListDsrtByIdBsStatusUp(String idBs, int status_pencacahan, String tahun, int semester );

    @Query("Select * FROM dsrt WHERE id_bs= :idBs AND tahun = :tahun AND semester = :semester")
    LiveData<List<Dsrt>> getLiveDsrtByIdBs(String idBs, String tahun, int semester );

    @Query("Select * FROM dsrt WHERE id_bs= :idBs AND tahun = :tahun AND semester = :semester")
    List<Dsrt> getListDsrtByIdBs(String idBs, String tahun, int semester );

    @Query("UPDATE dsrt SET foto = :fileFoto WHERE id = :idDsrt")
    void updateFotoRumah(int idDsrt, String fileFoto);

    @Query("UPDATE dsrt SET status_pencacahan = :statusPencacahan WHERE id = :idDsrt")
    void updateStatusPencacahan(int idDsrt, int statusPencacahan);

    @Query("UPDATE dsrt SET " +
            "nama_krt2 = :namaKrt, " +
            "jml_art2 = :jmlKrt, " +
            "status_rumah = :statusRumah, " +
            "makanan_sebulan = :makananSebulan, " +
            "nonmakanan_sebulan = :nonMakananSebulan, " +
            "gsmp = :gsmp," +
            "latitude = :latitude," +
            "longitude = :longitude," +
            "durasi_pencachan = :durasi," +
            "foto = :fileFoto," +
            "status_pencacahan = :statusPencacahan WHERE id = :idDsrt")
    void updatePencacahan(int idDsrt, String namaKrt, int jmlKrt, String statusRumah, String makananSebulan, String nonMakananSebulan, int gsmp, String latitude, String longitude, String durasi, String fileFoto, int statusPencacahan);

    @Query("UPDATE dsrt SET " +
            "latitude_selesai = :latitudea_selesai, " +
            "longitude_selesai = :longitude_selesai WHERE id = :idDsrt")
    void updateDoneLocation(int idDsrt, String latitudea_selesai, String longitude_selesai);

    @Query("UPDATE dsrt SET " +
            "jam_mulai = :jamMulai WHERE id = :idDsrt")
    void updateJamMulai(int idDsrt, String jamMulai);

    @Query("UPDATE dsrt SET " +
            "jam_selesai = :jamSelesai WHERE id = :idDsrt")
    void updateJamSelesai(int idDsrt, String jamSelesai);

    @Query("UPDATE dsrt SET " +
            "nama_krt2 = :namaKrt, " +
            "jml_art2 = :jmlKrt, " +
            "jml_komoditas_makanan = :jml_komoditas_makanan, " +
            "jml_komoditas_nonmakanan = :jml_komoditas_nonmakanan, " +
            "makanan_sebulan = :makananSebulan, " +
            "nonmakanan_sebulan = :nonMakananSebulan, " +
            "makanan_sebulan_bypml = :makanan_sebulan_bypml, " +
            "nonmakanan_sebulan_bypml = :nonmakanan_sebulan_bypml, " +
            "transportasi = :transportasi," +
            "peliharaan = :peliharaan," +
            "art_sekolah = :artSekolah," +
            "art_bpjs = :artBpjs," +
            "ijazah_krt = :ijazahKrt," +
            "kegiatan_seminggu = :kegiatanKrt," +
            "deskripsi_kegiatan = :deskripsiKegiatan," +
            "luas_lantai = :luasLantai," +
            "status_pencacahan = :statusPencacahan WHERE id = :idDsrt")
    void updatePemeriksaan(
            int idDsrt,
            String namaKrt,
            int jmlKrt,
            int jml_komoditas_makanan,
            int jml_komoditas_nonmakanan,
            String makananSebulan,
            String nonMakananSebulan,
            String makanan_sebulan_bypml,
            String nonmakanan_sebulan_bypml,
            String transportasi,
            String peliharaan,
            int artSekolah,
            int artBpjs,
            String ijazahKrt,
            String kegiatanKrt,
            String deskripsiKegiatan,
            int luasLantai,
            int statusPencacahan);


    @Update
    void updateDsrt(Dsrt dsrt);

    @Query("DELETE FROM dsrt")
    int nukeDsrt();
}
