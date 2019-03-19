package com.example.testrepositoryjava.repository.db;


import com.example.testrepositoryjava.repository.data.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 6 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
