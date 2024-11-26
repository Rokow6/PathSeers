package edu.utsa.cs3773.pathseer.objectClasses;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.UserDao;

public class JobSeeker extends User {
    private UploadedFile resume;
    private ArrayList<JobListing> applications;
    private ArrayList<JobListing> savedJobs;
    private AppDatabase db;

    // Creates default JobSeeker
    public JobSeeker(AppDatabase db) {
        super(db);
        this.db = db;
        applications = new ArrayList<JobListing>();
        savedJobs = new ArrayList<JobListing>();

        db.jobSeekerDao().addJobSeekerData(getJobSeekerID(), "");
    }

    // Creates a JobSeeker with an age, name, bio, email, username, and password
    public JobSeeker(int age, String name, String bio, String email, String username, String password, AppDatabase db) throws NoSuchAlgorithmException {
        super(age, name, bio, email, username, password, db);
        this.db = db;
        applications = new ArrayList<JobListing>();
        savedJobs = new ArrayList<JobListing>();

        db.jobSeekerDao().addJobSeekerData(getJobSeekerID(), "");
    }

    // Creates a JobSeeker with an age, name, bio, username, password, and resume
    public JobSeeker(int age, String name, String bio, String email, String username, String password, AppDatabase db, UploadedFile resume) throws NoSuchAlgorithmException {
        super(age, name, bio, email, username, password, db);
        this.db = db;
        this.resume = resume;
        applications = new ArrayList<JobListing>();
        savedJobs = new ArrayList<JobListing>();

        db.jobSeekerDao().addJobSeekerData(getJobSeekerID(), "");
    }

    // Returns the ID of the JobSeeker
    public int getJobSeekerID() {
        return db.jobSeekerDao().getJobSeekerIDFromUserID(db.userDao().getUserIDFromUsername(getUsername()));
    }

    // ***Leaving these here just in case idk***
//    // Returns the Resume of the JobSeeker
//    public UploadedFile getResume() {
//        return resume;
//    }
//
//    // Sets the Resume of the JobSeeker
//    public void setResume(UploadedFile resume) {
//        this.resume = resume;
//    }

    // Returns the ArrayList of JobListings that the JobSeeker applied for
    public ArrayList<JobListing> getApplications() {
        return applications;
    }

    // Adds a JobListing to the ArrayList for when a JobSeeker applies for a listing
    public void addApplication(JobListing job) {
        applications.add(job);
    }

    // Returns the ArrayList of JobListings that the JobSeeker has saved
    public ArrayList<JobListing> getSavedJobs() {
        return savedJobs;
    }

    // Adds a JobListing to the ArrayList for when a JobSeeker applies for a listing
    public void addSavedJob(JobListing job) {
        savedJobs.add(job);
    }
}
