package com.smartwaste.entity;


public class Vehicle {
    private String vehicleId;
    private String driverId;
    private boolean availability;

    
    public Vehicle(String vehicleId, String driverId, boolean availability) {
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.availability = availability;
    }

    
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public boolean isAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    @Override
    public String toString() {
        return "Vehicle{vehicleId='" + vehicleId + "', driverId='" + driverId
                + "', availability=" + availability + "}";
    }
}
