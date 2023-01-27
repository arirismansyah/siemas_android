package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"id_bs","tahun" ,"semester","nu_rt"})
public class Laporan212 {
    @NonNull
    @ColumnInfo(name = "id_bs")
    public String id_bs;

    @Nullable
    @ColumnInfo(name = "nks")
    public String nks;

    @NonNull
    @ColumnInfo(name = "tahun")
    public String tahun;

    @NonNull
    @ColumnInfo(name = "semester")
    public int semester;

    @NonNull
    @ColumnInfo(name = "nu_rt")
    public int nu_rt;
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


    public Laporan212(@NonNull String id_bs, @Nullable String nks, @NonNull String tahun, int semester, int nu_rt, @Nullable String nama_krt, @Nullable String pengawas, @Nullable String tanggal, int status) {
        this.id_bs = id_bs;
        this.nks = nks;
        this.tahun = tahun;
        this.semester = semester;
        this.nu_rt = nu_rt;
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
    public String getId_bs() {
        return id_bs;
    }

    public void setId_bs(@NonNull String id_bs) {
        this.id_bs = id_bs;
    }

    @Nullable
    public String getNks() {
        return nks;
    }

    public void setNks(@Nullable String nks) {
        this.nks = nks;
    }

    public int getNu_rt() {
        return nu_rt;
    }

    public void setNu_rt(int nu_rt) {
        this.nu_rt = nu_rt;
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
