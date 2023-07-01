package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"tahun" ,"semester","kd_kab","kd_kec", "kd_desa","kd_bs", "nu_rt"})
public class Laporan212 {
    @NonNull
    @ColumnInfo(name = "tahun")
    public String tahun;

    @NonNull
    @ColumnInfo(name = "semester")
    public int semester;

    @NonNull
    @ColumnInfo(name = "kd_kab")
    public String kd_kab;

    @NonNull
    @ColumnInfo(name = "kd_kec")
    public String kd_kec;

    @NonNull
    @ColumnInfo(name = "kd_desa")
    public String kd_desa;

    @NonNull
    @ColumnInfo(name = "kd_bs")
    public String kd_bs;

    @NonNull
    @ColumnInfo(name = "nu_rt")
    public int nu_rt;

    @Nullable
    @ColumnInfo(name = "nks")
    public String nks;

    @Nullable
    @ColumnInfo(name = "nama_krt")
    public String nama_krt;

    @Nullable
    @ColumnInfo(name = "pengawas")
    public String pengawas;

    @Nullable
    @ColumnInfo(name = "tanggal")
    public String tanggal;

    @Nullable
    @ColumnInfo(name = "status")
    public int status;

    public Laporan212(@NonNull String tahun, int semester, @NonNull String kd_kab, @NonNull String kd_kec, @NonNull String kd_desa, @NonNull String kd_bs, int nu_rt, @Nullable String nks, @Nullable String nama_krt, @Nullable String pengawas, @Nullable String tanggal, int status) {
        this.tahun = tahun;
        this.semester = semester;
        this.kd_kab = kd_kab;
        this.kd_kec = kd_kec;
        this.kd_desa = kd_desa;
        this.kd_bs = kd_bs;
        this.nu_rt = nu_rt;
        this.nks = nks;
        this.nama_krt = nama_krt;
        this.pengawas = pengawas;
        this.tanggal = tanggal;
        this.status = status;
    }

    @NonNull
    public String getTahun() {
        return tahun;
    }

    public void setTahun(@NonNull String tahun) {
        this.tahun = tahun;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @NonNull
    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(@NonNull String kd_kab) {
        this.kd_kab = kd_kab;
    }

    @NonNull
    public String getKd_kec() {
        return kd_kec;
    }

    public void setKd_kec(@NonNull String kd_kec) {
        this.kd_kec = kd_kec;
    }

    @NonNull
    public String getKd_desa() {
        return kd_desa;
    }

    public void setKd_desa(@NonNull String kd_desa) {
        this.kd_desa = kd_desa;
    }

    @NonNull
    public String getKd_bs() {
        return kd_bs;
    }

    public void setKd_bs(@NonNull String kd_bs) {
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

    @Nullable
    public String getNama_krt() {
        return nama_krt;
    }

    public void setNama_krt(@Nullable String nama_krt) {
        this.nama_krt = nama_krt;
    }

    @Nullable
    public String getPengawas() {
        return pengawas;
    }

    public void setPengawas(@Nullable String pengawas) {
        this.pengawas = pengawas;
    }

    @Nullable
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(@Nullable String tanggal) {
        this.tanggal = tanggal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
