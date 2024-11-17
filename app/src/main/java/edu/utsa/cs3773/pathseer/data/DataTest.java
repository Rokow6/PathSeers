package edu.utsa.cs3773.pathseer.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3773.pathseer.JobListingSearcher;

// just a test script for testing the database
public class DataTest {
    public static void TestDatabase(AppDatabase db) {
//        db.userDao().addUserData(21, "John Smith", "John's bio", "jsmith", "password");
//        db.userDao().addUserData(21, "Jane Doe", "Jane's bio", "jdoe", "password");
//        db.notificationDao().addNotificationData(2, "New Offer!", "Offer1 Description");
//        db.notificationDao().addNotificationData(2, "New Offer!", "Offer2 Description");
//        db.notificationDao().addNotificationData(2, "New Offer!", "Offer3 Description");
//        db.notificationDao().addNotificationData(1, "New Offer!", "Offer4 Description");

        // if you ever need to delete the database's data (recreate the database),
        // you can hold click the app in the emulator -> app info -> storage & cache -> clear storage

        List<UserData> userDataList = db.userDao().getAllSecure();

        for (UserData userData : userDataList) {
            Log.d("DEBUGTEST", userData.userID + ", " + userData.age + ", " + userData.name + ", " + userData.bio);
        }

        List<NotificationData> notificationDataList = db.notificationDao().getAll();

        for (NotificationData notificationData : notificationDataList) {
            Log.d("DEBUGTEST", notificationData.notifID + ", " + notificationData.title + ", " + notificationData.description);
        }

        List<JobListingData> jobListingDataList = db.jobListingDao().getAll();
        for (JobListingData jobListingData : jobListingDataList) {
            Log.d("DEBUGTEST", jobListingData.jobListingID + ", " + jobListingData.title + ", " + jobListingData.description + ", " + jobListingData.pay + ", ");
        }

        ArrayList<String> requirements = new ArrayList<String>();
        requirements.add("Requirement1");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("Tag2");
        tags.add("Tag3");
        tags.add("Tag4");
        jobListingDataList = JobListingSearcher.SearchJobListings(db, "employer",
                -1, -1, requirements, tags);

        for (JobListingData jobListingData : jobListingDataList) {
            Log.d("DEBUGTEST", jobListingData.jobListingID + ", " + jobListingData.title + ", " + jobListingData.description + ", " + jobListingData.pay);
        }

        // db.userDao().updateUserData(1, 21, "John Smith", "John's NEW NEW bio", "jsmith", "password");

//        for (UserData userData : userDataList) {
//            Log.d("DEBUGTEST", userData.userID + ", " + userData.age + ", " + userData.name + ", " + userData.bio);
//        }

        int adminID = db.adminDao().getAdminIDFromUserID(1);
        Log.d("DEBUGTEST", "jsmith's admin ID: " + adminID);

        int userID = db.userDao().getUserIDFromUsername("jsmith");
        Log.d("DEBUGTEST", "jsmith's user ID: " + userID);
    }
}
