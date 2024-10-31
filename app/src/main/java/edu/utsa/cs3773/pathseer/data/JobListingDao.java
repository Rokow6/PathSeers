package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for job listing data; provides functions to call SQL queries
@Dao
public interface JobListingDao {
    // Returns a list of all job listing data in the database
    @Query("SELECT * FROM JobListingData")
    List<JobListingData> getAll();

    // Returns the job listing data of the specified job listing
    @Query("SELECT * FROM JobListingData WHERE jobListingID = (:jobListingID)")
    JobListingData getJobListingByID(int jobListingID);

    // Returns a list of all job listing data associated with a specific employer
    @Query("SELECT * FROM JobListingData WHERE fk_employerID = (:employerID)")
    List<JobListingData> getJobListingsByEmployerID(int employerID);

    // Adds a new job listing to the database (id is auto-incremented)
    @Query("INSERT INTO JobListingData (fk_employerID,title,location,description,pay) VALUES (:employerID,:title,:location,:description,:pay)")
    void addJobListingData(int employerID, String title, String location, String description, double pay);

    // Removes a job listing from the database based on job listing ID (NOTE: this is not the employer ID)
    @Query("DELETE FROM JobListingData WHERE jobListingID = (:jobListingID)")
    void deleteJobListingByID(int jobListingID);

    // Updates the data of the job listing given by jobListingID
    @Query("UPDATE JobListingData " +
            "SET title = :title, location = :location, description = :description, pay = :pay " +
            "WHERE jobListingID = (:jobListingID)")
    void updateJobListingByID(int jobListingID, String title, String location, String description, double pay);
}
