package edu.utsa.cs3773.pathseer;

import java.util.*;

public class JobSeeker extends User {
    private Resume resume;
    private ArrayList<JobListing> applications;

    public JobSeeker() {
        applications = new ArrayList<JobListing>();
    }

    public JobSeeker(int age, String name, String bio, String username, String password) {
        super(age, name, bio, username, password);
        applications = new ArrayList<JobListing>();
    }

    public JobSeeker(int age, String name, String bio, String username, String password, Resume resume) {
        super(age, name, bio, username, password);
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
