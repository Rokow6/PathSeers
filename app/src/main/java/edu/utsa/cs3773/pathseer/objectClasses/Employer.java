package edu.utsa.cs3773.pathseer.objectClasses;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import edu.utsa.cs3773.pathseer.data.UserDao;

public class Employer extends User {
    ArrayList<JobListing> jobPostings;

    public Employer() {
        jobPostings = new ArrayList<JobListing>();
    }

    public Employer(int age, String name, String bio, String username, String password, UserDao userDao) throws NoSuchAlgorithmException {
        super(age, name, bio, username, password, userDao);
        jobPostings = new ArrayList<JobListing>();
    }

    public void createJobPosting() {
        jobPostings.add(new JobListing());
    }

    public void createJobPosting(String title, String location, String description, double pay) {
        jobPostings.add(new JobListing(title, location, description, pay));
    }
}
