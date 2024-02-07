package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;

@Entity
public class Dsrt {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "tahun")
    public String tahun;

    @ColumnInfo(name = "semester")
    public int semester;

    @ColumnInfo(name = "kd_kab")
    @Nullable
    public String kd_kab;

    @ColumnInfo(name = "nama_kab")
    @Nullable
    public String nama_kab;

    @ColumnInfo(name = "kd_kec")
    @Nullable
    public String kd_kec;

    @ColumnInfo(name = "nama_kec")
    @Nullable
    public String nama_kec;

    @ColumnInfo(name = "kd_desa")
    @Nullable
    public String kd_desa;

    @ColumnInfo(name = "nama_desa")
    @Nullable
    public String nama_desa;

    @ColumnInfo(name = "kd_bs")
    @Nullable
    public String kd_bs;

    @ColumnInfo(name = "nu_rt")
    public int nu_rt;

    @ColumnInfo(name = "nks")
    @Nullable
    public String nks;

    @ColumnInfo(name = "status_pencacahan")
    @Nullable
    public int status_pencacahan;

    @ColumnInfo(name = "nama_krt_prelist")
    @Nullable
    public String nama_krt_prelist;

    @ColumnInfo(name = "jml_art_prelist")
    @Nullable
    public int jml_art_prelist;

    @ColumnInfo(name = "nama_krt_cacah")
    @Nullable
    public String nama_krt_cacah;

    @ColumnInfo(name = "jml_art_cacah")
    @Nullable
    public int jml_art_cacah;

    @ColumnInfo(name = "status_rumah")
    @Nullable
    public String status_rumah;

    @ColumnInfo(name = "jml_komoditas_makanan")
    @Nullable
    public int jml_komoditas_makanan;

    @ColumnInfo(name = "jml_komoditas_nonmakanan")
    @Nullable
    public int jml_komoditas_nonmakanan;

    @ColumnInfo(name = "makanan_sebulan")
    @Nullable
    public String makanan_sebulan;

    @ColumnInfo(name = "nonmakanan_sebulan")
    @Nullable
    public String nonmakanan_sebulan;

    @ColumnInfo(name = "makanan_sebulan_bypml")
    @Nullable
    public String makanan_sebulan_bypml;

    @ColumnInfo(name = "nonmakanan_sebulan_bypml")
    @Nullable
    public String nonmakanan_sebulan_bypml;

    @ColumnInfo(name = "transportasi")
    @Nullable
    public String transportasi;

    @ColumnInfo(name = "peliharaan")
    @Nullable
    public String peliharaan;

    @ColumnInfo(name = "art_sekolah")
    @Nullable
    public int art_sekolah;

    @ColumnInfo(name = "art_bpjs")
    @Nullable
    public int art_bpjs;

    @ColumnInfo(name = "ijazah_krt")
    @Nullable
    public String ijazah_krt;

    @ColumnInfo(name = "kegiatan_seminggu")
    @Nullable
    public String kegiatan_seminggu;

    @ColumnInfo(name = "deskripsi_kegiatan")
    @Nullable
    public String deskripsi_kegiatan;

    @ColumnInfo(name = "luas_lantai")
    @Nullable
    public int luas_lantai;

    @ColumnInfo(name = "gsmp")
    @Nullable
    public int gsmp;

    @ColumnInfo(name = "gsmp_desk")
    @Nullable
    public String gsmp_desk;

    @ColumnInfo(name = "bantuan")
    @Nullable
    public int bantuan;

    @ColumnInfo(name = "bantuan_desk")
    @Nullable
    public String bantuan_desk;

    @ColumnInfo(name = "latitude")
    @Nullable
    public String latitude;

    @ColumnInfo(name = "longitude")
    @Nullable
    public String longitude;

    @ColumnInfo(name = "latitude_selesai")
    @Nullable
    public String latitude_selesai;

    @ColumnInfo(name = "longitude_selesai")
    @Nullable
    public String longitude_selesai;

    @ColumnInfo(name = "jam_mulai")
    @Nullable
    public String jam_mulai;

    @ColumnInfo(name = "jam_selesai")
    @Nullable
    public String jam_selesai;

    @ColumnInfo(name = "durasi_pencachan")
    @Nullable
    public String durasi_pencacahan;

    @ColumnInfo(name = "pencacah")
    @Nullable
    public String pencacah;

    @ColumnInfo(name = "pengawas")
    @Nullable
    public String pengawas;

    public Dsrt(int id, String tahun, int semester, @Nullable String kd_kab, @Nullable String nama_kab, @Nullable String kd_kec, @Nullable String nama_kec, @Nullable String kd_desa, @Nullable String nama_desa, @Nullable String kd_bs, int nu_rt, @Nullable String nks, int status_pencacahan, @Nullable String nama_krt_prelist, int jml_art_prelist, @Nullable String nama_krt_cacah, int jml_art_cacah, @Nullable String status_rumah, int jml_komoditas_makanan, int jml_komoditas_nonmakanan, @Nullable String makanan_sebulan, @Nullable String nonmakanan_sebulan, @Nullable String makanan_sebulan_bypml, @Nullable String nonmakanan_sebulan_bypml, @Nullable String transportasi, @Nullable String peliharaan, int art_sekolah, int art_bpjs, @Nullable String ijazah_krt, @Nullable String kegiatan_seminggu, @Nullable String deskripsi_kegiatan, int luas_lantai, int gsmp, @Nullable String gsmp_desk, int bantuan, @Nullable String bantuan_desk, @Nullable String latitude, @Nullable String longitude, @Nullable String latitude_selesai, @Nullable String longitude_selesai, @Nullable String jam_mulai, @Nullable String jam_selesai, @Nullable String durasi_pencacahan, @Nullable String pencacah, @Nullable String pengawas) {
        this.id = id;
        this.tahun = tahun;
        this.semester = semester;
        this.kd_kab = kd_kab;
        this.nama_kab = nama_kab;
        this.kd_kec = kd_kec;
        this.nama_kec = nama_kec;
        this.kd_desa = kd_desa;
        this.nama_desa = nama_desa;
        this.kd_bs = kd_bs;
        this.nu_rt = nu_rt;
        this.nks = nks;
        this.status_pencacahan = status_pencacahan;
        this.nama_krt_prelist = nama_krt_prelist;
        this.jml_art_prelist = jml_art_prelist;
        this.nama_krt_cacah = nama_krt_cacah;
        this.jml_art_cacah = jml_art_cacah;
        this.status_rumah = status_rumah;
        this.jml_komoditas_makanan = jml_komoditas_makanan;
        this.jml_komoditas_nonmakanan = jml_komoditas_nonmakanan;
        this.makanan_sebulan = makanan_sebulan;
        this.nonmakanan_sebulan = nonmakanan_sebulan;
        this.makanan_sebulan_bypml = makanan_sebulan_bypml;
        this.nonmakanan_sebulan_bypml = nonmakanan_sebulan_bypml;
        this.transportasi = transportasi;
        this.peliharaan = peliharaan;
        this.art_sekolah = art_sekolah;
        this.art_bpjs = art_bpjs;
        this.ijazah_krt = ijazah_krt;
        this.kegiatan_seminggu = kegiatan_seminggu;
        this.deskripsi_kegiatan = deskripsi_kegiatan;
        this.luas_lantai = luas_lantai;
        this.gsmp = gsmp;
        this.gsmp_desk = gsmp_desk;
        this.bantuan = bantuan;
        this.bantuan_desk = bantuan_desk;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitude_selesai = latitude_selesai;
        this.longitude_selesai = longitude_selesai;
        this.jam_mulai = jam_mulai;
        this.jam_selesai = jam_selesai;
        this.durasi_pencacahan = durasi_pencacahan;
        this.pencacah = pencacah;
        this.pengawas = pengawas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Nullable
    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(@Nullable String kd_kab) {
        this.kd_kab = kd_kab;
    }

    @Nullable
    public String getNama_kab() {
        return nama_kab;
    }

    public void setNama_kab(@Nullable String nama_kab) {
        this.nama_kab = nama_kab;
    }

    @Nullable
    public String getKd_kec() {
        return kd_kec;
    }

    public void setKd_kec(@Nullable String kd_kec) {
        this.kd_kec = kd_kec;
    }

    @Nullable
    public String getNama_kec() {
        return nama_kec;
    }

    public void setNama_kec(@Nullable String nama_kec) {
        this.nama_kec = nama_kec;
    }

    @Nullable
    public String getKd_desa() {
        return kd_desa;
    }

    public void setKd_desa(@Nullable String kd_desa) {
        this.kd_desa = kd_desa;
    }

    @Nullable
    public String getNama_desa() {
        return nama_desa;
    }

    public void setNama_desa(@Nullable String nama_desa) {
        this.nama_desa = nama_desa;
    }

    @Nullable
    public String getKd_bs() {
        return kd_bs;
    }

    public void setKd_bs(@Nullable String kd_bs) {
        this.kd_bs = kd_bs;
    }

    public int getNu_rt() {
        return nu_rt;
    }

    public void setNu_rt(int nu_rt) {
        this.nu_rt = nu_rt;
    }

    @Nullable
    public String getNks() {
        return nks;
    }

    public void setNks(@Nullable String nks) {
        this.nks = nks;
    }

    public int getStatus_pencacahan() {
        return status_pencacahan;
    }

    public void setStatus_pencacahan(int status_pencacahan) {
        this.status_pencacahan = status_pencacahan;
    }

    @Nullable
    public String getNama_krt_prelist() {
        return nama_krt_prelist;
    }

    public void setNama_krt_prelist(@Nullable String nama_krt_prelist) {
        this.nama_krt_prelist = nama_krt_prelist;
    }

    public int getJml_art_prelist() {
        return jml_art_prelist;
    }

    public void setJml_art_prelist(int jml_art_prelist) {
        this.jml_art_prelist = jml_art_prelist;
    }

    @Nullable
    public String getNama_krt_cacah() {
        return nama_krt_cacah;
    }

    public void setNama_krt_cacah(@Nullable String nama_krt_cacah) {
        this.nama_krt_cacah = nama_krt_cacah;
    }

    public int getJml_art_cacah() {
        return jml_art_cacah;
    }

    public void setJml_art_cacah(int jml_art_cacah) {
        this.jml_art_cacah = jml_art_cacah;
    }

    @Nullable
    public String getStatus_rumah() {
        return status_rumah;
    }

    public void setStatus_rumah(@Nullable String status_rumah) {
        this.status_rumah = status_rumah;
    }

    public int getJml_komoditas_makanan() {
        return jml_komoditas_makanan;
    }

    public void setJml_komoditas_makanan(int jml_komoditas_makanan) {
        this.jml_komoditas_makanan = jml_komoditas_makanan;
    }

    public int getJml_komoditas_nonmakanan() {
        return jml_komoditas_nonmakanan;
    }

    public void setJml_komoditas_nonmakanan(int jml_komoditas_nonmakanan) {
        this.jml_komoditas_nonmakanan = jml_komoditas_nonmakanan;
    }

    @Nullable
    public String getMakanan_sebulan() {
        return makanan_sebulan;
    }

    public void setMakanan_sebulan(@Nullable String makanan_sebulan) {
        this.makanan_sebulan = makanan_sebulan;
    }

    @Nullable
    public String getNonmakanan_sebulan() {
        return nonmakanan_sebulan;
    }

    public void setNonmakanan_sebulan(@Nullable String nonmakanan_sebulan) {
        this.nonmakanan_sebulan = nonmakanan_sebulan;
    }

    @Nullable
    public String getMakanan_sebulan_bypml() {
        return makanan_sebulan_bypml;
    }

    public void setMakanan_sebulan_bypml(@Nullable String makanan_sebulan_bypml) {
        this.makanan_sebulan_bypml = makanan_sebulan_bypml;
    }

    @Nullable
    public String getNonmakanan_sebulan_bypml() {
        return nonmakanan_sebulan_bypml;
    }

    public void setNonmakanan_sebulan_bypml(@Nullable String nonmakanan_sebulan_bypml) {
        this.nonmakanan_sebulan_bypml = nonmakanan_sebulan_bypml;
    }

    @Nullable
    public String getTransportasi() {
        return transportasi;
    }

    public void setTransportasi(@Nullable String transportasi) {
        this.transportasi = transportasi;
    }

    @Nullable
    public String getPeliharaan() {
        return peliharaan;
    }

    public void setPeliharaan(@Nullable String peliharaan) {
        this.peliharaan = peliharaan;
    }

    public int getArt_sekolah() {
        return art_sekolah;
    }

    public void setArt_sekolah(int art_sekolah) {
        this.art_sekolah = art_sekolah;
    }

    public int getArt_bpjs() {
        return art_bpjs;
    }

    public void setArt_bpjs(int art_bpjs) {
        this.art_bpjs = art_bpjs;
    }

    @Nullable
    public String getIjazah_krt() {
        return ijazah_krt;
    }

    public void setIjazah_krt(@Nullable String ijazah_krt) {
        this.ijazah_krt = ijazah_krt;
    }

    @Nullable
    public String getKegiatan_seminggu() {
        return kegiatan_seminggu;
    }

    public void setKegiatan_seminggu(@Nullable String kegiatan_seminggu) {
        this.kegiatan_seminggu = kegiatan_seminggu;
    }

    @Nullable
    public String getDeskripsi_kegiatan() {
        return deskripsi_kegiatan;
    }

    public void setDeskripsi_kegiatan(@Nullable String deskripsi_kegiatan) {
        this.deskripsi_kegiatan = deskripsi_kegiatan;
    }

    public int getLuas_lantai() {
        return luas_lantai;
    }

    public void setLuas_lantai(int luas_lantai) {
        this.luas_lantai = luas_lantai;
    }

    public int getGsmp() {
        return gsmp;
    }

    public int getBantuan() {
        return bantuan;
    }

    public void setGsmp(int gsmp) {
        this.gsmp = gsmp;
    }

    public void setBantuan(int bantuan) {
        this.bantuan = bantuan;
    }

    @Nullable
    public String getGsmp_desk() {
        return gsmp_desk;
    }

    @Nullable
    public String getBantuan_desk() {
        return bantuan_desk;
    }

    public void setGsmp_desk(@Nullable String gsmp_desk) {
        this.gsmp_desk = gsmp_desk;
    }

    public void setBantuan_desk(@Nullable String bantuan_desk) {
        this.bantuan_desk = bantuan_desk;
    }

    @Nullable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable String latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable String longitude) {
        this.longitude = longitude;
    }

    @Nullable
    public String getLatitude_selesai() {
        return latitude_selesai;
    }

    public void setLatitude_selesai(@Nullable String latitude_selesai) {
        this.latitude_selesai = latitude_selesai;
    }

    @Nullable
    public String getLongitude_selesai() {
        return longitude_selesai;
    }

    public void setLongitude_selesai(@Nullable String longitude_selesai) {
        this.longitude_selesai = longitude_selesai;
    }

    @Nullable
    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(@Nullable String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    @Nullable
    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(@Nullable String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    @Nullable
    public String getDurasi_pencacahan() {
        return durasi_pencacahan;
    }

    public void setDurasi_pencacahan(@Nullable String durasi_pencacahan) {
        this.durasi_pencacahan = durasi_pencacahan;
    }

    @Nullable
    public String getPencacah() {
        return pencacah;
    }

    public void setPencacah(@Nullable String pencacah) {
        this.pencacah = pencacah;
    }

    @Nullable
    public String getPengawas() {
        return pengawas;
    }

    public void setPengawas(@Nullable String pengawas) {
        this.pengawas = pengawas;
    }
}
