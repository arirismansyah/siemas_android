package com.example.siemas.RoomDatabase;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.siemas.RoomDatabase.Dao.PendidikanDao;
import com.example.siemas.RoomDatabase.Entities.Dsbs;
import com.example.siemas.RoomDatabase.Entities.Dsrt;
import com.example.siemas.RoomDatabase.Entities.Jadwal212;
import com.example.siemas.RoomDatabase.Entities.KegiatanUtama;
import com.example.siemas.RoomDatabase.Entities.Laporan212;
import com.example.siemas.RoomDatabase.Entities.Pendidikan;
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
    public List<Dsbs> getDsbs() {
        return repository.getDsbs();
    }

    public void getDsrtPclFromApi(Context context, String pencacah, String token) {
        repository.getDsrtPclFromApi(context, pencacah, token);
    }

    public void getDsrtPmlFromApi(Context context, String pengawas, String token) {
        repository.getDsrtPmlFromApi(context, pengawas, token);
    }

    public LiveData<List<Dsrt>> getLiveDataDsrt(){return repository.getLiveDataDsrt();}

    public List<Dsrt> getDsrt() {
        return repository.getDsrt();
    }

    public List<Dsrt> getListDsrtByIdBs(String idBs){
        return repository.getListDsrtByIdBs(idBs);
    }

    public Dsrt getDsrtById(Integer idDsrt){
        return repository.getDsrtById(idDsrt);
    }

    public LiveData<List<Dsbs>> getLiveDataDsbs() {
        return repository.getLiveDataDsbs();
    }

    public LiveData<List<Dsrt>> getLiveDataDsrtByIdBs(String idBs) {
        return repository.getLiveDataDsrtByIdBs(idBs);
    }

    public List<Dsrt> getListDsrtByIdBsStatusLw(String idBs, int status){
        return repository.getListDsrtByIdBsStatusLw(idBs, status);
    }

    public List<Dsrt> getListDsrtByIdBsStatusUp(String idBs, int status){
        return repository.getListDsrtByIdBsStatusUp(idBs, status);
    }

    // update foto
    public void updateFotoRumah(int idDsrt, String fileFoto){
        repository.updateFotoRumah(idDsrt, fileFoto);
    }

    // update status pencacahan
    public void updateStatusPencacahan(int idDsrt, int statusPencacahan){
        repository.updateStatusPencacahan(idDsrt, statusPencacahan);
    }

    // update pencacahan
    public void updatePencacahan(int idDsrt, String namaKrt, int jmlArt, String statusRumah, String makananSebulan, String nonMakananSebulan, int
            gsmp, String latitude, String longitude, String durasi, String fileFoto, int statusPencacahan){
        repository.updatePencacahan(idDsrt, namaKrt, jmlArt, statusRumah, makananSebulan, nonMakananSebulan, gsmp, latitude, longitude, durasi, fileFoto, statusPencacahan);
    }

    // update pemeriksaan
    public void updatePemeriksaan(
            int idDsrt,
            String namaKrt,
            int jmlArt,
            String makananSebulan,
            String nonMakananSebulan,
            String transportasi,
            String peliharaan,
            int artSekolah,
            int artBpjs,
            String ijazahKrt,
            String kegiatanKrt,
            String deskripsiKegiatan,
            int luasLantai,
            int statusPencacahan
    ){
        repository.updatePemeriksaan(
                idDsrt,
                namaKrt,
                jmlArt,
                makananSebulan,
                nonMakananSebulan,
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

    public List<Jadwal212> getListJadwal(){return repository.getListJadwal();}
    public Jadwal212 getJadwalByTanggal(String tanggal){
        return repository.getJadwalByTanggal(tanggal);
    }

    public Dsrt getDsrtByIdBsNuRt(String idBs, int nuRT){
        return repository.getDsrtByIdBsNuRt(idBs, nuRT);
    }

    public void insertLaporan(Laporan212 laporan212){
        repository.insertLaporan(laporan212);
    }

    public LiveData<List<Laporan212>> getLiveDataLaporan(){
        return repository.getLiveDataLaporan();
    }

    public void deleteLaporan(String idBs, int nuRt){
        repository.deleteLaporan(idBs, nuRt);
    }

    public void nukeLaporan(){
        repository.nukeLaporan();
    }

    public void updateStatusLaporan(String idBs, int nuRt, int status){repository.updateStatusLaporan(idBs, nuRt, status);}

    public List<Laporan212> getListLaporanByIdBsStatus(String idBs, int status){
        return repository.getLaporanByIdBsStatus(idBs, status);
    }

    public List<Laporan212> getListLaporanByIdBsStatusUp(String idBs, int status){
        return repository.getLaporanByIdBsStatusUp(idBs, status);
    }

    public void getLaporanFromApi(Context context, String email, String token) {
        repository.getLaporanFromApi(context, email, token);
    }

    // update lokasi selesai
    public void updateLokasiSelesai(int idDsrt, String latitudeSelesai, String longitudeSelesai){
        repository.updateLokasiSelesai(idDsrt, latitudeSelesai, longitudeSelesai);
    }

    // update jam mulai
    public void updateJamMulai(int idDsrt, String jamMulai){
        repository.updateJamMulai(idDsrt, jamMulai);
    }

    // update jam selesai
    public void updateJamSelesai(int idDsrt, String jamSelesai){
        repository.updateJamSelesai(idDsrt, jamSelesai);
    }
}
