package edu.utsa.cs3773.pathseer;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.objectClasses.UploadedFile;

public class ProfileScreen extends NavigationActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private UploadedFile uploadedFile;
    private ImageView resumeView;
    private Button addResumeButton;
    private SharedPreferences sharedPref;
    private AppDatabase db;
    int jobSeekerID;
    private TextView profilePictureText;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile_screen, findViewById(R.id.content_frame));

        db = AppDatabase.getInstance(this);
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        jobSeekerID = db.jobSeekerDao().getJobSeekerIDFromUserID(sharedPref.getInt("user_id", -1));

        initializeViews();

        // Check if the user is a job seeker and if they have a resume saved
        if (jobSeekerID > 0 && !db.jobSeekerDao().getResumeUriStringFromJobSeekerID(jobSeekerID).isEmpty()) {
            displayImage(Uri.parse(db.jobSeekerDao().getResumeUriStringFromJobSeekerID(jobSeekerID)));
        }

        // Fetch user info from the database
        String name = db.jobSeekerDao().getFullNameFromJobSeekerID(jobSeekerID);
        String email = db.jobSeekerDao().getEmailFromJobSeekerID(jobSeekerID);

        if (name != null) {
            String initials = getInitials(name);
            profilePictureText.setText(initials); // Display initials
        }

        if (email != null) {
            emailTextView.setText(email); // Display email
        }

        // Open file picker on button click
        addResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
    }

    private void initializeViews() {
        addResumeButton = findViewById(R.id.addResumeButton);
        resumeView = findViewById(R.id.resumeView);
        profilePictureText = findViewById(R.id.profilePicture);
        emailTextView = findViewById(R.id.email);
    }

    private String getInitials(String fullName) {
        String[] nameParts = fullName.split(" ");
        StringBuilder initials = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0)); // Add the first letter of each name part
            }
        }

        return initials.toString().toUpperCase(); // Return initials in uppercase
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*"); // Restrict to images
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri fileUri = data.getData();
                String fileName = getFileName(fileUri);
                uploadedFile = new UploadedFile(fileUri, fileName); // Store in class attribute
                Toast.makeText(this, "File Uploaded: " + fileName, Toast.LENGTH_SHORT).show();
                displayImage(fileUri); // Display the selected image

                if (jobSeekerID > 0) {
                    // Add URI to the database for job seekers
                    db.jobSeekerDao().updateJobSeekerByID(jobSeekerID, fileUri.toString(), fileName);
                    getContentResolver().takePersistableUriPermission(fileUri,
                            (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION)); // Permission handling
                } else {
                    Toast.makeText(this, "WARN: Resume will only be saved for job seeker users", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        fileName = cursor.getString(columnIndex); // Fetching the file name
                    }
                }
            }
        }
        return fileName != null ? fileName : uri.getLastPathSegment();
    }


    private void displayImage(Uri fileUri) {
        if (resumeView != null) {
            resumeView.setImageURI(fileUri); // Displaying the uploaded image
        }
    }
}
