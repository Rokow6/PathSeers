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
    private AppDatabase db;
    private int employerID;

    public JobListing(AppDatabase db, int employerID) {
        benefits = new ArrayList<String>();
        requirements = new ArrayList<String>();
        responsibilities = new ArrayList<String>();
        this.db = db;
        this.employerID = employerID;
    }

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
    }

    public int getJobListingID() {
        return db.jobListingDao().getJobListingIDFromData(employerID, title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
        db.jobListingDao().updateJobListingByID(getJobListingID(), this.title, this.location, this.description, this.pay);
    }

    public ArrayList<String> getBenefits() {
        return benefits;
    }

    public void addBenefit(String benefit) {
        benefits.add(benefit);
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public void addRequirement(String requirement) {
        requirements.add(requirement);
    }

    public ArrayList<String> getResponsibilities() {
        return responsibilities;
    }

    public void addResponsibility(String responsibility) {
        responsibilities.add(responsibility);
    }
}
