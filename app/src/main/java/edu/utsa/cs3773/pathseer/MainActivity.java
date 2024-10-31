package edu.utsa.cs3773.pathseer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Create Database instance and test it through logcat
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "pathseers-database")
                .allowMainThreadQueries() // purely for testing can lead to big slow down with lots of data
                .fallbackToDestructiveMigration() // will cause all data to be lost on schema change, which is fine since we only have test data
                .build();
        // DataTest.TestDatabase(db); // just using this script to test if the database queries are working
    }
}