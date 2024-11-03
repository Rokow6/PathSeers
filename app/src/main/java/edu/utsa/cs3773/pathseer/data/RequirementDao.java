package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for requirement data; provides functions to call SQL queries
@Dao
public interface RequirementDao {
    // Returns a list of all requirement data in the database
    @Query("SELECT * FROM RequirementData")
    List<RequirementData> getAll();

    // Returns the requirement data of the specified requirement
    @Query("SELECT * FROM RequirementData WHERE requirementID = (:requirementID)")
    RequirementData getRequirementByID(int requirementID);

    // Returns a list of all requirement data associated with a specific job listing
    @Query("SELECT * FROM RequirementData WHERE fk_jobListingID = (:jobListingID)")
    List<RequirementData> getRequirementDataByJobListingID(int jobListingID);

    // Returns a list of all requirement text associated with a specific job listing
    @Query("SELECT text FROM RequirementData WHERE fk_jobListingID = (:jobListingID)")
    List<String> getRequirementTextByJobListingID(int jobListingID);

    // Adds a new requirement to the database (id is auto-incremented)
    @Query("INSERT INTO RequirementData (fk_jobListingID,text) VALUES (:jobListingID,:text)")
    void addRequirementData(int jobListingID, String text);

    @Query("SELECT requirementID FROM RequirementData WHERE fk_jobListingID = :jobListingID AND text = :text")
    void getRequirementIDFromData(int jobListingID, String text);

    // Removes a requirement from the database based on requirement ID (NOTE: this is not the job listing ID)
    @Query("DELETE FROM RequirementData WHERE requirementID = (:requirementID)")
    void deleteRequirementByID(int requirementID);
}
