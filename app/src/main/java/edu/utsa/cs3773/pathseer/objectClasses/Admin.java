package edu.utsa.cs3773.pathseer.objectClasses;

import java.util.*;

public class Admin extends User { // needs implementation for ID and admin powers
    private int adminID;
    private ArrayList<String> activityLog;

    public Admin() {
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
