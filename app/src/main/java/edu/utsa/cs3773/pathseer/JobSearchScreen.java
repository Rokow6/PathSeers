package edu.utsa.cs3773.pathseer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.JobListingData;

public class JobSearchScreen extends NavigationActivity implements PayFilterDialogFragment.PayFilterDialogListener {
    private RecyclerView recyclerViewJobs;
    private SearchView searchView;
    private SearchManager searchManager;
    private FragmentManager fragmentManager;
    private List<JobListingData> jobListingData;
    private String query;
    private double payUpperBound = -1;
    private double payLowerBound = -1;
    private AppDatabase db;
    private Button payFilterButton;
    private ImageButton searchButton;
    private PayFilterDialogFragment payFilterDialogFragment;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_job_search_screen, findViewById(R.id.content_frame));

        db = AppDatabase.getInstance(this);
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        jobListingData = db.jobListingDao().getAll(); // Pulls all the JobListingData

        initializeViews();
        initializeButtonListeners();
    }

    // Called when search is entered into search bar
    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) { // check if intent is for search
            query = intent.getStringExtra(SearchManager.QUERY);
            initiateSearch(query);
        }
    }

    private void initiateSearch(String query) {
        jobListingData = JobListingSearcher.SearchJobListings(db, query, payUpperBound, payLowerBound,
                new ArrayList<>(), new ArrayList<>()); // get search results based on entered query
        updateViews();
    }

    // Called when the user clicks Set in the dialog
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // update the current filters with the new data
        Bundle args = dialog.getArguments();
        payUpperBound = args.getDouble("upper");
        payLowerBound = args.getDouble("lower");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // do nothing for now (this function might need to be removed if it doesn't end up doing anything)
    }

    // Initializes the View objects on the screen
    private void initializeViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewJobs.setAdapter(new ItemJobViewAdapter(getApplicationContext(), jobListingData));
        searchView = findViewById(R.id.sv_search_job);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        fragmentManager = getSupportFragmentManager();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        payFilterButton = findViewById(R.id.btn_pay);
        payFilterDialogFragment = new PayFilterDialogFragment();
        searchButton = findViewById(R.id.btn_filter);
    }

    // Initializes the button onClickListeners
    private void initializeButtonListeners() {
        payFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // user clicks the pay filter button
                Bundle args = new Bundle();
                args.putDouble("upper", payUpperBound);
                args.putDouble("lower", payLowerBound);
                payFilterDialogFragment.setArguments(args); // set arguments to send
                payFilterDialogFragment.show(fragmentManager, "PAY_FRAGMENT"); // show the popup
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // user clicks the search button
                initiateSearch(searchView.getQuery().toString());
            }
        });
    }

    // Updates the recycler view to reflect current search results
    private void updateViews() {
        recyclerViewJobs.setAdapter(new ItemJobViewAdapter(getApplicationContext(), jobListingData));
    }
}