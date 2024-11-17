package edu.utsa.cs3773.pathseer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.JobListingData;

public class JobSearchScreen extends NavigationActivity {
    private RecyclerView recyclerViewJobs;
    private SearchView searchView;
    private SearchManager searchManager;
    private List<JobListingData> jobListingData;
    private String query;
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

    // Called when search is entered into search bar
    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) { // check if intent is for search
            query = intent.getStringExtra(SearchManager.QUERY);
            jobListingData = JobListingSearcher.SearchJobListings(db, query, -1, -1,
                    new ArrayList<>(), new ArrayList<>()); // get search results based on entered query
            updateViews();
        }
    }

    // Initializes the View objects on the screen
    private void initializeViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewJobs.setAdapter(new ItemJobViewAdapter(getApplicationContext(), jobListingData));
        searchView = findViewById(R.id.sv_search_job);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
    }

    // Updates the recycler view to reflect current search results
    private void updateViews() {
        recyclerViewJobs.setAdapter(new ItemJobViewAdapter(getApplicationContext(), jobListingData));
    }
}