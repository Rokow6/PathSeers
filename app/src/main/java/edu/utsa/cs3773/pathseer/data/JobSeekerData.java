package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for job seeker data
@Entity(foreignKeys = @ForeignKey(
        entity = UserData.class,
        parentColumns = "userID",
        childColumns = "fk_userID",
        onDelete = ForeignKey.CASCADE)
)
public class JobSeekerData {
    @PrimaryKey(autoGenerate = true)
    public int jobSeekerID;

    @ColumnInfo(name = "fk_userID")
    public int fk_userID;

    @ColumnInfo(name = "resumeUriString")
    public String resumeUriString;

    @ColumnInfo(name = "resumeFileName")
    public String resumeFileName;

}
