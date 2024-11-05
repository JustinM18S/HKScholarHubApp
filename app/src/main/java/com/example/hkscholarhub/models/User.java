package com.example.hkscholarhub.models;

public class User {
    private long id; // User ID
    private String name; // Name of the user
    private String email; // User's email address
    private String dutyType; // User's duty type, e.g., Internal Facilitator, External Facilitator
    private String hkType; // User's HK type, e.g., HK25, HK50, HK75

    // Constructor
    public User(long id, String name, String email, String dutyType, String hkType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dutyType = dutyType;
        this.hkType = hkType;
    }

    public User(String name, String email, long id) {
        this.id = id;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public String getHkType() {
        return hkType;
    }

    public void setHkType(String hkType) {
        this.hkType = hkType;
    }

    @Override
    public String toString() {
        return name; // When displayed in a spinner or dropdown, it will show the name
    }
}
