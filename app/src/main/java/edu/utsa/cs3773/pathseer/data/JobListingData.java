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
    public int jobListingID;

    @ColumnInfo(name = "fk_employerID")
    int fk_employerID;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "pay")
    public double pay;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        JobListingData jldObj = (JobListingData) obj;
        return (jldObj.jobListingID == this.jobListingID);
    }

    @Override
    public int hashCode() {
        return this.jobListingID;
    }
}
