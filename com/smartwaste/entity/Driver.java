package com.smartwaste.entity;


public class Driver extends User {
    private boolean available;

    
    public Driver(String userId, String username, String password) {
        super(userId, username, password, "Driver");
        this.available = true;
    }

    
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Driver{userId='" + getUserId() + "', username='" + getUsername()
                + "', available=" + available + "}";
    }
}
