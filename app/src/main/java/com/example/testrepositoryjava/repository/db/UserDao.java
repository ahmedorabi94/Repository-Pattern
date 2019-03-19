package com.example.testrepositoryjava.repository.db;


import com.example.testrepositoryjava.repository.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);


    @Query("SELECT  * FROM users")
    LiveData<List<User>> getAllUsers();


    @Delete
    void deleteUsers(List<User> users);


}
