package edu.utsa.cs3773.pathseer.objectClasses;

import androidx.room.Query;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import edu.utsa.cs3773.pathseer.Encryptor;
import edu.utsa.cs3773.pathseer.data.UserDao;

public class User {
    private int age;
    private String name;
    private String bio;
    private String username;
    private String password;
    private ArrayList<Notification> notifications;
    private UserDao userDao;

    public User() {
        notifications = new ArrayList<Notification>();
    }

    public User(int age, String name, String bio, String username, String password, UserDao userDao) throws NoSuchAlgorithmException {
        this.age = age;
        this.name = name;
        this.bio = bio;
        this.username = username;
        notifications = new ArrayList<Notification>();
        this.userDao = userDao;

        userDao.addUserData(age, name, bio, username, "", "");

        this.password = Encryptor.encryptString(password, userDao.getUserIDFromUsername(this.username), userDao);

        userDao.updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, userDao.getUserDataByID(getID()).salt);
    }

    public int getID() {
        return userDao.getUserIDFromUsername(username);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        userDao.updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, userDao.getUserDataByID(getID()).salt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        userDao.updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, userDao.getUserDataByID(getID()).salt);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
        userDao.updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, userDao.getUserDataByID(getID()).salt);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        userDao.updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, userDao.getUserDataByID(getID()).salt);
    }

    // needs to change to check if password input matches hash in the database
    // run encryptString(input + salt).equals(hash)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        String hashedPass = Encryptor.encryptString(password, getID(), userDao); //need to store hash in the database
        this.password = hashedPass; // prolly don't need this idk
        userDao.updateUserData(getID(), this.age, this.name, this.bio, this.username, this.password, userDao.getUserDataByID(getID()).salt);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification notification) {
        notifications.add(notification);
    }
}
