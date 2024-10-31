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

    // returns the userID of a user based on their current username; returns 0 if user does not exist
    @Query("SELECT userID FROM UserData WHERE username = (:username)")
    int getUserIDFromUsername(String username);

    // Adds a new user to the database (id is auto-incremented)
    // Make sure to check the database with getUserIDFromUsername to check if the username already exists
    // (You may need to just pass in an empty string for anything that's currently blank)
    @Query("INSERT INTO UserData (age,name,bio,username,password,salt) VALUES (:age,:name,:bio,:username,:password,:salt)")
    void addUserData(int age, String name, String bio, String username, String password, String salt);


    // Deletes a user from the database
    @Query("DELETE FROM UserData WHERE userID = (:userID)")
    void deleteUserByID(int userID);

    // Updates the data of the user given by userID (Requires all data to be filled; or it might not but idk what might happen)
    // Make sure if updating username to check getUserIDFromUsername to see if the username already exists
    // NOTE: updates are slow and may take a while before the new data can be retrieved;
    // we could consider using some sort of cache to access recently used data
    @Query("UPDATE UserData SET age = :age, name = :name, bio = :bio, username = :username, password = :password, salt = :salt WHERE userID = (:userID)")
    void updateUserData(int userID, int age, String name, String bio, String username, String password, String salt);

    // Updates the salt of a user
    @Query(("UPDATE UserData SET salt = :salt WHERE userID = (:userID)"))
    void updateSalt(int userID, String salt);
}
