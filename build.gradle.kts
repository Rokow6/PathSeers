// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google() // Google's Maven repository to access Google services
        mavenCentral() // Central repository for other libraries
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.7.1") // Update this version based on your Android Studio
        classpath("com.google.gms:google-services:4.3.15") // Latest stable Google services plugin
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
