package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.DataTest;
import edu.utsa.cs3773.pathseer.data.UserDao;
import edu.utsa.cs3773.pathseer.data.UserData;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase db;
    private UserDao userDao;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_sign_in);
        registerButton = findViewById(R.id.button_register);

        // Initialize Database instance for persistent data access
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "pathseers-database")
                .allowMainThreadQueries() // purely for testing can lead to big slow down with lots of data
                .fallbackToDestructiveMigration() // will cause all data to be lost on schema change, which is fine since we only have test data
                .build();
        DatabaseInitializer.initializeDatabase(db); // initialize empty database tables

        userDao = db.userDao();


        //Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        //Set up register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleLogin() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        //Retrieve user data from the database
        UserData userData = userDao.getUserDataByUsername(username);

        if (userData == null) {
            //User not found in the database
            Toast.makeText(this, "User not found. Please register first.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            //Hash the entered password then add the stored salt
            String hashedEnteredPass = Encryptor.encryptString(password, userData.salt);
            //Compare hashed password
            if (hashedEnteredPass.equals(userData.password)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                saveUserID(username);

                Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred during login.", Toast.LENGTH_SHORT).show();
        }

    }

    public void saveUserID(String username) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("user_id", db.userDao().getUserIDFromUsername(username));
        editor.apply();
    }
}