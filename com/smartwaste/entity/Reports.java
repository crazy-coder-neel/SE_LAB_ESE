package com.smartwaste.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Reports {
    private String reportId;
    private String citizenId;
    private String binId;
    private String location;
    private int fillLevel;
    private String description;
    private LocalDateTime reportDate;
    private String complaintStatus;
    private String assignedVehicleId;

    
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    
    public Reports(String reportId, String citizenId, String binId,
                   String location, int fillLevel, String description) {
        this.reportId = reportId;
        this.citizenId = citizenId;
        this.binId = binId;
        this.location = location;
        this.fillLevel = fillLevel;
        this.description = description;
        this.reportDate = LocalDateTime.now();
        this.complaintStatus = "Pending";
        this.assignedVehicleId = null;
    }

    
    public void createReport() {
        this.reportDate = LocalDateTime.now();
        this.complaintStatus = "Pending";
    }

    
    public void updateStatus(String status) {
        this.complaintStatus = status;
    }

    
    public void displayReport() {
        System.out.println("  +-----------------------------------------+");
        System.out.println("  | Report ID       : " + padRight(reportId, 20) + "|");
        System.out.println("  | Citizen ID      : " + padRight(citizenId, 20) + "|");
        System.out.println("  | Bin ID          : " + padRight(binId, 20) + "|");
        System.out.println("  | Location        : " + padRight(location, 20) + "|");
        System.out.println("  | Fill Level      : " + padRight(fillLevel + "%", 20) + "|");
        System.out.println("  | Description     : " + padRight(description, 20) + "|");
        System.out.println("  | Report Date     : " + padRight(reportDate.format(FORMATTER), 20) + "|");
        System.out.println("  | Status          : " + padRight(complaintStatus, 20) + "|");
        String vehicleStr = (assignedVehicleId != null) ? assignedVehicleId : "Not Assigned";
        System.out.println("  | Assigned Vehicle: " + padRight(vehicleStr, 20) + "|");
        System.out.println("  +-----------------------------------------+");
    }

    
    private String padRight(String s, int n) {
        if (s.length() >= n) return s.substring(0, n);
        return String.format("%-" + n + "s", s);
    }

    
    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getCitizenId() { return citizenId; }
    public void setCitizenId(String citizenId) { this.citizenId = citizenId; }

    public String getBinId() { return binId; }
    public void setBinId(String binId) { this.binId = binId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getFillLevel() { return fillLevel; }
    public void setFillLevel(int fillLevel) { this.fillLevel = fillLevel; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getReportDate() { return reportDate; }
    public void setReportDate(LocalDateTime reportDate) { this.reportDate = reportDate; }

    public String getComplaintStatus() { return complaintStatus; }
    public void setComplaintStatus(String complaintStatus) { this.complaintStatus = complaintStatus; }

    public String getAssignedVehicleId() { return assignedVehicleId; }
    public void setAssignedVehicleId(String assignedVehicleId) { this.assignedVehicleId = assignedVehicleId; }
}
