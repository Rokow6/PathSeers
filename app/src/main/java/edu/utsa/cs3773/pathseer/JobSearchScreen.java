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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.JobListingData;
import edu.utsa.cs3773.pathseer.data.RequirementData;
import edu.utsa.cs3773.pathseer.data.TagData;

public class JobSearchScreen extends NavigationActivity implements PayFilterDialogFragment.PayFilterDialogListener,
        MultiChoiceFilterDialogFragment.MultiChoiceFilterDialogListener {
    private RecyclerView recyclerViewJobs;
    private SearchView searchView;
    private SearchManager searchManager;
    private FragmentManager fragmentManager;
    private List<JobListingData> jobListingData;
    private ArrayList<String> requirementTexts;
    private ArrayList<String> tagTexts;
    private String query;
    private double payUpperBound = -1;
    private double payLowerBound = -1;
    private ArrayList<String> selectedRequirements;
    private ArrayList<String> selectedTags;
    private AppDatabase db;
    private Button payFilterButton;
    private Button requirementFilterButton;
    private Button tagFilterButton;
    private ImageButton searchButton;
    private PayFilterDialogFragment payFilterDialogFragment;
    private MultiChoiceFilterDialogFragment requirementFilterDialogFragment;
    private MultiChoiceFilterDialogFragment tagFilterDialogFragment;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_job_search_screen, findViewById(R.id.content_frame));

        db = AppDatabase.getInstance(this);
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        jobListingData = db.jobListingDao().getAll(); // Pulls all the JobListingData

        // initialize requirement related variables for the filter
        selectedRequirements = new ArrayList<>();
        requirementTexts = new ArrayList<>();
        List<RequirementData> requirementList = db.requirementDao().getAll();
        for (RequirementData requirement : requirementList) {
            if (!requirementTexts.contains(requirement.text)) { // do not add the text more than once if it is a duplicate
                requirementTexts.add(requirement.text); // add the text of the requirement
            }
        }

        // initialize tag related variables for filter
        selectedTags = new ArrayList<>();
        tagTexts = new ArrayList<>();
        List<TagData> tagList = db.tagDao().getAll();
        for (TagData tag : tagList) {
            if (!tagTexts.contains(tag.text)) { // check if it's already there
                tagTexts.add(tag.text); // add the tag
            }
        }

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
        jobListingData = JobListingSearcher.SearchJobListings(db, query, payLowerBound, payUpperBound,
                selectedRequirements, selectedTags); // get search results based on entered query
        updateViews();
    }

    // Called when the user clicks Set in the dialog
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // update the current filters with the new data
        Bundle args = dialog.getArguments();
        // check what got returned and check which filter it was
        if (args.getDouble("upper", -2) != -2 && args.getDouble("lower", -2) != -2) {
            payUpperBound = args.getDouble("upper", -1);
            payLowerBound = args.getDouble("lower", -1);
            if (payUpperBound < payLowerBound) { // check if upper bound is less than lower bound and error if it is
                Toast.makeText(this, "ERROR: The upper bound MUST be greater than the lower bound", Toast.LENGTH_LONG).show();
                payUpperBound = -1;
                payLowerBound = -1;
            }
        }
        else if (args.getStringArrayList("selectedItems") != null) {
            if (args.getInt("fragmentID", -1) == 1) { // fragment was for requirements so update that
                selectedRequirements = args.getStringArrayList("selectedItems");
            }
            else if (args.getInt("fragmentID", -1) == 2) { // ditto but for tags
                selectedTags = args.getStringArrayList("selectedItems");
            }
        }

        initiateSearch(searchView.getQuery().toString()); // since new filters were set, search with those filters
    }

    // Called when the user clicks clear in the dialog
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // clears filters associated with the dialog closed
        Bundle args = dialog.getArguments();
        if (args.getInt("fragmentID", -1) == 0) { // pay filter dialog
            // clear pay filter
            payUpperBound = -1;
            payLowerBound = -1;
        }
        else if (args.getInt("fragmentID", -1) == 1) { // requirement filter dialog
            // clear requirement filter
            selectedRequirements.clear();
        }
        else if (args.getInt("fragmentID", -1) == 2) { // tag filter dialog
            // clear tag filter
            selectedTags.clear();
        }

        initiateSearch(searchView.getQuery().toString()); // search again to match newly cleared filters
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
        requirementFilterButton = findViewById(R.id.btn_requirements);
        requirementFilterDialogFragment = new MultiChoiceFilterDialogFragment();
        tagFilterButton = findViewById(R.id.btn_tags);
        tagFilterDialogFragment = new MultiChoiceFilterDialogFragment();
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
        requirementFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // user clicks the requirement filter button
                Bundle args = new Bundle();
                args.putStringArrayList("selectedItems", selectedRequirements);
                args.putStringArrayList("itemTexts", requirementTexts);
                args.putString("title", "Select Requirements");
                args.putInt("fragmentID", 1);
                requirementFilterDialogFragment.setArguments(args); // set args
                requirementFilterDialogFragment.show(fragmentManager, "REQUIREMENT_FRAGMENT"); // show popup
            }
        });
        tagFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // user clicks the tag filter button
                Bundle args = new Bundle();
                args.putStringArrayList("selectedItems", selectedTags);
                args.putStringArrayList("itemTexts", tagTexts);
                args.putString("title", "Select Tags");
                args.putInt("fragmentID", 2);
                tagFilterDialogFragment.setArguments(args); // set args
                tagFilterDialogFragment.show(fragmentManager, "TAG_FRAGMENT");
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() { // user clicks the search button
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