package com.smartwaste.entity;


public class Bin {
    private String binId;
    private String location;
    private int fillLevel;
    private String status;

    
    public Bin(String binId, String location, int fillLevel, String status) {
        this.binId = binId;
        this.location = location;
        this.fillLevel = fillLevel;
        this.status = status;
    }

    
    public String getBinId() { return binId; }
    public void setBinId(String binId) { this.binId = binId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getFillLevel() { return fillLevel; }
    public void setFillLevel(int fillLevel) { this.fillLevel = fillLevel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Bin{binId='" + binId + "', location='" + location
                + "', fillLevel=" + fillLevel + ", status='" + status + "'}";
    }
}
