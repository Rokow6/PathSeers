package edu.utsa.cs3773.pathseer;

import java.util.ArrayList;
import java.util.List;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.JobListingData;

// Provides functions to search job listings
public class JobListingSearcher {

    // Searches job listings using a search string; looks through title and description for occurrence of search
    // Leave inputs as null or -1 if they are not used
    // Very simple search, only uses a single string and does not take keywords into account
    public static List<JobListingData> SearchJobListings(AppDatabase db, String search, int payLowerBound, int payUpperBound,
                                                          ArrayList<String> requirements, ArrayList<String> tags) {
        List<JobListingData> searchList = db.jobListingDao().getAll(); // get the jobs listings so we can narrow them down

        if (search != null) { // check if search text was entered
            List<JobListingData> searchTextResults = db.jobListingDao().getJobListingsBySearchText(search);
            searchList.retainAll(searchTextResults); // only keep results in output
        }

        if (payLowerBound > -1 && payUpperBound > -1 && payUpperBound >= payLowerBound) { // check if bounds are set properly
            List<JobListingData> payRangeResults = db.jobListingDao().getJobListingsByPayRange(payLowerBound, payUpperBound);
            searchList.retainAll(payRangeResults); // narrow down results further
        }

        if (requirements != null) { // check if requirements were entered
            List<JobListingData> requirementsResults = db.jobListingDao().getAll();
            for (String requirement : requirements) { // iterate through each requirement and search for job listings that have it
                // narrow down by each requirement
                requirementsResults.retainAll(db.jobListingDao().getJobListingsByRequirement(requirement));
            }
            searchList.retainAll(requirementsResults); // narrow result down again
        }

        if (tags != null) {
            List<JobListingData> tagsResults = db.jobListingDao().getAll();
            for (String tag : tags) { // iterate through each tag and search for job listings that have it
                // narrow down by each tag
                tagsResults.retainAll(db.jobListingDao().getJobListingsByTag(tag));
            }
            searchList.retainAll(tagsResults); // narrow result down again
        }

        return searchList; // return fully narrowed down results
    }

}
