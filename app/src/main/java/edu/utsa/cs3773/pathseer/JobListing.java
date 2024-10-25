package edu.utsa.cs3773.pathseer;

import java.util.*;

public class JobListing {
    private String title;
    private String location;
    private String description;
    private double pay;
    private ArrayList<String> benefits;
    private ArrayList<String> requirements;
    private ArrayList<String> responsibilities;

    public JobListing() {
        benefits = new ArrayList<String>();
        requirements = new ArrayList<String>();
        responsibilities = new ArrayList<String>();
    }

    public JobListing(String title, String location, String description, double pay) {
        this.title = title;
        this.location =  location;
        this.description = description;
        this.pay = pay;
        benefits = new ArrayList<String>();
        requirements = new ArrayList<String>();
        responsibilities = new ArrayList<String>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
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
