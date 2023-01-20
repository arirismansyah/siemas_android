package com.example.siemas.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Dsart {
    @PrimaryKey
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
    public String semester;
    @ColumnInfo(name = "nu_rt")
    public String nu_rt;
    @ColumnInfo(name = "nu_art")
    public String nu_art;
    @ColumnInfo(name = "nama_art")
    public String nama_art;
    @ColumnInfo(name = "pekerjaan")
    public String pekerjaan;
    @ColumnInfo(name = "pendapatan")
    public String pendapatan;
    @ColumnInfo(name = "pendidikan")
    public String pendidikan;

    public Dsart(int id, String id_bs, String kd_kab, String nks, String tahun, String semester, String nu_rt, String nu_art, String nama_art, String pekerjaan, String pendapatan, String pendidikan) {
        this.id = id;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getNu_rt() {
        return nu_rt;
    }

    public void setNu_rt(String nu_rt) {
        this.nu_rt = nu_rt;
    }

    public String getNu_art() {
        return nu_art;
    }

    public void setNu_art(String nu_art) {
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
