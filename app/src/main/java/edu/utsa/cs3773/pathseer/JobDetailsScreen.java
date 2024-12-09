package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.ApplicationData;
import edu.utsa.cs3773.pathseer.data.JobListingData;


public class JobDetailsScreen extends NavigationActivity {

    private TextView jobTitle, employerName, jobLocation, jobPay, jobDescription;
    private LinearLayout requirementsLayout, responsibilitiesLayout, benefitsLayout, tagsLayout;
    private Button applyButton;
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
            displayTags(jobListingID);

            applyButton.setOnClickListener(v -> {
                    addApplicationForCurrentUser(jobListingID);
            });
        }

        // Debugging applications for the current user
        debugApplicationsForCurrentUser();
    }

    // Fetch job details from the database
    private void loadJobDetails(int jobListingID) {
        new Thread(() -> {
            JobListingData jobListing = db.jobListingDao().getJobListingByID(jobListingID);
            runOnUiThread(() -> {
                if (jobListing != null) {
                    jobTitle.setText(jobListing.title);
                    employerName.setText(db.jobListingDao().getEmployerNameFromJobListingID(jobListingID));
                    jobLocation.setText(jobListing.location);
                    jobPay.setText(String.format("$%.2f / yr", jobListing.pay));
                    jobDescription.setText(jobListing.description);
                }
            });
        }).start();
    }

    private void addApplicationForCurrentUser(int jobListingID) {
        int currentJobSeekerID = db.jobSeekerDao().getJobSeekerIDFromUserID(sharedPref.getInt("user_id", -1)); // Retrieve job seeker ID
        if (currentJobSeekerID <= 0) {
            // Handle the case where no user is logged in
            runOnUiThread(() -> Toast.makeText(this, "You are not a job seeker or user is not logged in.", Toast.LENGTH_SHORT).show());
            return;
        }

        new Thread(() -> {
            // Check if the application already exists
            int existingApplicationID = db.applicationDao().getApplicationIDFromAssociatedIDs(currentJobSeekerID, jobListingID);
            if (existingApplicationID != 0) {
                runOnUiThread(() -> Toast.makeText(this, "You have already applied for this job.", Toast.LENGTH_SHORT).show());
                return;
            }

            // Add the application
            db.applicationDao().addApplicationData(currentJobSeekerID, jobListingID, "Application submitted.");
            runOnUiThread(() -> Toast.makeText(this, "Application submitted successfully!", Toast.LENGTH_SHORT).show());
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

    // Adds the list of tags to linear layout
    private void displayTags(int jobListingID) {
        List<String> tags = db.tagDao().getTagTextsByJobListingID(jobListingID);

        // Clear any previous data (in case this is refreshed)
        tagsLayout.removeAllViews();

        for (String tag : tags) {
            TextView textView = new TextView(this);
            textView.setText(tag);
            textView.setTextSize(16);
            textView.setPadding(0, 8, 0, 8);
            tagsLayout.addView(textView);
        }
    }

    private void initializeViews() {
        jobTitle = findViewById(R.id.jobTitle);
        employerName = findViewById(R.id.employerName);
        jobLocation = findViewById(R.id.jobLocation);
        jobPay = findViewById(R.id.jobPay);
        jobDescription = findViewById(R.id.jobDescription);
        requirementsLayout = findViewById(R.id.requirementsLayout);
        responsibilitiesLayout = findViewById(R.id.responsibilitiesLayout);
        benefitsLayout = findViewById(R.id.benefitsLayout);
        tagsLayout = findViewById(R.id.tagsLayout);
        applyButton = findViewById(R.id.applyButton);
    }

    private void debugApplicationsForCurrentUser() {
        int currentJobSeekerID = sharedPref.getInt("jobSeekerID", -1);
        if (currentJobSeekerID == -1) return;

        new Thread(() -> {
            List<ApplicationData> applications = db.applicationDao().getApplicationsFromJobSeekerID(currentJobSeekerID);
            for (ApplicationData app : applications) {
                Log.d("Application", "Job ID: " + app.fk_jobListingID + ", Description: " + app.description);
            }
        }).start();
    }

}