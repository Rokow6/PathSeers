package edu.utsa.cs3773.pathseer;

import java.security.NoSuchAlgorithmException;

import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class DatabaseInitializer {

    // Initializes database with test data if the database is currently empty
    // Checks each table for data and inserts starting data if it is empty
    // NOTE: if you want to reset the database so this script runs again then:
    //      you can hold click the app in the emulator -> app info -> storage & cache -> clear storage
    public static void initializeDatabase(AppDatabase db) {
        if (db.userDao().getAllSecure().isEmpty()) { // user data
            try {
                initializeUserData(db);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if (db.adminDao().getAll().isEmpty()) { // admin data
            initializeAdminData(db);
        }
        if (db.jobSeekerDao().getAll().isEmpty()) { // job seeker data
            initializeJobSeekerData(db);
        }
        if (db.employerDao().getAll().isEmpty()) { // employer data
            initializeEmployerData(db);
        }
        if (db.notificationDao().getAll().isEmpty()) { // notification data
            initializeNotificationData(db);
        }
        if (db.jobListingDao().getAll().isEmpty()) { // job listing data
            initializeJobListingData(db);
        }
        if (db.applicationDao().getAll().isEmpty()) { // application data
            initializeApplicationData(db);
        }
        if (db.savedJobDao().getAll().isEmpty()) { // saved job data
            initializeSavedJobData(db);
        }
        if (db.benefitDao().getAll().isEmpty()) { // benefit data
            initializeBenefitData(db);
        }
        if (db.requirementDao().getAll().isEmpty()) { // requirement data
            initializeRequirementData(db);
        }
        if (db.responsibilityDao().getAll().isEmpty()) { // responsibility data
            initializeResponsibilityData(db);
        }
        if (db.tagDao().getAll().isEmpty()) { // tag data
            initializeTagData(db);
        }
        if (db.jobHasTagDao().getAll().isEmpty()) { // jht data
            initializeJobHasTagData(db);
        }
    }

    // Insert user data into database
    private static void initializeUserData(AppDatabase db) throws NoSuchAlgorithmException {
        String password;

        // Add admin user
        db.userDao().addUserData(0, "Admin", "This is the admin account", "admin@pathseers.com", "admin", "", "");
        password = Encryptor.encryptString("tempadminpassword", db.userDao().getUserIDFromUsername("admin"), db.userDao());
        db.userDao().updatePassword(db.userDao().getUserIDFromUsername("admin"), password);
        // Add test job seeker user
        db.userDao().addUserData(25, "Test JobSeeker", "This is the test job seeker account", "jobseeker@example.com", "jobseeker", "", "");
        password = Encryptor.encryptString("tempjobseekerpassword", db.userDao().getUserIDFromUsername("jobseeker"), db.userDao());
        db.userDao().updatePassword(db.userDao().getUserIDFromUsername("jobseeker"), password);
        // Add test employer
        db.userDao().addUserData(25, "Test Employer", "This is the test employer account", "employer@example.com", "employer", "", "");
        password = Encryptor.encryptString("tempemployerpassword", db.userDao().getUserIDFromUsername("employer"), db.userDao());
        db.userDao().updatePassword(db.userDao().getUserIDFromUsername("employer"), password);
    }

    // Inserts admin data into the database
    private static void initializeAdminData(AppDatabase db) {
        // Add main admin
        db.adminDao().addAdminData(db.userDao().getUserIDFromUsername("admin"));
    }

    // Inserts job seeker data into the database
    private static void initializeJobSeekerData(AppDatabase db) {
        // Add test job seeker
        db.jobSeekerDao().addJobSeekerData(db.userDao().getUserIDFromUsername("jobseeker"), ""); // no test resume data yet :(
    }

    // Inserts employer data into the database
    private static void initializeEmployerData(AppDatabase db) {
        // Add test employer
        db.employerDao().addEmployerData(db.userDao().getUserIDFromUsername("employer"));
    }

    // Inserts notification data
    private static void initializeNotificationData(AppDatabase db) {
        // Add test notification and send it to the test job seeker
        db.notificationDao().addNotificationData(db.userDao().getUserIDFromUsername("jobseeker"), "Test Notification!", "This is the test notification!");
    }

    // Inserts job listing data
    private static void initializeJobListingData(AppDatabase db) {
        // Add test job listing 1
        db.jobListingDao().addJobListingData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")),
                "Test Job Listing 1", "Test Location", "This is test job listing 1", 999.99);
        // Add test job listing 2
        db.jobListingDao().addJobListingData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")),
                "Test Job Listing 2", "Test Locatoin", "This is test job listing 2", 4.99);
    }

    // Inserts application data
    private static void initializeApplicationData(AppDatabase db) {
        // Add test application data as application for test job listing 1 by test job seeker
        db.applicationDao().addApplicationData(db.jobSeekerDao().getJobSeekerIDFromUserID(db.userDao().getUserIDFromUsername("jobseeker")),
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 1"),
                "Hello! This is Test JobSeeker and I am applying for Test Job Listing 1");
    }

    // Inserts saved job data
    private static void initializeSavedJobData(AppDatabase db) {
        // Add saved job data as job listing 2 for test job seeker
        db.savedJobDao().addSavedJobData(db.jobSeekerDao().getJobSeekerIDFromUserID(db.userDao().getUserIDFromUsername("jobseeker")),
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 2"));
    }

    // Inserts benefit data
    private static void initializeBenefitData(AppDatabase db) {
        // Add test benefit to job listing 1
        db.benefitDao().addBenefitData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 1"),
                "Benefit1");
        // Add test benefit to job listing 2
        db.benefitDao().addBenefitData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 2"),
                "Benefit2");
    }

    // Inserts requirement data
    private static void initializeRequirementData(AppDatabase db) {
        // Add test requirement to job listing 1
        db.requirementDao().addRequirementData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 1"),
                "Requirement1");
        // Add test requirement to job listing 2
        db.requirementDao().addRequirementData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 2"),
                "Requirement2");
    }

    // Inserts responsibility data
    private static void initializeResponsibilityData(AppDatabase db) {
        // Add test responsibility to job listing 1
        db.responsibilityDao().addResponsibilityData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 1"),
                "Responsibility1");
        // Add test responsibility to job listing 2
        db.responsibilityDao().addResponsibilityData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 2"),
                "Responsibility2");
    }

    // Inserts tag data
    private static void initializeTagData(AppDatabase db) {
        // Add test tag 1
        db.tagDao().addTagData("Tag1");
        // Add test tag 2
        db.tagDao().addTagData("Tag2");
    }

    // Inserts job-tag connection data
    private  static void initializeJobHasTagData(AppDatabase db) {
        // Add Tag1 to job listing 1
        db.jobHasTagDao().addJobHasTagData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 1"),
                db.tagDao().getTagIDFromText("Tag1"));
        // Add Tag2 to job listing 2
        db.jobHasTagDao().addJobHasTagData(
                db.jobListingDao().getJobListingIDFromData(db.employerDao().getEmployerIDFromUserID(db.userDao().getUserIDFromUsername("employer")), "Test Job Listing 2"),
                db.tagDao().getTagIDFromText("Tag2"));
    }
}
