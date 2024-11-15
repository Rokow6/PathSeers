package edu.utsa.cs3773.pathseer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeScreen extends NavigationActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home_screen, findViewById(R.id.content_frame));

        Button createJobPosting = findViewById(R.id.createJobPosting);
        Button searchJobListings = findViewById(R.id.searchScreen);

        createJobPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, PostJobScreen.class);
                startActivity(intent);
                finish();
            }
        });

        searchJobListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, JobSearchScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
