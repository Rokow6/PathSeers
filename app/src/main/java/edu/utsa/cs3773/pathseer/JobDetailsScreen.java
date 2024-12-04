package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.JobListingData;


public class JobDetailsScreen extends NavigationActivity {

    private TextView jobTitle, jobLocation, jobPay, jobDescription;
    private LinearLayout requirementsLayout, responsibilitiesLayout, benefitsLayout;
    private AppDatabase db;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_job_details_screen, findViewById(R.id.content_frame));

        db = AppDatabase.getInstance(this);
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        initializeViews();

        // Retrieve the jobListingID from the Intent
        int jobListingID = getIntent().getIntExtra("jobListingID", -1);
        if (jobListingID != -1) {
            loadJobDetails(jobListingID);
            displayRequirements(jobListingID);
            displayResponsibilities(jobListingID);
            displayBenefits(jobListingID);
        }
    }

    // Fetch job details from the database
    private void loadJobDetails(int jobListingID) {
        new Thread(() -> {
            JobListingData jobListing = db.jobListingDao().getJobListingByID(jobListingID);
            runOnUiThread(() -> {
                if (jobListing != null) {
                    jobTitle.setText(jobListing.title);
                    jobLocation.setText(jobListing.location);
                    jobPay.setText(String.format("$%.2f / yr", jobListing.pay));
                    jobDescription.setText(jobListing.description);
                }
            });
        }).start();
    }

    // Adds the list of requirements to linear layout
    private void displayRequirements(int jobListingID) {
        List<String> requirements = db.requirementDao().getRequirementTextByJobListingID(jobListingID);

        // Clear any previous data (in case this is refreshed)
        requirementsLayout.removeAllViews();

        for (String requirement : requirements) {
            TextView textView = new TextView(this);
            textView.setText(requirement);
            textView.setTextSize(16);
            textView.setPadding(0, 8, 0, 8);
            requirementsLayout.addView(textView);
        }
    }

    // Adds the list of responsibilities to linear layout
    private void displayResponsibilities(int jobListingID) {
        List<String> responsibilities = db.responsibilityDao().getResponsibilityTextByJobListingID(jobListingID);

        // Clear any previous data (in case this is refreshed)
        responsibilitiesLayout.removeAllViews();

        for (String responsibility : responsibilities) {
            TextView textView = new TextView(this);
            textView.setText(responsibility);
            textView.setTextSize(16);
            textView.setPadding(0, 8, 0, 8);
            responsibilitiesLayout.addView(textView);
        }
    }

    // Adds the list of benefits to linear layout
    private void displayBenefits(int jobListingID) {
        List<String> benefits = db.benefitDao().getBenefitTextByJobListingID(jobListingID);

        // Clear any previous data (in case this is refreshed)
        benefitsLayout.removeAllViews();

        for (String benefit : benefits) {
            TextView textView = new TextView(this);
            textView.setText(benefit);
            textView.setTextSize(16);
            textView.setPadding(0, 8, 0, 8);
            benefitsLayout.addView(textView);
        }
    }

    private void initializeViews() {
        jobTitle = findViewById(R.id.jobTitle);
        jobLocation = findViewById(R.id.jobLocation);
        jobPay = findViewById(R.id.jobPay);
        jobDescription = findViewById(R.id.jobDescription);
        requirementsLayout = findViewById(R.id.requirementsLayout);
        responsibilitiesLayout = findViewById(R.id.responsibilitiesLayout);
        benefitsLayout = findViewById(R.id.benefitsLayout);
    }
}