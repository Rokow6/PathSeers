package edu.utsa.cs3773.pathseer.data;

import android.util.Log;

import java.util.List;

// just a test script for testing the database
public class DataTest {
    public static void TestDatabase(AppDatabase db) {
//        db.userDao().addUserData(21, "John Smith", "John's bio", "jsmith", "password");
//        db.userDao().addUserData(21, "Jane Doe", "Jane's bio", "jdoe", "password");
//        db.notificationDao().addNotificationData(2, "New Offer!", "Offer1 Description");
//        db.notificationDao().addNotificationData(2, "New Offer!", "Offer2 Description");
//        db.notificationDao().addNotificationData(2, "New Offer!", "Offer3 Description");
//        db.notificationDao().addNotificationData(3, "New Offer!", "Offer4 Description");

        // if you ever need to delete the database's data (recreate the database),
        // you can hold click the app in the emulator -> app info -> storage & cache -> clear storage

        List<UserData> userDataList = db.userDao().getAll();

        for (UserData userData : userDataList) {
            Log.d("DEBUGTEST", userData.userID + ", " + userData.age + ", " + userData.name);
        }

        List<NotificationData> notificationDataList = db.notificationDao().getAll();

        for (NotificationData notificationData : notificationDataList) {
            Log.d("DEBUGTEST", notificationData.notifID + ", " + notificationData.title + ", " + notificationData.description);
        }
    }
}
