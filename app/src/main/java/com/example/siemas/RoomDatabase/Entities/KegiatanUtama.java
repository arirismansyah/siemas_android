package com.example.siemas.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KegiatanUtama {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "kegiatan_utama")
    public String kegiatan_utama;

    public KegiatanUtama(int id, String kegiatan_utama) {
        this.id = id;
        this.kegiatan_utama = kegiatan_utama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKegiatan_utama() {
        return kegiatan_utama;
    }

    public void setKegiatan_utama(String kegiatan_utama) {
        this.kegiatan_utama = kegiatan_utama;
    }
}
