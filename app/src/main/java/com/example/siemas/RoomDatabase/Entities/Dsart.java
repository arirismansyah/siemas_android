package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id_bs", "tahun","semester","nu_rt", "nu_art"},unique = true)})

public class Dsart {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "id_bs")
    public String id_bs;
    @ColumnInfo(name = "kd_kab")
    public String kd_kab;
    @ColumnInfo(name = "nks")
    public String nks;
    @ColumnInfo(name = "tahun")
    public String tahun;
    @ColumnInfo(name = "semester")
    public int semester;
    @ColumnInfo(name = "nu_rt")
    public int nu_rt;
    @ColumnInfo(name = "nu_art")
    public int nu_art;
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

    public Dsart( String id_bs, String kd_kab, String nks, String tahun, int semester, int nu_rt, int nu_art, @Nullable String nama_art, @Nullable String pekerjaan, @Nullable String pendapatan, @Nullable String pendidikan) {

        this.id_bs = id_bs;
        this.kd_kab = kd_kab;
        this.nks = nks;
        this.tahun = tahun;
        this.semester = semester;
        this.nu_rt = nu_rt;
        this.nu_art = nu_art;
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

    public String getId_bs() {
        return id_bs;
    }

    public void setId_bs(String id_bs) {
        this.id_bs = id_bs;
    }

    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(String kd_kab) {
        this.kd_kab = kd_kab;
    }

    public String getNks() {
        return nks;
    }

    public void setNks(String nks) {
        this.nks = nks;
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

    public String getNama_art() {
        return nama_art;
    }

    public void setNama_art(String nama_art) {
        this.nama_art = nama_art;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(String pendapatan) {
        this.pendapatan = pendapatan;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }
}
