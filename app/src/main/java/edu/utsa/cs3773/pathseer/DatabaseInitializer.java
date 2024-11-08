package edu.utsa.cs3773.pathseer;

import java.security.NoSuchAlgorithmException;

import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class DatabaseInitializer {

    // Initializes database with test data if the database is currently empty
    // Checks each table for data and inserts starting data if it is empty
    // NOTE: if you want to reset the database so this script runs again then:
    //      you can hold click the app in the emulator -> app info -> storage & cache -> clear storage
    public static void initializeDatabase(AppDatabase db) {
        if (db.userDao().getAll().isEmpty()) {
            try {
                initializeUserData(db);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if (db.adminDao().getAll().isEmpty()) {
            initializeAdminData(db);
        }
    }

    // Insert user data into database
    private static void initializeUserData(AppDatabase db) throws NoSuchAlgorithmException {
        String password;

        // Add admin user
        db.userDao().addUserData(0, "Admin", "This is the admin account", "admin@pathseers.com", "admin", "", "");
        password = Encryptor.encryptString("tempadminpassword", db.userDao().getUserIDFromUsername("admin"), db.userDao());
        db.userDao().updatePassword(db.userDao().getUserIDFromUsername("admin"), password);
    }

    private static void initializeAdminData(AppDatabase db) {
        // Add main admin
        db.adminDao().addAdminData(db.userDao().getUserIDFromUsername("admin"));
    }
}
