package com.example.siemas.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Periode {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "tahun")
    public String tahun;

    @ColumnInfo(name = "semester")
    public int semester;

    @ColumnInfo(name = "bulan")
    public String bulan;

    public Periode(int id, String tahun, int semester, String bulan) {
        this.id = id;
        this.tahun = tahun;
        this.semester = semester;
        this.bulan = bulan;
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

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }
}
