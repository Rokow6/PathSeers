package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Data access object for user data; provides functions to call SQL queries
@Dao
public interface UserDao {
    // Returns a list of all user data in the database
    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    // Returns a list of all user data except password and salt
    @Query("SELECT userID, age, name, bio, email, username FROM UserData")
    List<UserData> getAllSecure();

    // Returns the user data of the specified user
    @Query("SELECT * FROM UserData WHERE userID = (:userID)")
    UserData getUserDataByID(int userID);

    // returns the userID of a user based on their current username; returns 0 if user does not exist
    @Query("SELECT userID FROM UserData WHERE username = (:username)")
    int getUserIDFromUsername(String username);

    //returns the UserID of a user based on their current email; returns 0 if user does not exist
    @Query("SELECT userID FROM UserData WHERE email = (:email)")
    int getUserIDFromEmail(String email);

    //Returns the user data for a specified username
    @Query("SELECT * FROM UserData WHERE username = :username")
    UserData getUserDataByUsername(String username);

    // Returns the employer ID for a specified username
    // Will return 0 if user is not an employer
    @Query("SELECT EmployerData.employerID FROM UserData JOIN EmployerData ON userID = fk_userID WHERE username = (:username)")
    int getEmployerIDFromUsername(String username);

    // Returns the job seeker ID for a specified username
    // Will return 0 if the user is not a job seeker
    @Query("SELECT JobSeekerData.jobSeekerID FROM UserData JOIN JobSeekerData ON userID = fk_userID WHERE username = (:username)")
    int getJobSeekerIDFromUsername(String username);

    // Adds a new user to the database (id is auto-incremented)
    // Make sure to check the database with getUserIDFromUsername to check if the username already exists
    // (You may need to just pass in an empty string for anything that's currently blank)
    @Query("INSERT INTO UserData (age,name,bio,email,username,password,salt) VALUES (:age,:name,:bio,:email,:username,:password,:salt)")
    void addUserData(int age, String name, String bio, String email, String username, String password, String salt);

    @Insert
    long addUserData(UserData userData);

    // Deletes a user from the database
    @Query("DELETE FROM UserData WHERE userID = (:userID)")
    void deleteUserByID(int userID);

    // Updates the data of the user given by userID (Requires all data to be filled; or it might not but idk what might happen)
    // Make sure if updating username to check getUserIDFromUsername to see if the username already exists
    // NOTE: updates are slow and may take a while before the new data can be retrieved;
    // we could consider using some sort of cache to access recently used data
    @Query("UPDATE UserData SET age = :age, name = :name, bio = :bio, email = :email, username = :username, password = :password, salt = :salt WHERE userID = (:userID)")
    void updateUserData(int userID, int age, String name, String bio, String email, String username, String password, String salt);

    //Update all fields in UserData (including password and salt)
    @Update
    void updateUserData(UserData userData);

    // Updates the salt of a user
    @Query("UPDATE UserData SET salt = :salt WHERE userID = :userID")
    void updateSalt(int userID, String salt);

    // Updates the password of a user
    @Query("UPDATE UserData SET password = :password WHERE userID = :userID")
    void updatePassword(int userID, String password);
}
