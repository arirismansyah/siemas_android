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
    void insertListDsrt(List<Dsrt> listDsrt);

    @Query("Select id, kd_kab,nama_kab, kd_kec, nama_kec, kd_desa, nama_desa, kd_bs, nks, tahun, semester, nu_rt, nama_krt_cacah, status_pencacahan, jml_art_prelist, jml_art_cacah,jml_komoditas_makanan,jml_komoditas_nonmakanan,art_sekolah,art_bpjs,luas_lantai,gsmp " +
            "FROM dsrt WHERE tahun = :tahun AND semester = :semester ")
    LiveData<List<Dsrt>> getDsrtLive(String tahun, int semester);

    @Query("Select *  FROM dsrt WHERE id = :idDsrt")
    Dsrt getDsrtById(int idDsrt);

    @Query("Select id, tahun, semester, kd_kab,nama_kab, kd_kec, nama_kec, kd_desa, nama_desa, kd_bs, nu_rt, nks,  nama_krt_cacah, status_pencacahan, jml_art_prelist, jml_art_cacah, jml_komoditas_makanan,jml_komoditas_nonmakanan,art_sekolah,art_bpjs,luas_lantai, gsmp  FROM dsrt  WHERE tahun = :tahun AND semester = :semester ORDER BY nu_rt")
    List<Dsrt> getDsrtList(String tahun, int semester);

    @Query("Select *  FROM dsrt WHERE tahun = :tahun AND semester = :semester AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND nu_rt = :nu_rt")
    Dsrt getDsrtByIdBSNuRt(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt);

    @Query("Select id, tahun, semester, kd_kab,nama_kab, kd_kec, nama_kec, kd_desa, nama_desa, kd_bs, nu_rt, nks,  nama_krt_cacah, status_pencacahan, jml_art_prelist, jml_art_cacah, jml_komoditas_makanan,jml_komoditas_nonmakanan,art_sekolah,art_bpjs,luas_lantai, gsmp  FROM dsrt WHERE status_pencacahan < :status_pencacahan AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND tahun = :tahun AND semester = :semester")
    List<Dsrt> getListDsrtByIdBsStatusLw(int status_pencacahan, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Query("Select id, tahun, semester, kd_kab,nama_kab, kd_kec, nama_kec, kd_desa, nama_desa, kd_bs, nu_rt, nks,  nama_krt_cacah, status_pencacahan, jml_art_prelist, jml_art_cacah, jml_komoditas_makanan,jml_komoditas_nonmakanan,art_sekolah,art_bpjs,luas_lantai, gsmp  FROM dsrt WHERE status_pencacahan > :status_pencacahan AND kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND tahun = :tahun AND semester = :semester")
    List<Dsrt> getListDsrtByIdBsStatusUp(int status_pencacahan, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Query("Select *  FROM dsrt WHERE  kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND tahun = :tahun AND semester = :semester ORDER BY nu_rt")
    LiveData<List<Dsrt>> getLiveDsrtByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Query("Select id, tahun, semester, kd_kab,nama_kab, kd_kec, nama_kec, kd_desa, nama_desa, kd_bs, nu_rt, nks,  nama_krt_cacah, status_pencacahan, jml_art_prelist, jml_art_cacah, jml_komoditas_makanan,jml_komoditas_nonmakanan,art_sekolah,art_bpjs,luas_lantai, gsmp  FROM dsrt WHERE  kd_kab =:kd_kab AND kd_kec =:kd_kec AND kd_desa =:kd_desa AND kd_bs =:kd_bs AND tahun = :tahun AND semester = :semester ORDER BY nu_rt")
    List<Dsrt> getListDsrtByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs);

    @Query("UPDATE dsrt SET status_pencacahan = :statusPencacahan WHERE id = :idDsrt")
    void updateStatusPencacahan(int idDsrt, int statusPencacahan);

    @Query("UPDATE dsrt SET " +
            "nama_krt_cacah = :namaKrt, " +
            "jml_art_cacah = :jmlKrt, " +
            "status_rumah = :statusRumah, " +
            "makanan_sebulan = :makananSebulan, " +
            "nonmakanan_sebulan = :nonMakananSebulan, " +
            "gsmp = :gsmp," +
            "gsmp_desk = :gsmp_desk," +
            "latitude = :latitude," +
            "longitude = :longitude," +
            "durasi_pencachan = :durasi," +
            "status_pencacahan = :statusPencacahan " +
            "WHERE id = :idDsrt")
    void updatePencacahan(int idDsrt, String namaKrt, int jmlKrt, String statusRumah, String makananSebulan, String nonMakananSebulan, int gsmp, String gsmp_desk, String latitude, String longitude, String durasi, int statusPencacahan);

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

    @Query("UPDATE dsrt Set durasi_pencachan = :durasi WHERE id = :idDsrt")
    void updateDurasiPencacahan(int idDsrt, String durasi);

    @Query("UPDATE dsrt SET " +
            "nama_krt_cacah = :namaKrt, " +
            "jml_art_cacah = :jmlKrt, " +
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


    @Query("DELETE FROM dsrt")
    int nukeDsrt();
}
