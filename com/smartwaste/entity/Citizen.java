package com.smartwaste.entity;


public class Citizen extends User {
    private String ward;

    
    public Citizen(String userId, String username, String password, String ward) {
        super(userId, username, password, "Citizen");
        this.ward = ward;
    }

    
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    @Override
    public String toString() {
        return "Citizen{userId='" + getUserId() + "', username='" + getUsername()
                + "', ward='" + ward + "'}";
    }
}
