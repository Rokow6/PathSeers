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

        // Initialize views
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_sign_in);
        registerButton = findViewById(R.id.button_register);

        // Initialize Database instance for persistent data access using the singleton
        db = AppDatabase.getInstance(this);
        executorService.execute(() -> DatabaseInitializer.initializeDatabase(db));
        userDao = db.userDao();

        // Set up login button click listener
        loginButton.setOnClickListener(view -> handleLogin());

        // Set up register button click listener
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        final String username = usernameInput.getText().toString().trim();
        final String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use Executor to handle background operation
        executorService.execute(() -> {
            // Retrieve user data from the database
            UserData userData = userDao.getUserDataByUsername(username);
            if (userData == null) {
                runOnUiThread(() -> Toast.makeText(this, "User not found. Please register first.", Toast.LENGTH_SHORT).show());
                return;
            }

            final String salt = userData.salt != null ? userData.salt : "";
            final String storedPassword = userData.password != null ? userData.password : "";

            runOnUiThread(() -> {
                try {
                    String hashedEnteredPass = Encryptor.encryptString(password, salt);

                    // Check if entered password matches stored password
                    if (hashedEnteredPass.equals(storedPassword)) {
                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        // Save user details (user ID and full name)
                        saveUserDetails(username);

                        // Proceed to HomeScreen activity
                        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Error hashing password: ", e);
                    Toast.makeText(this, "An error occurred during login.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void saveUserDetails(String username) {
        executorService.execute(() -> {
            UserData userData = userDao.getUserDataByUsername(username);
            if (userData != null) {
                // Save the full name along with the user ID
                PreferenceUtils.saveUserSession(MainActivity.this, userData.userID, userData.name);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
