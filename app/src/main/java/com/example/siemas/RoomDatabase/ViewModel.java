package com.example.siemas.RoomDatabase;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.siemas.RoomDatabase.Dao.PendidikanDao;
import com.example.siemas.RoomDatabase.Entities.Dsart;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Foto;
import com.example.siemas.RoomDatabase.Entities.Jadwal212;
import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;
import com.example.siemas.RoomDatabase.Entities.Periode;
import com.example.siemas.RoomDatabase.Entities.Status;
import com.example.siemas.RoomDatabase.Entities.StatusRumah;
import com.example.siemas.RoomDatabase.Entities.User;

import java.security.PublicKey;
import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;

    public ViewModel(Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public List<User> getUser() {
        return repository.getUser();
    }

    public void nukeUser() {
        repository.nukeUser();
    }

    public void getDsbsPclFromApi(Context context, String pencacah, String token) {
        repository.getDsbsPclFromApi(context, pencacah, token);
    }

    public void getDsbsPmlFromApi(Context context, String pengawas, String token) {
        repository.getDsbsPmlFromApi(context, pengawas, token);
    }

    public void getJadwalFromApi(Context context, String email, String token) {
        repository.getJadwal212FromApi(context, email, token);
    }

    public List<Dsbs> getDsbs(String tahun, int semester) {
        return repository.getDsbs(tahun, semester);
    }

    public void getDsrtPclFromApi(Context context, String pencacah, String token) {
        repository.getDsrtPclFromApi(context, pencacah, token);
    }

    public void getDsrtPmlFromApi(Context context, String pengawas, String token) {
        repository.getDsrtPmlFromApi(context, pengawas, token);
    }

    public LiveData<List<Dsrt>> getLiveDataDsrt(String tahun, int semester) {
        return repository.getLiveDataDsrt(tahun, semester);
    }

    public List<Dsrt> getDsrt(String tahun, int semester) {
        return repository.getDsrt(tahun, semester);
    }

    public List<Dsrt> getListDsrtByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getListDsrtByIdBs(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public Dsrt getDsrtById(Integer idDsrt) {
        return repository.getDsrtById(idDsrt);
    }

    public LiveData<List<Foto>> getLiveDatafotobyIdbs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getLiveDataFotoByIdBs(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public LiveData<List<Dsbs>> getLiveDataDsbs(String tahun, int semester) {
        return repository.getLiveDataDsbs(tahun, semester);
    }

    public LiveData<List<Dsrt>> getLiveDataDsrtByIdBs(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getLiveDataDsrtByIdBs(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public List<Dsrt> getListDsrtByStatus(int status_pencacahan_lw, int status_pencacahan_up, String tahun, int semester) {
        return repository.getListDsrtByStatus(status_pencacahan_lw, status_pencacahan_up,  tahun, semester);
    }

    public List<Dsrt> getListDsrtByIdBsStatusLw(int status_pencacahan,String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getListDsrtByIdBsStatusLw(status_pencacahan,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public List<Dsrt> getListDsrtByIdBsStatusUp(int status_pencacahan,String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getListDsrtByIdBsStatusUp(status_pencacahan,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public List<Foto> getListDsrtByStatusFoto(int status_foto, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getListDsrtByStatusFoto(status_foto,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public List<Foto> getListDsrtByStatusFotoNot(int status_foto, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getListDsrtByStatusFotoNot(status_foto,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public Foto getFotoById(Integer idDsrt) {
        return repository.getFotoById(idDsrt);
    }

//     update foto
    public void updateFotoRumah(int idDsrt, byte[] fileFoto) {
        repository.updateFotoRumah(idDsrt, fileFoto);
    }

    // update status pencacahan
    public void updateStatusPencacahan(int idDsrt, int statusPencacahan) {
        repository.updateStatusPencacahan(idDsrt, statusPencacahan);
    }

    public void updateStatusFoto(int idDsrt, int status) {
        repository.updateStatusFoto(idDsrt, status);
    }

    // update pencacahan
    public void updatePencacahan(int idDsrt, String namaKrt, int jmlArt, String statusRumah,
                                 String makananSebulan, String nonMakananSebulan,
                                 int gsmp, String gsmp_desk, int bantuan, String bantuan_desk, String latitude, String longitude,
                                 String durasi, int statusPencacahan) {
        repository.updatePencacahan(idDsrt, namaKrt, jmlArt, statusRumah, makananSebulan,
                nonMakananSebulan, gsmp, gsmp_desk, bantuan, bantuan_desk, latitude, longitude, durasi, statusPencacahan);
    }

    // update pemeriksaan
    public void updatePemeriksaan(
            int idDsrt,
            String namaKrt,
            int jmlArt,
            int jml_komoditas_makanan,
            int jml_komoditas_nonmakanan,
            String makananSebulan,
            String nonMakananSebulan,
            String makananSebulanbypml,
            String nonMakananSebulanbypml,
            String transportasi,
            String peliharaan,
            int artSekolah,
            int artBpjs,
            String ijazahKrt,
            String kegiatanKrt,
            String deskripsiKegiatan,
            int luasLantai,
            int statusPencacahan
    ) {
        repository.updatePemeriksaan(
                idDsrt,
                namaKrt,
                jmlArt,
                jml_komoditas_makanan,
                jml_komoditas_nonmakanan,
                makananSebulan,
                nonMakananSebulan,
                makananSebulanbypml,
                nonMakananSebulanbypml,
                transportasi,
                peliharaan,
                artSekolah,
                artBpjs,
                ijazahKrt,
                kegiatanKrt,
                deskripsiKegiatan,
                luasLantai,
                statusPencacahan
        );
    }

    // status rumah

    public List<StatusRumah> getAllStatusRumah() {
        return repository.getAllStatusRumah();
    }

    public void insertStatusRumah(StatusRumah statusRumah) {
        repository.insertStatusRumah(statusRumah);
    }

    public void nukeStatusRumah() {
        repository.nukeStatusRumah();
    }

    // pendidikan

    public List<Pendidikan> getAllPendidikan() {
        return repository.getAllPendidikan();
    }

    public void insertPendidikan(Pendidikan pendidikan) {
        repository.insertPendidikan(pendidikan);
    }

    public void nukePendidikan() {
        repository.nukePendidikan();
    }

    // status pencacahan

    public List<Status> getAllStatus() {
        return repository.getAllStatus();
    }

    public void insertStatus(Status status) {
        repository.insertStatus(status);
    }

    public void nukeStatus() {
        repository.nukeStatus();
    }

    // kegiatan utama
    public List<KegiatanUtama> getAllKegiatan() {
        return repository.getAllKegiatan();
    }

    public void insertKegiatan(KegiatanUtama kegiatanUtama) {
        repository.insertKegiatan(kegiatanUtama);
    }

    public void nukeKegiatan() {
        repository.nukeKegiatan();
    }

    public void nukeDsbs() {
        repository.nukeDsbs();
    }

    public void nukeDsrt() {
        repository.nukeDsrt();
    }

    public List<Jadwal212> getListJadwal() {
        return repository.getListJadwal();
    }

    public Jadwal212 getJadwalByTanggal(String tanggal) {
        return repository.getJadwalByTanggal(tanggal);
    }

    public Dsrt getDsrtByIdBsNuRt(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT) {
        return repository.getDsrtByIdBsNuRt(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
    }

    public void insertLaporan(Laporan212 laporan212) {
        repository.insertLaporan(laporan212);
    }

    public LiveData<List<Laporan212>> getLiveDataLaporan() {
        return repository.getLiveDataLaporan();
    }

    public void deleteLaporan(String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT) {
        repository.deleteLaporan(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
    }

    public void nukeLaporan() {
        repository.nukeLaporan();
    }

    public void updateStatusLaporan(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nuRT) {
        repository.updateStatusLaporan(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nuRT);
    }

    public List<Laporan212> getListLaporanByIdBsStatus(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getLaporanByIdBsStatus(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public List<Laporan212> getListLaporanByIdBsStatusUp(int status, String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs) {
        return repository.getLaporanByIdBsStatusUp(status,tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs);
    }

    public void getLaporanFromApi(Context context, String email, String token) {
        repository.getLaporanFromApi(context, email, token);
    }

    // update lokasi selesai
    public void updateLokasiSelesai(int idDsrt, String latitudeSelesai, String longitudeSelesai) {
        repository.updateLokasiSelesai(idDsrt, latitudeSelesai, longitudeSelesai);
    }

    // update jam mulai
    public void updateJamMulai(int idDsrt, String jamMulai) {
        repository.updateJamMulai(idDsrt, jamMulai);
    }

    // update jam selesai
    public void updateJamSelesai(int idDsrt, String jamSelesai) {
        repository.updateJamSelesai(idDsrt, jamSelesai);
    }

    public void updateDurasiPencacahan(int idDsrt, String durasi) {
        repository.updateDurasiPencacahan(idDsrt, durasi);
    }

    public void getPeriodeFromServer(Context context) {
        repository.getPeriodeFromAPI(context);
    }

    public List<Periode> getPeriode() {
        return repository.getPeriode();
    }

    public void insertDsart(List<Dsart> list) {
        repository.insertDsartList(list);
    }

    public void nukeDsartbyId(String tahun, int semester,String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt) {
        repository.nukeDsartbyId(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt);
    }

    public List<Dsart> getDsartbyId(String tahun, int semester, String kd_kab,String kd_kec, String kd_desa, String kd_bs,  int nu_rt) {
        return repository.getDsartbyId(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt);
    }

    public List<Dsart> getDsarlamatbyId(String tahun, int semester, String kd_kab,String kd_kec, String kd_desa, String kd_bs,  int nu_rt) {
        return repository.getDsartbyId(tahun, semester, kd_kab, kd_kec, kd_desa, kd_bs, nu_rt);
    }

    public void getDsartPclFromApi(Context context, String pencacah, String token) {
        repository.getDsartPclFromAPI(context, pencacah, token);
    }

    public void getDsartPmlFromApi(Context context, String pencacah, String token) {
        repository.getDsartPmlFromAPI(context, pencacah, token);
    }
}
