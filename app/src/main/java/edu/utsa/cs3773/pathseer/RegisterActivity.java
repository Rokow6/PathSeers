package edu.utsa.cs3773.pathseer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.UserDao;
import edu.utsa.cs3773.pathseer.data.UserData;
import edu.utsa.cs3773.pathseer.objectClasses.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameInput, emailInput, usernameInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private AppDatabase db;
    private UserDao userDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        initializeViews();
        db = AppDatabase.getInstance(this);
        userDao = db.userDao();

        // Set up register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void initializeViews() {
        fullNameInput = findViewById(R.id.input_full_name);
        emailInput = findViewById(R.id.input_email);
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        confirmPasswordInput = findViewById(R.id.input_confirm_password);
        registerButton = findViewById(R.id.button_sign_up);
    }

    private void registerUser() {
        String fullName = fullNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Check if all fields are filled
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Please fill out all fields");
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return;
        }

        executorService.execute(() -> {
            // Check if username or email already exists
            if (userDao.getUserIDFromUsername(username) != 0) {
                runOnUiThread(() -> showToast("Username already registered. Please log in."));
                return;
            }

            if (userDao.getUserIDFromEmail(email) != 0) {
                runOnUiThread(() -> showToast("Email already registered. Please log in."));
                return;
            }

            try {
                //Add a user with placeholder values for password and salt
                UserData tempUser = new UserData(0, fullName, "", email, username, "", "");
                userDao.addUserData(tempUser);

                //Retrieve the auto-generated userID
                int userId = userDao.getUserIDFromUsername(username);
                Log.d("RegisterActivity", "Generated User ID: " + userId);

                //Generate hashed password and update salt via Encryptor
                String hashedPassword = Encryptor.encryptString(password, userId, userDao);

                //Fetch the updated salt from the database
                String salt = userDao.getUserDataByID(userId).salt;
                Log.d("RegisterActivity", "Retrieved Salt: " + salt);

                //Update the hashed password in the database
                userDao.updatePassword(userId, hashedPassword);


                runOnUiThread(() -> {
                    Log.d("RegisterActivity", "User created successfully.");
                    showToast("User registered successfully!");
                    finish();
                });
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Log.e("RegisterActivity", "User could not be created.");
                    showToast("Error occurred while creating account.");
                });
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

}
