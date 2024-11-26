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
import android.widget.Toast;

import androidx.annotation.Nullable;

import edu.utsa.cs3773.pathseer.data.AppDatabase;
import edu.utsa.cs3773.pathseer.objectClasses.UploadedFile;

public class ProfileScreen extends NavigationActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private UploadedFile uploadedFile; // Attribute to store the uploaded file
    private ImageView resumeView; // To display uploaded image
    private Button addResumeButton;
    private SharedPreferences sharedPref;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile_screen, findViewById(R.id.content_frame));

        db = AppDatabase.getInstance(this);
        sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

        initializeViews();

        // v -> openFilePicker()
        addResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
                // Logic here later for adding UploadedFile data to database
            }
        });
    }

    private void initializeViews() {
        addResumeButton = findViewById(R.id.addResumeButton);
        resumeView = findViewById(R.id.resumeView);
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
            }
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)); // idk what this is about but the code works maybe so ¯\_('_')_/¯
                }
            }
        }
        return fileName != null ? fileName : uri.getLastPathSegment();
    }

    private void displayImage(Uri fileUri) {
        if (resumeView != null) {
            resumeView.setImageURI(fileUri);
        }
    }
}