package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for application data
@Entity(foreignKeys = {
        @ForeignKey(
                entity = JobListingData.class,
                parentColumns = "jobListingID",
                childColumns = "fk_jobListingID",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = JobSeekerData.class,
                parentColumns = "jobSeekerID",
                childColumns = "fk_jobSeekerID",
                onDelete = ForeignKey.CASCADE)
})
public class ApplicationData {
    @PrimaryKey(autoGenerate = true)
    public int applicationID;

    @ColumnInfo(name = "fk_jobListingID")
    public int fk_jobListingID;

    @ColumnInfo(name = "fk_jobSeekerID")
    public int fk_jobSeekerID;

    @ColumnInfo(name = "description")
    public String description;

}
