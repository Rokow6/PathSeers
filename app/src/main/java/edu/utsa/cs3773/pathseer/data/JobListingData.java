package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for job listings; foreign key links to an employer id which the job listing is posted by
// Job Listings will automatically be removed if the employer that they are associated with is deleted
@Entity(foreignKeys = @ForeignKey(
        entity = EmployerData.class,
        parentColumns = "employerID",
        childColumns = "fk_employerID",
        onDelete = ForeignKey.CASCADE)
)
public class JobListingData {
    @PrimaryKey(autoGenerate = true)
    int jobListingID;

    @ColumnInfo(name = "fk_employerID")
    int fk_employerID;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "location")
    String location;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "pay")
    double pay;
}
