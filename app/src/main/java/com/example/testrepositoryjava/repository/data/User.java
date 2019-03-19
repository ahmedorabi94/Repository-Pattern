package com.example.testrepositoryjava.repository.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {


    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "firstName")
    public String first;

    @ColumnInfo(name = "lastName")
    public String last;


    public User() {

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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }


}
