package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.UserDao;
import edu.utsa.cs3773.pathseer.data.UserData;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase db;
    private UserDao userDao;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button registerButton;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_sign_in);
        registerButton = findViewById(R.id.button_register);

        // Initialize Database instance for persistent data access using the singleton
        db = AppDatabase.getInstance(this);
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

        // Use Executor to handle background operation
        executorService.execute(() -> {
            // Retrieve user data from the database
            UserData userData = userDao.getUserDataByUsername(username);
            String salt = "";
            String userDataPassword = "";
            if (userData != null) {
                salt = userData.salt;
                userDataPassword = userData.password;
            }
            Log.d("LoginActivity", "Username: " + username);
            Log.d("LoginActivity", "Stored Salt for Login: " + salt);
            Log.d("LoginActivity", "Stored Password for Login: " + userDataPassword);

            runOnUiThread(() -> {
                if (userData != null) {
                    try {
                        String hashedEnteredPass = Encryptor.encryptString(password, userData.salt);

                        // Log the entered and stored hash for debugging
                        Log.d("LoginActivity", "Entered Hash: " + hashedEnteredPass);
                        Log.d("LoginActivity", "Stored Hash: " + userData.password);

                        if (hashedEnteredPass.equals(userData.password)) {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            saveUserID(username);

                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "An error occurred during login.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User not found. Please register first.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        });
    }

    public void saveUserID(String username) {
        executorService.execute(() -> {
            int userId = db.userDao().getUserIDFromUsername(username);

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("user_id", userId);
            editor.apply();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


}