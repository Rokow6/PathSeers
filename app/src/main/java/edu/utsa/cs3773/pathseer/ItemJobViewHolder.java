package edu.utsa.cs3773.pathseer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemJobViewHolder extends RecyclerView.ViewHolder {
    public View btn_apply;
    private ImageView jobIcon;
    TextView tv_job_title, tv_company_location, tv_salary;

    public ItemJobViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews() {
        jobIcon = itemView.findViewById(R.id.job_icon);
        tv_job_title = itemView.findViewById(R.id.tv_job_title);
        tv_company_location = itemView.findViewById(R.id.tv_company_location);
        tv_salary = itemView.findViewById(R.id.tv_salary);
        btn_apply = itemView.findViewById(R.id.btn_apply);
    }
}
