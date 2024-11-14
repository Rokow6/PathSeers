package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.objectClasses.Employer;
import edu.utsa.cs3773.pathseer.objectClasses.JobListing;

public class PostJobScreen extends NavigationActivity {

    private EditText titleInput, locationInput, descriptionInput, payInput, benefitInput, requirementInput, responsibilityInput, tagInput;
    private Button addBenefit, addRequirement, addResponsibility, addTag, publishJobListing;
    private LinearLayout postJobListingLayout, benefitLayout, removeBenefitLayout, requirementLayout, removeRequirementLayout,
            responsibilityLayout, removeResponsibilityLayout, tagLayout, removeTagLayout;
    private ArrayList<TextView> benefitsTextView = new ArrayList<TextView>();
    private ArrayList<TextView> requirementsTextView = new ArrayList<TextView>();
    private ArrayList<TextView> responsibilitiesTextView = new ArrayList<TextView>();
    private ArrayList<TextView> tagsTextView = new ArrayList<TextView>();
    private AppDatabase db;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_post_job_screen, findViewById(R.id.content_frame));

        db = MainActivity.db;
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        initializeViews();

        addBenefit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(benefitInput.getText().toString());

                benefitsTextView.add(textOut);

                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        benefitsTextView.remove(textOut);
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                removeBenefitLayout.addView(addView);
            }});

        addRequirement.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(requirementInput.getText().toString());

                requirementsTextView.add(textOut);

                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        requirementsTextView.remove(textOut);
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                removeRequirementLayout.addView(addView);
            }});

        addResponsibility.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(responsibilityInput.getText().toString());

                responsibilitiesTextView.add(textOut);

                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        responsibilitiesTextView.remove(textOut);
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                removeResponsibilityLayout.addView(addView);
            }});

        addTag.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(tagInput.getText().toString());

                tagsTextView.add(textOut);

                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        tagsTextView.remove(textOut);
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                removeTagLayout.addView(addView);
            }});

        publishJobListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishJobListing();
            }
        });
    }

    private void initializeViews() {
        postJobListingLayout = findViewById(R.id.postJobLisingLayout);
        titleInput = findViewById(R.id.titleInput);
        locationInput = findViewById(R.id.locationInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        payInput = findViewById(R.id.payInput);
        benefitInput = (EditText)findViewById(R.id.benefitInput);
        addBenefit = (Button)findViewById(R.id.addBenefit);
        benefitLayout = (LinearLayout)findViewById(R.id.benefitLayout);
        removeBenefitLayout = (LinearLayout)findViewById(R.id.removeBenefitLayout);
        requirementInput = (EditText)findViewById(R.id.requirementInput);
        addRequirement = (Button)findViewById(R.id.addRequirement);
        requirementLayout = (LinearLayout)findViewById(R.id.requirementLayout);
        removeRequirementLayout = (LinearLayout)findViewById(R.id.removeRequirementLayout);
        responsibilityInput = (EditText)findViewById(R.id.responsibilityInput);
        addResponsibility = (Button)findViewById(R.id.addResponsibility);
        responsibilityLayout = (LinearLayout)findViewById(R.id.responsibilityLayout);
        removeResponsibilityLayout = (LinearLayout)findViewById(R.id.removeResponsibilityLayout);
        tagInput = (EditText)findViewById(R.id.tagInput);
        addTag = (Button)findViewById(R.id.addTag);
        tagLayout = (LinearLayout)findViewById(R.id.tagLayout);
        removeTagLayout = (LinearLayout)findViewById(R.id.removeTagLayout);
        publishJobListing = findViewById(R.id.addJobListing);
    }

    // Creates a JobListing with input from screen
    private void publishJobListing() {
        String title = titleInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        double pay = -1.0;

        // Checks if input for payInput is a double
        String payString = payInput.getText().toString().trim();
        if(!payString.isEmpty())
            try
            {
                pay = Double.parseDouble(payString);
                // it means it is double
            } catch (Exception e1) {
                // this means it is not double
                Toast.makeText(this, "Pay entered is not valid (enter a number)", Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
                return;
            }

        if (title.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill out title, location, and description", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creates JobListing
        JobListing jobListing = new JobListing(title, location, description, pay, db, db.employerDao().getEmployerIDFromUserID(sharedPref.getInt("user_id", -1)));
        addArrayLists(jobListing);

        // Successful creation of JobListing; sends back to home screen
        Toast.makeText(this, "Created Job Listing", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PostJobScreen.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    // Adds ArrayList data to JobListing
    private void addArrayLists(JobListing jobListing) {
        for (int i = 0; i < benefitsTextView.size(); i++) {
            jobListing.addBenefit(benefitsTextView.get(i).getText().toString().trim());
        }
        for (int i = 0; i < requirementsTextView.size(); i++) {
            jobListing.addRequirement(requirementsTextView.get(i).getText().toString().trim());
        }
        for (int i = 0; i < responsibilitiesTextView.size(); i++) {
            jobListing.addResponsibility(responsibilitiesTextView.get(i).getText().toString().trim());
        }
        for (int i = 0; i < tagsTextView.size(); i++) {
            jobListing.addTag(tagsTextView.get(i).getText().toString().trim());
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
