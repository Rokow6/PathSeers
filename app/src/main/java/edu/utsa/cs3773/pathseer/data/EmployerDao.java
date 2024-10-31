package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for employer data
@Dao
public interface EmployerDao {
    // Returns a list of all employer data, that is the list of employer IDs with their associated user ID
    @Query("SELECT * FROM EmployerData")
    List<EmployerData> getAll();

    // Returns a list of all user data of employer users
    @Query("SELECT UserData.userID, UserData.age, UserData.name, UserData.bio, UserData.username, UserData.password" +
            " FROM UserData JOIN EmployerData ON EmployerData.fk_userID = UserData.userID")
    List<UserData> getAllEmployerUserData();

    // Returns the user data of the user specified by the employer ID if they are an employer
    @Query("SELECT UserData.userID, UserData.age, UserData.name, UserData.bio, UserData.username, UserData.password" +
            " FROM UserData JOIN EmployerData ON EmployerData.fk_userID = UserData.userID WHERE EmployerData.employerID = (:employerID)")
    UserData getEmployerUserDataByEmployerID(int employerID);

    // Returns the user ID of a user based on their employer ID
    @Query("SELECT fk_userID FROM EmployerData WHERE employerID = (:employerID)")
    int getUserIDFromEmployerID(int employerID);

    // Returns the employer ID of a user based on their user ID if they are an employer
    // Returns 0 if the user is not an employer
    @Query("SELECT employerID FROM EmployerData WHERE fk_userID = (:userID)")
    int getEmployerIDFromUserID(int userID);

    // Adds a user as an employer using their user ID
    @Query("INSERT INTO EmployerData (fk_userID) VALUES (:userID)")
    void addEmployerData(int userID);

    // Removes an employer by employer ID (NOTE: this does not delete user data, just the employer status of a user)
    @Query("DELETE FROM EmployerData WHERE employerID = (:employerID)")
    void deleteEmployerByEmployerID(int employerID);

    // Removes an employer by user ID (NOTE: this does not delete user data, just the employer status of a user)
    @Query("DELETE FROM EmployerData WHERE fk_userID = (:userID)")
    void deleteEmployerByUserID(int userID);

}
