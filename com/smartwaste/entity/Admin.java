package com.smartwaste.entity;

/**
 * Admin Entity Class - Represents an administrator who manages complaints.
 * Inherits from User. Admin can view reports, assign vehicles, and update status.
 */
public class Admin extends User {

    // Constructor
    public Admin(String userId, String username, String password) {
        super(userId, username, password, "Admin");
    }

    @Override
    public String toString() {
        return "Admin{userId='" + getUserId() + "', username='" + getUsername() + "'}";
    }
}
