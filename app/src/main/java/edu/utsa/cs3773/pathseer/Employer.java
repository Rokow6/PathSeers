package edu.utsa.cs3773.pathseer;

import java.util.*;

public class Employer extends User {
    ArrayList<JobListing> jobPostings;

    public Employer() {
        jobPostings = new ArrayList<JobListing>();
    }

    public Employer(int age, String name, String bio, String username, String password) {
        super(age, name, bio, username, password);
        jobPostings = new ArrayList<JobListing>();
    }

    public void createJobPosting() {
        jobPostings.add(new JobListing());
    }

    public void createJobPosting(String title, String location, String description, double pay) {
        jobPostings.add(new JobListing(title, location, description, pay));
    }
}
