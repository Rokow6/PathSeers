package edu.utsa.cs3773.pathseer.objectClasses;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.UserDao;

public class JobSeeker extends User {
    private Resume resume;
    private ArrayList<JobListing> applications;
    private AppDatabase db;

    public JobSeeker(AppDatabase db) {
        super(db);
        this.db = db;
        applications = new ArrayList<JobListing>();
    }

    public JobSeeker(int age, String name, String bio, String username, String password, AppDatabase db) throws NoSuchAlgorithmException {
        super(age, name, bio, username, password, db);
        this.db = db;
        applications = new ArrayList<JobListing>();
    }

    public JobSeeker(int age, String name, String bio, String username, String password, AppDatabase db, Resume resume) throws NoSuchAlgorithmException {
        super(age, name, bio, username, password, db);
        this.db = db;
        this.resume = resume;
        applications = new ArrayList<JobListing>();
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public ArrayList<JobListing> getApplications() {
        return applications;
    }

    public void addApplication(JobListing job) {
        applications.add(job);
    }
}
