package com.example.siemas.RoomDatabase.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StatusRumah {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "status_rumah")
    public String status_rumah;

    public StatusRumah(int id, String status_rumah) {
        this.id = id;
        this.status_rumah = status_rumah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus_rumah() {
        return status_rumah;
    }

    public void setStatus_rumah(String status_rumah) {
        this.status_rumah = status_rumah;
    }
}
