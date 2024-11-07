package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for connecting job seekers and job listing data
@Entity(foreignKeys = {
        @ForeignKey(
                entity = JobSeekerData.class,
                parentColumns = "jobSeekerID",
                childColumns = "fk_jobSeekerID",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = JobListingData.class,
                parentColumns = "jobListingID",
                childColumns = "fk_jobListingID",
                onDelete = ForeignKey.CASCADE)
})
public class SavedJobData {
    @PrimaryKey(autoGenerate = true)
    public int savedJobID;

    @ColumnInfo(name = "fk_jobSeekerID")
    public int fk_jobSeekerID;

    @ColumnInfo(name = "fk_jobListingID")
    public int fk_jobListingID;
}
