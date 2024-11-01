package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for benefit data; provides functions to call SQL queries
@Dao
public interface BenefitDao {
    // Returns a list of all benefits data in the database
    @Query("SELECT * FROM BenefitData")
    List<BenefitData> getAll();

    // Returns the benefit data of the specified benefit
    @Query("SELECT * FROM BenefitData WHERE benefitID = (:benefitID)")
    BenefitData getBenefitByID(int benefitID);

    // Returns a list of all benefit data associated with a specific job listing
    @Query("SELECT * FROM BenefitData WHERE fk_jobListingID = (:jobListingID)")
    List<BenefitData> getBenefitDataByUserID(int jobListingID);

    // Returns a list of all benefit text associated with a specific job listing
    @Query("SELECT text FROM BenefitData WHERE fk_jobListingID = (:jobListingID)")
    List<String> getBenefitTextByUserID(int jobListingID);

    // Adds a new benefit to the database (id is auto-incremented)
    @Query("INSERT INTO BenefitData (fk_jobListingID,text) VALUES (:jobListingID,:text)")
    void addBenefitData(int jobListingID, String text);

    @Query("SELECT benefitID FROM BenefitData WHERE fk_jobListingID = :jobListingID AND text = :text")
    void getBenefitIDFromData(int jobListingID, String text);

    // Removes a benefit from the database based on benefit ID (NOTE: this is not the job listing ID)
    @Query("DELETE FROM BenefitData WHERE benefitID = (:benefitID)")
    void deleteBenefitByID(int benefitID);
}
