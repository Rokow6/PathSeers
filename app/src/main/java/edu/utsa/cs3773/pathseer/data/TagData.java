package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database table class for tags
@Entity
public class TagData {
    @PrimaryKey(autoGenerate = true)
    int tagID;

    @ColumnInfo(name = "text")
    String text;
}
