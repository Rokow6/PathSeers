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

    // Returns a list of all notifications associated with a specific user
    @Query("SELECT * FROM NotificationData WHERE fk_userID IN (:userID)")
    List<NotificationData> getNotificationByUserID(int userID);

    // Adds a new notification to the database (id is auto-incremented)
    @Query("INSERT INTO NotificationData (fk_userID,title,description) VALUES (:userID,:title,:description)")
    void addNotificationData(int userID, String title, String description);

    // Removes a notification from the database based on notification ID (NOTE: this is not the user ID)
    @Query("DELETE FROM NotificationData WHERE notifID = (:id)")
    void deleteNotificationByID(int id);
}
