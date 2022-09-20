package com.example.siemas.RoomDatabase.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.siemas.RoomDatabase.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("Select * FROM User")
    List<User> getAllUser();

    @Insert(entity = User.class, onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("DELETE FROM user")
    int nukeUser();
}
