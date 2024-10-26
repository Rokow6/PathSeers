package edu.utsa.cs3773.pathseer.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Data access object for notification data; provides functions to call SQL queries
@Dao
public interface NotificationDao {
    // Returns a list of all notification data in the database
    @Query("SELECT * FROM NotificationData")
    List<NotificationData> getAll();

    // Returns the notification data of the specified notification
    @Query("SELECT * FROM NotificationData WHERE notifID = (:notifID)")
    NotificationData getNotificationByID(int notifID);

    // Returns a list of all notification data associated with a specific user
    @Query("SELECT * FROM NotificationData WHERE fk_userID = (:userID)")
    List<NotificationData> getNotificationsByUserID(int userID);

    // Adds a new notification to the database (id is auto-incremented)
    @Query("INSERT INTO NotificationData (fk_userID,title,description) VALUES (:userID,:title,:description)")
    void addNotificationData(int userID, String title, String description);

    // Removes a notification from the database based on notification ID (NOTE: this is not the user ID)
    @Query("DELETE FROM NotificationData WHERE notifID = (:notifID)")
    void deleteNotificationByID(int notifID);

    // Updates the data of the notification given by notifID
    // (may not be very useful, probably better to delete and create a new notification)
    @Query("UPDATE NotificationData SET fk_userID = :fk_userID, title = :title, description = :description WHERE notifID = (:notifID)")
    void updateNotificationByID(int notifID, int fk_userID, String title, String description);
}
