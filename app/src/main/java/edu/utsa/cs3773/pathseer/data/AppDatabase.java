package edu.utsa.cs3773.pathseer.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Database class used to access the database in code as an instanced object
@Database(entities = {UserData.class, NotificationData.class, AdminData.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract NotificationDao notificationDao();
    public abstract AdminDao adminDao();
}