package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for tag data; provides functions to call SQL queries
@Dao
public interface TagDao {
    // Returns a list of all tags data in the database
    @Query("SELECT * FROM TagData")
    List<TagData> getAll();

    // Returns the tag data of the specified tag
    @Query("SELECT * FROM TagData WHERE tagID = (:tagID)")
    TagData getTagByID(int tagID);

    // Returns the list of tags associated with a specific job ID
    // May need testing to see if it works properly
    @Query("SELECT TagData.tagID, TagData.text FROM TagData JOIN JobHasTagData ON JobHasTagData.fk_tagID=TagData.tagID " +
            "WHERE JobHasTagData.fk_jobListingID=(:jobListingID)")
    List<TagData> getTagsByJobListingID(int jobListingID);

    // Returns the list of tag texts associated with a specific job ID
    @Query("SELECT TagData.text FROM TagData JOIN JobHasTagData ON JobHasTagData.fk_tagID=TagData.tagID " +
            "WHERE JobHasTagData.fk_jobListingID=(:jobListingID)")
    List<String> getTagTextsByJobListingID(int jobListingID);

    // Adds a new tag to the database (id is auto-incremented)
    // Make sure to check if tag already exists with getTagIDFromText
    @Query("INSERT INTO TagData (text) VALUES (:text)")
    void addTagData(String text);

    // Returns the tagID of a tag based on its text; returns 0 if tag does not exist
    @Query("SELECT tagID FROM TagData WHERE text = :text")
    int getTagIDFromText(String text);

    // Removes a tag from the database based on tag ID (NOTE: this is not the job listing ID)
    @Query("DELETE FROM TagData WHERE tagID = (:tagID)")
    void deleteTagByID(int tagID);
}
