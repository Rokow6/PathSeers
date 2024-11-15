package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.JobListingData;

public class JobSearchScreen extends NavigationActivity {
    private RecyclerView recyclerViewJobs;
    private List<JobListingData> jobListingData;
    private AppDatabase db;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_job_search_screen, findViewById(R.id.content_frame));

        db = AppDatabase.getInstance(this);
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        jobListingData = db.jobListingDao().getAll(); // Pulls all the JobListingData

        initializeViews();

    }

    // Initializes the View objects on the screen
    private void initializeViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewJobs.setAdapter(new ItemJobViewAdapter(getApplicationContext(), jobListingData));
    }
}