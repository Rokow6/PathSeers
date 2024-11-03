package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for requirements; foreign key links to a job listing id that it describes
// Requirements will automatically be removed if the job listing that they are associated with is deleted
@Entity(foreignKeys = @ForeignKey(
        entity = JobListingData.class,
        parentColumns = "jobListingID",
        childColumns = "fk_jobListingID",
        onDelete = ForeignKey.CASCADE)
)
public class RequirementData {
    @PrimaryKey(autoGenerate = true)
    int requirementID;

    @ColumnInfo(name = "fk_jobListingID")
    int fk_jobListingID;

    @ColumnInfo(name = "text")
    String text;
}
