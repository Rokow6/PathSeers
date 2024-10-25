package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database table class for user data
@Entity
public class UserData {
    @PrimaryKey(autoGenerate = true)
    public int userID;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "bio")
    public String bio;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;
}
