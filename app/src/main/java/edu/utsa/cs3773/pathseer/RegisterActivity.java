package edu.utsa.cs3773.pathseer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.data.UserDao;
import edu.utsa.cs3773.pathseer.objectClasses.User;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameInput, ageInput, bioInput,usernameInput, passwordInput;
    private Button registerButton;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize views
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        bioInput = findViewById(R.id.bioInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);

        userDao = MainActivity.db.userDao();

        //Set up register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = nameInput.getText().toString();
        int age = Integer.parseInt(ageInput.getText().toString());
        try {
            age = Integer.parseInt(ageInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
            return;
        }
        String bio = bioInput.getText().toString();
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        //Check if username already exists
        if (userDao.getUserIDFromUsername(username) != 0) {
            Toast.makeText(this, "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //Create a new User instance
            User newUser = new User(age, name, bio, username, password, userDao);
            //If successful
            Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Toast.makeText(this,"Error occured while creating account.", Toast.LENGTH_SHORT).show();
        }
    }
}