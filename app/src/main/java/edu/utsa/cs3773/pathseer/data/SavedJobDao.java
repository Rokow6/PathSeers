package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for connecting job seekers with job listing data
@Dao
public interface SavedJobDao {
    // Returns a list of all saved job connection data
    @Query("SELECT * FROM SavedJobData")
    List<SavedJobData> getAll();

    // Returns the data of the given saved job connection ID
    @Query("SELECT * FROM SavedJobData WHERE savedJobID = (:savedJobID)")
    SavedJobData getSavedJobByID(int savedJobID);

    // Returns the saved job connection data of a job seeker based on their ID
    @Query("SELECT * FROM SavedJobData WHERE fk_jobSeekerID = (:jobSeekerID)")
    List<SavedJobData> getSavedJobConnectionFromJobSeekerID(int jobSeekerID);

    // Returns the saved job connection data of a job listing based on its job listing ID
    @Query("SELECT * FROM SavedJobData WHERE fk_jobListingID = (:jobListingID)")
    List<SavedJobData> getSavedJobConnectionFromJobListingID(int jobListingID);

    // Returns the SavedJob ID based on its associated job seeker ID and job listing ID
    // Returns 0 if the SavedJob does not exist
    @Query("SELECT savedJobID FROM SavedJobData WHERE fk_jobListingID = (:jobListingID) AND fk_jobSeekerID = (:jobSeekerID)")
    int getSavedJobConnectionIDFromAssociatedIDs(int jobListingID, int jobSeekerID);

    // Adds a saved job connection; make sure the connection does not already exist
    @Query("INSERT INTO SavedJobData (fk_jobSeekerID,fk_jobListingID) VALUES (:jobSeekerID,:jobListingID)")
    void addSavedJobData(int jobSeekerID, int jobListingID);

    // Removes a saved job connection by its ID
    @Query("DELETE FROM SavedJobData WHERE savedJobID = (:savedJobID)")
    void deleteSavedJobByID(int savedJobID);
}
