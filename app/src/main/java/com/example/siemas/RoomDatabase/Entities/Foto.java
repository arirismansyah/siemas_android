package com.example.siemas.RoomDatabase.Entities;

import static java.sql.Types.BLOB;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Foto {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "kd_kab")
    @Nullable
    public String kd_kab;

    @ColumnInfo(name = "kd_kec")
    @Nullable
    public String kd_kec;

    @ColumnInfo(name = "kd_desa")
    @Nullable
    public String kd_desa;

    @ColumnInfo(name = "kd_bs")
    @Nullable
    public String kd_bs;

    @ColumnInfo(name = "nks")
    @Nullable
    public String nks;

    @ColumnInfo(name = "tahun")
    public String tahun;

    @ColumnInfo(name = "semester")
    public int semester;

    @ColumnInfo(name = "nu_rt")
    public int nu_rt;

    @ColumnInfo(name = "status_foto")
    @Nullable
    public int status_foto;

    @ColumnInfo(name = "foto", typeAffinity = BLOB)
    @Nullable
    public byte[] foto;

    public Foto(int id, @Nullable String kd_kab, @Nullable String kd_kec, @Nullable String kd_desa, @Nullable String kd_bs, @Nullable String nks, String tahun, int semester, int nu_rt, int status_foto, @Nullable byte[] foto) {
        this.id = id;
        this.kd_kab = kd_kab;
        this.kd_kec = kd_kec;
        this.kd_desa = kd_desa;
        this.kd_bs = kd_bs;
        this.nks = nks;
        this.tahun = tahun;
        this.semester = semester;
        this.nu_rt = nu_rt;
        this.status_foto = status_foto;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(@Nullable String kd_kab) {
        this.kd_kab = kd_kab;
    }

    @Nullable
    public String getKd_kec() {
        return kd_kec;
    }

    public void setKd_kec(@Nullable String kd_kec) {
        this.kd_kec = kd_kec;
    }

    @Nullable
    public String getKd_desa() {
        return kd_desa;
    }

    public void setKd_desa(@Nullable String kd_desa) {
        this.kd_desa = kd_desa;
    }

    @Nullable
    public String getKd_bs() {
        return kd_bs;
    }

    public void setKd_bs(@Nullable String kd_bs) {
        this.kd_bs = kd_bs;
    }

    @Nullable
    public String getNks() {
        return nks;
    }

    public void setNks(@Nullable String nks) {
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

    public int getStatus_foto() {
        return status_foto;
    }

    public void setStatus_foto(int status_foto) {
        this.status_foto = status_foto;
    }

    @Nullable
    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(@Nullable byte[] foto) {
        this.foto = foto;
    }
}
