package edu.utsa.cs3773.pathseer.objectClasses;

import java.util.*;

import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class JobListing {
    private String title;
    private String location;
    private String description;
    private double pay;
    private ArrayList<String> benefits;
    private ArrayList<String> requirements;
    private ArrayList<String> responsibilities;
    private ArrayList<String> tags;
    private AppDatabase db;
    private int employerID;

    // Creates a default JobListing
    public JobListing(AppDatabase db, int employerID) {
        benefits = new ArrayList<String>();
        requirements = new ArrayList<String>();
        responsibilities = new ArrayList<String>();
        tags = new ArrayList<String>();
        this.db = db;
        this.employerID = employerID;
    }

    // Creates a JobListing with a title, location, description, and pay
    public JobListing(String title, String location, String description, double pay, AppDatabase db, int employerID) {
        this.title = title;
        this.location =  location;
        this.description = description;
        this.pay = pay;
        this.db = db;
        this.employerID = employerID;
        benefits = new ArrayList<String>();
        requirements = new ArrayList<String>();
        responsibilities = new ArrayList<String>();
        tags = new ArrayList<String>();
    }

    // Returns the ID of the JobListing
    public int getJobListingID() {
        return db.jobListingDao().getJobListingIDFromData(employerID, title);
    }

    // Returns the title of the JobListing
    public String getTitle() {
        return title;
    }

    // Sets the title of the JobListing
    public void setTitle(String title) {
        this.title = title;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    // Returns the location of the JobListing
    public String getLocation() {
        return location;
    }

    // Sets the location of the JobListing
    public void setLocation(String location) {
        this.location = location;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    // Returns the description of the JobListing
    public String getDescription() {
        return description;
    }

    // Sets the description of the JobListing
    public void setDescription(String description) {
        this.description = description;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    // Returns the pay of the JobListing
    public double getPay() {
        return pay;
    }

    // Sets the pay of the JobListing
    public void setPay(double pay) {
        this.pay = pay;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    // Returns the ArrayList of benefits for the JobListing
    public ArrayList<String> getBenefits() {
        return benefits;
    }

    // Adds a benefit to the ArrayList for the JobListing
    public void addBenefit(String benefit) {
        benefits.add(benefit);
    }

    // Returns the ArrayList of requirements for the JobListing
    public ArrayList<String> getRequirements() {
        return requirements;
    }

    // Adds a requirement to the ArrayList for the JobListing
    public void addRequirement(String requirement) {
        requirements.add(requirement);
    }

    // Returns the ArrayList of responsibilities for the JobListing
    public ArrayList<String> getResponsibilities() {
        return responsibilities;
    }

    // Adds a responsibility to the ArrayList for the JobListing
    public void addResponsibility(String responsibility) {
        responsibilities.add(responsibility);
    }

    // Returns the ArrayList of tags for the JobListing
    public ArrayList<String> getTags() {
        return tags;
    }

    // Adds a tag to the ArrayList for the JobListing
    public void addTag(String tag) {
        tags.add(tag);
    }
}
