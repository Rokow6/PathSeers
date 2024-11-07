package edu.utsa.cs3773.pathseer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileScreen extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile_screen, findViewById(R.id.content_frame));
    }
}