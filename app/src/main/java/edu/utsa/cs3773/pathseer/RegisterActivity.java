package edu.utsa.cs3773.pathseer;

import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameInput, emailInput, usernameInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private AppDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = MainActivity.db;
        userDao = db.userDao();

        // Initialize views
        initializeViews();

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

        // Check if the username already exists in the database
        if (db.userDao().getUserIDFromUsername(username) != 0) {
            showToast("Username already registered. Please log in.");
            return;
        }

        // Check if the email already exists in the database
        if (db.userDao().getUserIDFromEmail(email) != 0) {
            showToast("Email already registered. Please log in.");
            return;
        }

        try {
            //Generate salt
            String salt = Encryptor.getNewUserSaltString();
            // Encrypt the password and get the salt
            String hashedPassword = Encryptor.encryptString(password, salt);

            // Prepare a new UserData object
            UserData newUser = new UserData();
            newUser.name = fullName;
            newUser.email = email;
            newUser.username = username;
            newUser.password = hashedPassword; // Store the hashed password
            newUser.salt = salt;               // Store the salt


            // Save the user data into the database
            new Thread(() -> {
                userDao.addUserData(newUser);  // Insert the user
                runOnUiThread(() -> showToast("User registered successfully!"));
                finish();
            }).start();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            showToast("Error occurred while creating account.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
