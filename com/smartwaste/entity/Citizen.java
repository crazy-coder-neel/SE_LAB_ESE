package com.smartwaste.entity;

/**
 * Citizen Entity Class - Represents a citizen who can report overflowing bins.
 * Inherits from User. Each citizen is assigned to a specific ward.
 * Citizens can only report bins located in their own ward (TC_08, TC_09).
 */
public class Citizen extends User {
    private String ward;

    // Constructor
    public Citizen(String userId, String username, String password, String ward) {
        super(userId, username, password, "Citizen");
        this.ward = ward;
    }

    // Getters and Setters
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    @Override
    public String toString() {
        return "Citizen{userId='" + getUserId() + "', username='" + getUsername()
                + "', ward='" + ward + "'}";
    }
}
