package edu.utsa.cs3773.pathseer.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Database class used to access the database in code as an instanced object
@Database(entities = {UserData.class, NotificationData.class, AdminData.class, JobSeekerData.class, EmployerData.class,
                        JobListingData.class, ApplicationData.class, BenefitData.class, RequirementData.class,
                        ResponsibilityData.class, TagData.class, JobHasTagData.class, SavedJobData.class}, version = 15)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract NotificationDao notificationDao();
    public abstract AdminDao adminDao();
    public abstract JobSeekerDao jobSeekerDao();
    public abstract EmployerDao employerDao();
    public abstract JobListingDao jobListingDao();
    public abstract ApplicationDao applicationDao();
    public abstract BenefitDao benefitDao();
    public abstract RequirementDao requirementDao();
    public abstract ResponsibilityDao responsibilityDao();
    public abstract TagDao tagDao();
    public abstract JobHasTagDao jobHasTagDao();
    public abstract SavedJobDao savedJobDao();

    //Singleton instance
    private static volatile AppDatabase instance;

    //Method to get the singleton instance of the database
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "pathseers-database")
                    .fallbackToDestructiveMigration() // Clears data on schema changes (for development)
                    .build();
        }
        return instance;
    }

    //For testing purposes, provides an in-memory version of the database
    public static AppDatabase getInMemoryInstance(Context context) {
        return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries() // For testing purposes only
                .build();
    }

    //Close the database to free resources
    public static synchronized void closeDatabase() {
        if (instance != null && instance.isOpen()) {
            instance.close();
            instance = null;
        }
    }
}
