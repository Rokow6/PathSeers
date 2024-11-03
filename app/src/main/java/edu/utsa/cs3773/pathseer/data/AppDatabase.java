package edu.utsa.cs3773.pathseer.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Database class used to access the database in code as an instanced object
@Database(entities = {UserData.class, NotificationData.class, AdminData.class, JobSeekerData.class, EmployerData.class,
                        JobListingData.class, ApplicationData.class, BenefitData.class, RequirementData.class,
                        ResponsibilityData.class, TagData.class}, version = 11)
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
}
