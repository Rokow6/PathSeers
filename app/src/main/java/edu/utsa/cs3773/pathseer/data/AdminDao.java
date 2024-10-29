package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for admin data
@Dao
public interface AdminDao {
    // Returns a list of all admin data, that is the list of admin IDs with their associated user ID
    @Query("SELECT * FROM AdminData")
    List<AdminData> getAll();

    // Returns a list of all user data of admin users
    @Query("SELECT UserData.userID, UserData.age, UserData.name, UserData.bio, UserData.username, UserData.password" +
            " FROM UserData JOIN AdminData ON AdminData.fk_userID = UserData.userID")
    List<UserData> getAllAdminUserData();

    // Returns the user data of the user specified by the admin ID if they are an admin
    @Query("SELECT UserData.userID, UserData.age, UserData.name, UserData.bio, UserData.username, UserData.password" +
            " FROM UserData JOIN AdminData ON AdminData.fk_userID = UserData.userID WHERE AdminData.adminID = (:adminID)")
    UserData getAdminUserDataByAdminID(int adminID);

    // Returns the user ID of a user based on their admin ID
    @Query("SELECT fk_userID FROM AdminData WHERE adminID = (:adminID)")
    int getUserIDFromAdminID(int adminID);

    // Returns the admin ID of a user based on their user ID if they are an admin
    // Returns 0 if the user is not an admin
    @Query("SELECT adminID FROM AdminData WHERE fk_userID = (:userID)")
    int getAdminIDFromUserID(int userID);

    // Adds a user as an admin using their user ID
    @Query("INSERT INTO AdminData (fk_userID) VALUES (:userID)")
    void addAdminData(int userID);

    // Removes a user by admin ID (NOTE: this does not delete user data, just the admin status of a user)
    @Query("DELETE FROM AdminData WHERE adminID = (:adminID)")
    void deleteAdminByAdminID(int adminID);

    // Removes a user by user ID (NOTE: this does not delete user data, just the admin status of a user)
    @Query("DELETE FROM AdminData WHERE fk_userID = (:userID)")
    void deleteAdminByUserID(int userID);

}
