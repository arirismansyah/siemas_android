package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dsbs {
    @PrimaryKey
    public int id;

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

    @ColumnInfo(name = "nbs")
    public String nbs;

    @ColumnInfo(name = "id_bs")
    public String id_bs;

    @ColumnInfo(name = "nks")
    public String nks;

    @ColumnInfo(name = "status")
    @Nullable
    public int status;

    @ColumnInfo(name = "jml_rt_c1")
    @Nullable
    public int jml_rt_c1;

    @ColumnInfo(name = "sumber")
    @Nullable
    public String sumber;

    @ColumnInfo(name = "pencacah")
    @Nullable
    public String pencacah;

    @ColumnInfo(name = "pengawas")
    @Nullable
    public String pengawas;

    public Dsbs(int id, String kd_kab, String nama_kab, String kd_kec, String nama_kec, String kd_desa, String nama_desa, String nbs, String id_bs, String nks, int status, int jml_rt_c1, @Nullable String sumber, @Nullable String pencacah, @Nullable String pengawas) {
        this.id = id;
        this.kd_kab = kd_kab;
        this.nama_kab = nama_kab;
        this.kd_kec = kd_kec;
        this.nama_kec = nama_kec;
        this.kd_desa = kd_desa;
        this.nama_desa = nama_desa;
        this.nbs = nbs;
        this.id_bs = id_bs;
        this.nks = nks;
        this.status = status;
        this.jml_rt_c1 = jml_rt_c1;
        this.sumber = sumber;
        this.pencacah = pencacah;
        this.pengawas = pengawas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNbs() {
        return nbs;
    }

    public void setNbs(String nbs) {
        this.nbs = nbs;
    }

    public String getId_bs() {
        return id_bs;
    }

    public void setId_bs(String id_bs) {
        this.id_bs = id_bs;
    }

    public String getNks() {
        return nks;
    }

    public void setNks(String nks) {
        this.nks = nks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getJml_rt_c1() {
        return jml_rt_c1;
    }

    public void setJml_rt_c1(int jml_rt_c1) {
        this.jml_rt_c1 = jml_rt_c1;
    }

    @Nullable
    public String getSumber() {
        return sumber;
    }

    public void setSumber(@Nullable String sumber) {
        this.sumber = sumber;
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
