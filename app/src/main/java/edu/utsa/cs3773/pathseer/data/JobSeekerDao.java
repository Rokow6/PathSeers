package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Data access object for job seeker data
@Dao
public interface JobSeekerDao {
    // Returns a list of all job seeker data, that is the list of job seeker IDs with their associated user ID and resume path
    @Query("SELECT * FROM JobSeekerData")
    List<JobSeekerData> getAll();

    // Returns a list of all user data of job seeker users; does not include resume path
    @Query("SELECT UserData.userID, UserData.age, UserData.name, UserData.bio, UserData.username, UserData.password" +
            " FROM UserData JOIN JobSeekerData ON JobSeekerData.fk_userID = UserData.userID")
    List<UserData> getAllJobSeekerUserData();

    // Returns the user data of the user specified by the job seeker ID if they are a job seeker; does not include resume path
    @Query("SELECT UserData.userID, UserData.age, UserData.name, UserData.bio, UserData.username, UserData.password" +
            " FROM UserData JOIN JobSeekerData ON JobSeekerData.fk_userID = UserData.userID WHERE JobSeekerData.jobSeekerID = (:jobSeekerID)")
    UserData getJobSeekerUserDataByJobSeekerID(int jobSeekerID);

    // Returns the user ID of a user based on their job seeker ID
    @Query("SELECT fk_userID FROM JobSeekerData WHERE jobSeekerID = (:jobSeekerID)")
    int getUserIDFromJobSeekerID(int jobSeekerID);

    // Returns the job seeker ID of a user based on their user ID if they are a job seeker
    // Returns 0 if the user is not a job seeker
    @Query("SELECT jobSeekerID FROM JobSeekerData WHERE fk_userID = (:userID)")
    int getJobSeekerIDFromUserID(int userID);

    // Returns the resume uri string based on the job seeker id
    // The resume uri string can be converted back into a uri with Uri.parse(string)
    @Query("SELECT resumeUriString FROM JobSeekerData WHERE jobSeekerID = (:jobSeekerID)")
    String getResumeUriStringFromJobSeekerID(int jobSeekerID);

    // Returns the resume's file name based on the job seeker id
    @Query("SELECT resumeFileName FROM JobSeekerData WHERE jobSeekerID = (:jobSeekerID)")
    String getResumeFileNameFromJobSeekerID(int jobSeekerID);

    // Returns name of user based on job seeker ID
    @Query("SELECT UserData.name" +
            " FROM UserData JOIN JobSeekerData ON JobSeekerData.fk_userID = UserData.userID WHERE JobSeekerData.jobSeekerID = (:jobSeekerID)")
    String getFullNameFromJobSeekerID(int jobSeekerID);

    // Returns name of user based on job seeker ID
    @Query("SELECT UserData.email" +
            " FROM UserData JOIN JobSeekerData ON JobSeekerData.fk_userID = UserData.userID WHERE JobSeekerData.jobSeekerID = (:jobSeekerID)")
    String getEmailFromJobSeekerID(int jobSeekerID);

    // Adds a user as a job seeker using their user ID and resume path
    @Query("INSERT INTO JobSeekerData (fk_userID,resumeUriString) VALUES (:userID,:resumeUriString)")
    void addJobSeekerData(int userID, String resumeUriString);

    @Insert
    void addJobSeekerData(JobSeekerData jobSeekerData);

    // Removes a job seeker by job seeker ID (NOTE: this does not delete user data, just the job seeker status and resume path of a user)
    @Query("DELETE FROM JobSeekerData WHERE jobSeekerID = (:jobSeekerID)")
    void deleteJobSeekerByJobSeekerID(int jobSeekerID);

    // Removes a job seeker by user ID (NOTE: this does not delete user data, just the job seeker status and resume path of a user)
    @Query("DELETE FROM JobSeekerData WHERE fk_userID = (:jobSeekerID)")
    void deleteJobSeekerByUserID(int jobSeekerID);

    // Updates the resume path of the job seeker given by jobSeekerID
    // the resume's uri string is simply the uri.toString() value
    @Query("UPDATE JobSeekerData SET resumeUriString = :resumeUriString, resumeFileName = :resumeFileName WHERE jobSeekerID = (:jobSeekerID)")
    void updateJobSeekerByID(int jobSeekerID, String resumeUriString, String resumeFileName);

}
