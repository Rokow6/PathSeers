package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for application data
@Dao
public interface ApplicationDao {
    // Returns a list of all application data
    @Query("SELECT * FROM ApplicationData")
    List<ApplicationData> getAll();

    // Returns the data of the given application ID
    @Query("SELECT * FROM ApplicationData WHERE applicationID = (:applicationID)")
    ApplicationData getApplicationDataByID(int applicationID);

    // Returns the application data of a user based on their job seeker ID
    @Query("SELECT * FROM ApplicationData WHERE fk_jobSeekerID = (:jobSeekerID)")
    List<ApplicationData> getApplicationsFromJobSeekerID(int jobSeekerID);

    // Returns the application data of a job listing based on its job listing ID
    @Query("SELECT * FROM ApplicationData WHERE fk_jobListingID = (:jobListingID)")
    List<ApplicationData> getApplicationsFromJobListingID(int jobListingID);

    // Returns the application ID based on its associated job seeker ID and job listing ID
    // Returns 0 if the application does not exist
    @Query("SELECT applicationID FROM ApplicationData WHERE fk_jobSeekerID = (:jobSeekerID) AND fk_jobListingID = (:jobListingID)")
    int getApplicationIDFromAssociatedIDs(int jobSeekerID, int jobListingID);

    // Adds an application; make sure the user has not already applied for the job listing
    @Query("INSERT INTO ApplicationData (fk_jobSeekerID,fk_jobListingID,description) VALUES (:jobSeekerID,:jobListingID,:description)")
    void addApplicationData(int jobSeekerID, int jobListingID, String description);

    // Removes an application by its ID
    @Query("DELETE FROM ApplicationData WHERE applicationID = (:applicationID)")
    void deleteApplicationByID(int applicationID);
}
