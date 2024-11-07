package edu.utsa.cs3773.pathseer.objectClasses;

import java.util.*;

import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class Admin extends User { // needs implementation for ID and admin powers
    private int adminID;
    private ArrayList<String> activityLog;
    private AppDatabase db;

    public Admin(AppDatabase db) {
        super(db);
        this.db = db;
        activityLog = new ArrayList<String>();
    }

    public ArrayList<String> viewActivityLog() {
        return activityLog;
    }

    public void deleteUser(User user) {

    }

    public void deleteJobListing(JobListing job) {

    }
}
