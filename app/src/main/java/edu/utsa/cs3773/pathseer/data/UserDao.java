package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for user data; provides functions to call SQL queries
@Dao
public interface UserDao {
    // Returns a list of all user data in the database
    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    // Returns the user data of the specified user
    @Query("SELECT * FROM UserData WHERE userID = (:id)")
    UserData getUserDataByID(int id);

    // Adds a new user to the database (id is auto-incremented)
    @Query("INSERT INTO UserData (age,name,bio,username,password) VALUES (:age,:name,:bio,:username,:password)")
    void addUserData(int age, String name, String bio, String username, String password);

    // Deletes a user from the database
    @Query("DELETE FROM UserData WHERE userID = (:id)")
    void deleteUserByID(int id);
}
