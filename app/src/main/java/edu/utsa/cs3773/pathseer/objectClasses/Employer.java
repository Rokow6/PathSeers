package edu.utsa.cs3773.pathseer.objectClasses;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.UserDao;

public class Employer extends User {
    ArrayList<JobListing> jobPostings;
    AppDatabase db;

    public Employer(AppDatabase db) {
        super(db);
        jobPostings = new ArrayList<JobListing>();
        this.db = db;

        db.employerDao().addEmployerData(getID());
    }

    public Employer(int age, String name, String bio, String username, String password, AppDatabase db) throws NoSuchAlgorithmException {
        super(age, name, bio, username, password, db);
        jobPostings = new ArrayList<JobListing>();
        this.db = db;

        db.employerDao().addEmployerData(getID());
    }

    public int getEmployerID() {
        return db.employerDao().getEmployerIDFromUserID(getID());
    }

    public void createJobPosting() {
        jobPostings.add(new JobListing(db, getEmployerID()));

        db.jobListingDao().addJobListingData(db.userDao().getUserIDFromUsername(super.getUsername()), "", "", "", 0.0);
    }

    public void createJobPosting(String title, String location, String description, double pay) {
        jobPostings.add(new JobListing(title, location, description, pay, db, getEmployerID()));

        db.jobListingDao().addJobListingData(db.userDao().getUserIDFromUsername(super.getUsername()), title, location, description, pay);
    }
}
