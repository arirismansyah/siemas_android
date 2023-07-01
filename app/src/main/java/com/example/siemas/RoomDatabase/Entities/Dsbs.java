package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dsbs {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "tahun")
    public String tahun;

    @ColumnInfo(name = "semester")
    public int semester;

    @ColumnInfo(name = "kd_kab")
    public String kd_kab;

    @ColumnInfo(name = "nama_kab")
    public String nama_kab;

    @ColumnInfo(name = "kd_kec")
    public String kd_kec;

    @ColumnInfo(name = "nama_kec")
    public String nama_kec;

    @ColumnInfo(name = "kd_desa")
    public String kd_desa;

    @ColumnInfo(name = "nama_desa")
    public String nama_desa;

    @ColumnInfo(name = "kd_bs")
    public String kd_bs;

    @ColumnInfo(name = "nks")
    public String nks;

    @ColumnInfo(name = "jml_rt")
    @Nullable
    public int jml_rt;

    @ColumnInfo(name = "pencacah")
    @Nullable
    public String pencacah;

    @ColumnInfo(name = "pengawas")
    @Nullable
    public String pengawas;

    public Dsbs(int id, String tahun, int semester, String kd_kab, String nama_kab, String kd_kec, String nama_kec, String kd_desa, String nama_desa, String kd_bs, String nks, int jml_rt, @Nullable String pencacah, @Nullable String pengawas) {
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
        this.nks = nks;
        this.jml_rt = jml_rt;
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

    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(String kd_kab) {
        this.kd_kab = kd_kab;
    }

    public String getNama_kab() {
        return nama_kab;
    }

    public void setNama_kab(String nama_kab) {
        this.nama_kab = nama_kab;
    }

    public String getKd_kec() {
        return kd_kec;
    }

    public void setKd_kec(String kd_kec) {
        this.kd_kec = kd_kec;
    }

    public String getNama_kec() {
        return nama_kec;
    }

    public void setNama_kec(String nama_kec) {
        this.nama_kec = nama_kec;
    }

    public String getKd_desa() {
        return kd_desa;
    }

    public void setKd_desa(String kd_desa) {
        this.kd_desa = kd_desa;
    }

    public String getNama_desa() {
        return nama_desa;
    }

    public void setNama_desa(String nama_desa) {
        this.nama_desa = nama_desa;
    }

    public String getKd_bs() {
        return kd_bs;
    }

    public void setKd_bs(String kd_bs) {
        this.kd_bs = kd_bs;
    }

    public String getNks() {
        return nks;
    }

    public void setNks(String nks) {
        this.nks = nks;
    }

    public int getJml_rt() {
        return jml_rt;
    }

    public void setJml_rt(int jml_rt) {
        this.jml_rt = jml_rt;
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
