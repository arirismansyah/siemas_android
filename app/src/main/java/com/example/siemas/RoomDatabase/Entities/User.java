package com.example.siemas.RoomDatabase.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "kd_wilayah")
    @Nullable
    public String kd_wilayah;

    @ColumnInfo(name = "nama_kab")
    @Nullable
    public String nama_kab;

    @ColumnInfo(name = "pengawas")
    @Nullable
    public  String pengawas;

    @ColumnInfo(name = "alamat")
    @Nullable
    public  String alamat;

    @ColumnInfo(name = "no_hp")
    @Nullable
    public  String no_hp;

    @ColumnInfo(name = "role")
    @Nullable
    public  String role;


    @ColumnInfo(name = "token")
    @Nullable
    public  String token;

    public User(int id, String username, String email, String name, @Nullable String kd_wilayah, @Nullable String nama_kab, @Nullable String pengawas, @Nullable String alamat, @Nullable String no_hp, @Nullable String role, @Nullable String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.kd_wilayah = kd_wilayah;
        this.nama_kab = nama_kab;
        this.pengawas = pengawas;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.role = role;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    public String getKd_wilayah() {
        return kd_wilayah;
    }

    public void setKd_wilayah(@Nullable String kd_wilayah) {
        this.kd_wilayah = kd_wilayah;
    }

    @Nullable
    public String getNama_kab() {
        return nama_kab;
    }

    public void setNama_kab(@Nullable String nama_kab) {
        this.nama_kab = nama_kab;
    }

    @Nullable
    public String getPengawas() {
        return pengawas;
    }

    public void setPengawas(@Nullable String pengawas) {
        this.pengawas = pengawas;
    }

    @Nullable
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(@Nullable String alamat) {
        this.alamat = alamat;
    }

    @Nullable
    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(@Nullable String no_hp) {
        this.no_hp = no_hp;
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
