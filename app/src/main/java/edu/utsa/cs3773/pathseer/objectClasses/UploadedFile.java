package edu.utsa.cs3773.pathseer.objectClasses;

import android.net.Uri;

public class UploadedFile {
    private Uri fileUri;
    private String fileName;

    // Constructor
    public UploadedFile(Uri fileUri, String fileName) {
        this.fileUri = fileUri;
        this.fileName = fileName;
    }

    // Getter for file URI
    public Uri getFileUri() {
        return fileUri;
    }

    // Getter for file name
    public String getFileName() {
        return fileName;
    }
}
