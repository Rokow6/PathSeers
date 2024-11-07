package edu.utsa.cs3773.pathseer.objectClasses;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class Employer extends User {
    ArrayList<JobListing> jobPostings;
    AppDatabase db;

    // Creates default Employer
    public Employer(AppDatabase db) {
        super(db);
        jobPostings = new ArrayList<JobListing>();
        this.db = db;

        db.employerDao().addEmployerData(getID());
    }

    // Creates an Employer with an age, name, bio, email, username, and password
    public Employer(int age, String name, String bio, String email, String username, String password, AppDatabase db) throws NoSuchAlgorithmException {
        super(age, name, bio, email, username, password, db);
        jobPostings = new ArrayList<JobListing>();
        this.db = db;

        db.employerDao().addEmployerData(getID());
    }

    // Returns the ID of the Employer
    public int getEmployerID() {
        return db.employerDao().getEmployerIDFromUserID(getID());
    }

    // Creates a default JobPosting and adds it to the Employer's jobPosting ArrayList
    public void createJobPosting() {
        jobPostings.add(new JobListing(db, getEmployerID()));
    }

    // Creates a JobPosting with a title, location, description, and pay and adds it to the Employer's jobPosting ArrayList
    public void createJobPosting(String title, String location, String description, double pay) {
        jobPostings.add(new JobListing(title, location, description, pay, db, getEmployerID()));
    }
}
