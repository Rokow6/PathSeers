package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for connecting job listing with tag data
@Dao
public interface JobHasTagDao {
    // Returns a list of all jht (JobHasTag) connection data
    @Query("SELECT * FROM JobHasTagData")
    List<JobHasTagData> getAll();

    // Returns the data of the given jht connection ID
    @Query("SELECT * FROM JobHasTagData WHERE jhtID = (:jhtID)")
    JobHasTagData getJHTDataByID(int jhtID);

    // Returns the jht connection data of a tag based on its ID
    @Query("SELECT * FROM JobHasTagData WHERE fk_tagID = (:tagID)")
    List<JobHasTagData> getJHTsFromTagID(int tagID);

    // Returns the jht connection data of a job listing based on its job listing ID
    @Query("SELECT * FROM JobHasTagData WHERE fk_jobListingID = (:jobListingID)")
    List<JobHasTagData> getJHTsFromJobListingID(int jobListingID);

    // Returns the jht ID based on its associated tag ID and job listing ID
    // Returns 0 if the jht does not exist
    @Query("SELECT jhtID FROM JobHasTagData WHERE fk_jobListingID = (:jobListingID) AND fk_tagID = (:tagID)")
    int getJobHasTagIDFromAssociatedIDs(int jobListingID, int tagID);

    // Adds a jht connection; make sure the connection does not already exist
    @Query("INSERT INTO JobHasTagData (fk_jobListingID,fk_tagID) VALUES (:jobListingID,:tagID)")
    void addJobHasTagData(int jobListingID, int tagID);

    // Removes a jht connection by its ID
    @Query("DELETE FROM JobHasTagData WHERE jhtID = (:jhtID)")
    void deleteJobHasTagByID(int jhtID);
}
