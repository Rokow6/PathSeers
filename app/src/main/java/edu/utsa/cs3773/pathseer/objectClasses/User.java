package edu.utsa.cs3773.pathseer.objectClasses;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import edu.utsa.cs3773.pathseer.Encryptor;
import edu.utsa.cs3773.pathseer.data.AppDatabase;

public class User {
    private int age;
    private String name;
    private String bio;
    private String username;
    private String password;
    private ArrayList<Notification> notifications;
    private AppDatabase db;

    // Creates default User
    public User(AppDatabase db) {
        this.db = db;
        notifications = new ArrayList<Notification>();

        db.userDao().addUserData(0, "", "", "", "", "");
    }

    // Creates User with an age, name, bio, username, and password
    public User(int age, String name, String bio, String username, String password, AppDatabase db) throws NoSuchAlgorithmException {
        this.age = age;
        this.name = name;
        this.bio = bio;
        this.username = username;
        notifications = new ArrayList<Notification>();
        this.db = db;

        db.userDao().addUserData(age, name, bio, username, "", "");

        this.password = Encryptor.encryptString(password, db.userDao().getUserIDFromUsername(this.username), db.userDao());

        db.userDao().updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, db.userDao().getUserDataByID(getID()).salt);
    }

    public User(int i, String fullName, String email, String password) {
    }

    // Returns the ID of the User
    public int getID() {
        return db.userDao().getUserIDFromUsername(username);
    }

    // Returns the age of the User
    public int getAge() {
        return age;
    }

    // Sets the age of the User
    public void setAge(int age) {
        this.age = age;
        db.userDao().updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, db.userDao().getUserDataByID(getID()).salt);
    }

    // Returns the name of the User
    public String getName() {
        return name;
    }

    // Sets the name of the User
    public void setName(String name) {
        this.name = name;
        db.userDao().updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, db.userDao().getUserDataByID(getID()).salt);
    }

    // Returns the bio of the User
    public String getBio() {
        return bio;
    }

    // Sets the bio of the User
    public void setBio(String bio) {
        this.bio = bio;
        db.userDao().updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, db.userDao().getUserDataByID(getID()).salt);
    }

    // Returns the username of the User
    public String getUsername() {
        return username;
    }

    // Sets the username of the User
    public void setUsername(String username) {
        this.username = username;
        db.userDao().updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, db.userDao().getUserDataByID(getID()).salt);
    }

    // Returns the password hash of the User
    public String getPassword() {
        return password;
    }

    // Sets and encrypts the password of the User
    public void setPassword(String password) throws NoSuchAlgorithmException {
        String hashedPass = Encryptor.encryptString(password, getID(), db.userDao()); //need to store hash in the database
        this.password = hashedPass; // prolly don't need this idk
        db.userDao().updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, db.userDao().getUserDataByID(getID()).salt);
    }

    // Returns the ArrayList of Notifications of the User
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    // Adds Notification to ArrayList of the User
    public void setNotifications(Notification notification) {
        notifications.add(notification);
    }
}
