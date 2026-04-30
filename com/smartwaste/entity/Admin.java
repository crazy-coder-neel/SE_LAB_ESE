package com.smartwaste.entity;


public class Admin extends User {

    
    public Admin(String userId, String username, String password) {
        super(userId, username, password, "Admin");
    }

    @Override
    public String toString() {
        return "Admin{userId='" + getUserId() + "', username='" + getUsername() + "'}";
    }
}
