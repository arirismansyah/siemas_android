package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"tahun","semester","kd_kab","kd_kec", "kd_desa","kd_bs","nu_rt", "nu_art"},unique = true)})
public class Dsart {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "tahun")
    public String tahun;
    @ColumnInfo(name = "semester")
    public int semester;

    @ColumnInfo(name = "kd_kab")
    public String kd_kab;
    @ColumnInfo(name = "kd_kec")
    public String kd_kec;
    @ColumnInfo(name = "kd_desa")
    public String kd_desa;
    @ColumnInfo(name = "kd_bs")
    public String kd_bs;
    @ColumnInfo(name = "nu_rt")
    public int nu_rt;
    @ColumnInfo(name = "nu_art")
    public int nu_art;

    @Nullable
    @ColumnInfo(name = "nks")
    public String nks;

    @Nullable
    @ColumnInfo(name = "nama_art")
    public String nama_art;
    @Nullable
    @ColumnInfo(name = "pekerjaan")
    public String pekerjaan;
    @Nullable
    @ColumnInfo(name = "pendapatan")
    public String pendapatan;
    @Nullable
    @ColumnInfo(name = "pendidikan")
    public String pendidikan;

    public Dsart( String tahun, int semester, String kd_kab, String kd_kec, String kd_desa, String kd_bs, int nu_rt, int nu_art, @Nullable String nks, @Nullable String nama_art, @Nullable String pekerjaan, @Nullable String pendapatan, @Nullable String pendidikan) {
        this.tahun = tahun;
        this.semester = semester;
        this.kd_kab = kd_kab;
        this.kd_kec = kd_kec;
        this.kd_desa = kd_desa;
        this.kd_bs = kd_bs;
        this.nu_rt = nu_rt;
        this.nu_art = nu_art;
        this.nks = nks;
        this.nama_art = nama_art;
        this.pekerjaan = pekerjaan;
        this.pendapatan = pendapatan;
        this.pendidikan = pendidikan;
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

    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(String kd_kab) {
        this.kd_kab = kd_kab;
    }

    public String getKd_kec() {
        return kd_kec;
    }

    public void setKd_kec(String kd_kec) {
        this.kd_kec = kd_kec;
    }

    public String getKd_desa() {
        return kd_desa;
    }

    public void setKd_desa(String kd_desa) {
        this.kd_desa = kd_desa;
    }

    public String getKd_bs() {
        return kd_bs;
    }

    public void setKd_bs(String kd_bs) {
        this.kd_bs = kd_bs;
    }

    public int getNu_rt() {
        return nu_rt;
    }

    public void setNu_rt(int nu_rt) {
        this.nu_rt = nu_rt;
    }

    public int getNu_art() {
        return nu_art;
    }

    public void setNu_art(int nu_art) {
        this.nu_art = nu_art;
    }

    @Nullable
    public String getNks() {
        return nks;
    }

    public void setNks(@Nullable String nks) {
        this.nks = nks;
    }

    @Nullable
    public String getNama_art() {
        return nama_art;
    }

    public void setNama_art(@Nullable String nama_art) {
        this.nama_art = nama_art;
    }

    @Nullable
    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(@Nullable String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    @Nullable
    public String getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(@Nullable String pendapatan) {
        this.pendapatan = pendapatan;
    }

    @Nullable
    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(@Nullable String pendidikan) {
        this.pendidikan = pendidikan;
    }
}
