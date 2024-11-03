package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for responsibilities; foreign key links to a job listing id that it describes
// Responsibilities will automatically be removed if the job listing that they are associated with is deleted
@Entity(foreignKeys = @ForeignKey(
        entity = JobListingData.class,
        parentColumns = "jobListingID",
        childColumns = "fk_jobListingID",
        onDelete = ForeignKey.CASCADE)
)
public class ResponsibilityData {
    @PrimaryKey(autoGenerate = true)
    int responsibilityID;

    @ColumnInfo(name = "fk_jobListingID")
    int fk_jobListingID;

    @ColumnInfo(name = "text")
    String text;
}
