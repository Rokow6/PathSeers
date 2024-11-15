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

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "salt")
    public String salt;

    // Constructor for creating UserData instances
    public UserData(int age, String name, String bio, String email, String username, String password, String salt) {
        this.age = age;
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}
