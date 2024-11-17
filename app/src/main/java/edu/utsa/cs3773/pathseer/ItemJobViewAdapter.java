package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.utsa.cs3773.pathseer.data.JobListingData;

public class ItemJobViewAdapter extends RecyclerView.Adapter<ItemJobViewHolder> {

    Context context;
    List<JobListingData> jobListingData;

    public ItemJobViewAdapter(Context context, List<JobListingData> jobListingData) {
        this.context = context;
        this.jobListingData = jobListingData;
    }

    @NonNull
    @Override
    public ItemJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemJobViewHolder(LayoutInflater.from(context).inflate(R.layout.item_job, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemJobViewHolder holder, int position) {
        JobListingData job = jobListingData.get(position);
        holder.tv_job_title.setText(job.title);
        holder.tv_company_location.setText(job.location);
        holder.tv_salary.setText(String.valueOf(job.pay));
    }

    @Override
    public int getItemCount() {
        return jobListingData.size();
    }
}
