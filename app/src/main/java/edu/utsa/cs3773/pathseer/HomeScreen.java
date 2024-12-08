package edu.utsa.cs3773.pathseer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends NavigationActivity {

    private TextView welcomeMessage;
    private Button letsGetStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Initialize views
        welcomeMessage = findViewById(R.id.welcome_message);
        letsGetStartedButton = findViewById(R.id.button_lets_go);

        // Retrieve the user's full name from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        String fullName = sharedPref.getString("fullName", "User"); // Default to "User" if not found

        // Set the welcome message dynamically
        welcomeMessage.setText("Hi " + fullName + "!\nFind a perfect job match.");

        // Set up the button click listener to navigate to JobSearchScreen
        letsGetStartedButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeScreen.this, JobSearchScreen.class);
            startActivity(intent);
            finish(); //
        });
    }
}
