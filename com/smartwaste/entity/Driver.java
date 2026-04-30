package com.smartwaste.entity;

/**
 * Driver Entity Class - Represents a collection driver.
 * Inherits from User. Drivers are assigned to vehicles and complete bin collections.
 */
public class Driver extends User {
    private boolean available;

    // Constructor
    public Driver(String userId, String username, String password) {
        super(userId, username, password, "Driver");
        this.available = true;
    }

    // Getters and Setters
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Driver{userId='" + getUserId() + "', username='" + getUsername()
                + "', available=" + available + "}";
    }
}
