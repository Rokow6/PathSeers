package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for connecting job listing and tag data
@Entity(foreignKeys = {
        @ForeignKey(
                entity = JobListingData.class,
                parentColumns = "jobListingID",
                childColumns = "fk_jobListingID",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(
                entity = TagData.class,
                parentColumns = "tagID",
                childColumns = "fk_tagID",
                onDelete = ForeignKey.CASCADE)
})
public class JobHasTagData {
    @PrimaryKey(autoGenerate = true)
    public int jhtID;

    @ColumnInfo(name = "fk_jobListingID")
    public int fk_jobListingID;

    @ColumnInfo(name = "fk_tagID")
    public int fk_tagID;
}
