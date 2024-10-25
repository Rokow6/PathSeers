package edu.utsa.cs3773.pathseer;

import java.util.*;

public class User {
    private int age;
    private String name;
    private String bio;
    private String username;
    private String password;
    private ArrayList<Notification> notifications;

    public User() {
        notifications = new ArrayList<Notification>();
    }

    public User(int age, String name, String bio, String username, String password) {
        this.age = age;
        this.name = name;
        this.bio = bio;
        this.username = username;
        this.password = password;
        notifications = new ArrayList<Notification>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification notification) {
        notifications.add(notification);
    }
}
