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
    @Query("SELECT * FROM UserData WHERE userID = (:userID)")
    UserData getUserDataByID(int userID);

    // Adds a new user to the database (id is auto-incremented)
    @Query("INSERT INTO UserData (age,name,bio,username,password) VALUES (:age,:name,:bio,:username,:password)")
    void addUserData(int age, String name, String bio, String username, String password);

    // Deletes a user from the database
    @Query("DELETE FROM UserData WHERE userID = (:userID)")
    void deleteUserByID(int userID);

    // Updates the data of the user given by userID (Requires all data to be filled; or it might not but idk what might happen)
    // NOTE: updates are slow and may not take a while before the new data can be retrieved;
    // we could consider using some sort of cache to access recently used data
    @Query("UPDATE UserData SET age = :age, name = :name, bio = :bio, username = :username, password = :password WHERE userID = (:userID)")
    void updateUserData(int userID, int age, String name, String bio, String username, String password);
}
