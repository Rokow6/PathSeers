package edu.utsa.cs3773.pathseer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Database table class for notifications; foreign key links to a user id which the notification is addressed to
// Notifications will automatically be removed if the user that they are associated with is deleted
@Entity(foreignKeys = @ForeignKey(
        entity = UserData.class,
        parentColumns = "userID",
        childColumns = "fk_userID",
        onDelete = ForeignKey.CASCADE)
)
public class NotificationData {
    @PrimaryKey(autoGenerate = true)
    int notifID;

    @ColumnInfo(name = "fk_userID")
    int fk_userID;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "description")
    String description;
}
