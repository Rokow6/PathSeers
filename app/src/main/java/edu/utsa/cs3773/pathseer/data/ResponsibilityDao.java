package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for responsibility data; provides functions to call SQL queries
@Dao
public interface ResponsibilityDao {
    // Returns a list of all responsibility data in the database
    @Query("SELECT * FROM ResponsibilityData")
    List<ResponsibilityData> getAll();

    // Returns the responsibility data of the specified responsibility
    @Query("SELECT * FROM ResponsibilityData WHERE responsibilityID = (:responsibilityID)")
    ResponsibilityData getResponsibilityByID(int responsibilityID);

    // Returns a list of all responsibility data associated with a specific job listing
    @Query("SELECT * FROM ResponsibilityData WHERE fk_jobListingID = (:jobListingID)")
    List<ResponsibilityData> getResponsibilityDataByJobListingID(int jobListingID);

    // Returns a list of all responsibility text associated with a specific job listing
    @Query("SELECT text FROM ResponsibilityData WHERE fk_jobListingID = (:jobListingID)")
    List<String> getResponsibilityTextByJobListingID(int jobListingID);

    // Adds a new responsibility to the database (id is auto-incremented)
    @Query("INSERT INTO ResponsibilityData (fk_jobListingID,text) VALUES (:jobListingID,:text)")
    void addResponsibilityData(int jobListingID, String text);

    // Returns the responsibilityID of a responsibility based on its data; returns 0 if responsibility does not exist
    @Query("SELECT responsibilityID FROM ResponsibilityData WHERE fk_jobListingID = :jobListingID AND text = :text")
    void getResponsibilityIDFromData(int jobListingID, String text);

    // Removes a responsibility from the database based on responsibility ID (NOTE: this is not the job listing ID)
    @Query("DELETE FROM ResponsibilityData WHERE responsibilityID = (:responsibilityID)")
    void deleteResponsibilityByID(int responsibilityID);
}
