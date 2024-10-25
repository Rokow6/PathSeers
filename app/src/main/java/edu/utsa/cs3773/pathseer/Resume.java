package edu.utsa.cs3773.pathseer;

import java.io.*;

public class Resume {
    private String fileName;
    private File resumeFile;

    public Resume(File resumeFile) {
        this.resumeFile = resumeFile;
        fileName = resumeFile.getName();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(File resumeFile) {
        this.resumeFile = resumeFile;
    }
}