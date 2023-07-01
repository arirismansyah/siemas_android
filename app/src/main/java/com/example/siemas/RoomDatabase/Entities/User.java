package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "kd_kab")
    @Nullable
    public String kd_kab;

    @ColumnInfo(name = "nama_kab")
    @Nullable
    public String nama_kab;

    @ColumnInfo(name = "role")
    @Nullable
    public  String role;

    @ColumnInfo(name = "token")
    @Nullable
    public  String token;


    public User(int id, String email, String name, @Nullable String kd_kab, @Nullable String nama_kab, @Nullable String role, @Nullable String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.kd_kab = kd_kab;
        this.nama_kab = nama_kab;
        this.role = role;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(@Nullable String kd_kab) {
        this.kd_kab = kd_kab;
    }

    @Nullable
    public String getNama_kab() {
        return nama_kab;
    }

    public void setNama_kab(@Nullable String nama_kab) {
        this.nama_kab = nama_kab;
    }

    @Nullable
    public String getRole() {
        return role;
    }

    public void setRole(@Nullable String role) {
        this.role = role;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
    }
}
