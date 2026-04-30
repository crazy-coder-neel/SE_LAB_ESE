package com.smartwaste.control;

import com.smartwaste.entity.*;
import com.smartwaste.main.DataStore;
import java.util.ArrayList;


public class VehicleAssignmentController {
    private DataStore dataStore;

    public VehicleAssignmentController() {
        this.dataStore = DataStore.getInstance();
    }

    
    public Vehicle assignVehicle(String reportId, String vehicleId) throws Exception {
        
        Reports report = null;
        for (Reports r : dataStore.getReports()) {
            if (r.getReportId().equalsIgnoreCase(reportId)) {
                report = r;
                break;
            }
        }
        if (report == null) {
            throw new Exception("Report ID " + reportId + " not found.");
        }

        
        Vehicle vehicle = null;
        for (Vehicle v : dataStore.getVehicles()) {
            if (v.getVehicleId().equalsIgnoreCase(vehicleId)) {
                vehicle = v;
                break;
            }
        }
        if (vehicle == null) {
            throw new Exception("Vehicle ID " + vehicleId + " not found.");
        }
        if (!vehicle.isAvailability()) {
            throw new Exception("Vehicle " + vehicleId + " is currently unavailable.");
        }

        
        report.setAssignedVehicleId(vehicleId);
        report.updateStatus("Assigned");
        vehicle.setAvailability(false);

        return vehicle;
    }

    
    public void releaseVehicle(String vehicleId) {
        for (Vehicle v : dataStore.getVehicles()) {
            if (v.getVehicleId().equalsIgnoreCase(vehicleId)) {
                v.setAvailability(true);
                break;
            }
        }
    }

    
    public ArrayList<Reports> getAssignedReports(String driverId) {
        ArrayList<Reports> assignedReports = new ArrayList<>();
        for (Reports report : dataStore.getReports()) {
            if (report.getAssignedVehicleId() != null) {
                for (Vehicle v : dataStore.getVehicles()) {
                    if (v.getVehicleId().equals(report.getAssignedVehicleId())
                            && v.getDriverId() != null
                            && v.getDriverId().equals(driverId)
                            && !report.getComplaintStatus().equals("Resolved")) {
                        assignedReports.add(report);
                        break;
                    }
                }
            }
        }
        return assignedReports;
    }

    
    public void markCompleted(String reportId) throws Exception {
        for (Reports report : dataStore.getReports()) {
            if (report.getReportId().equalsIgnoreCase(reportId)) {
                if (report.getAssignedVehicleId() != null) {
                    report.updateStatus("Resolved");
                    releaseVehicle(report.getAssignedVehicleId());

                    
                    for (Bin bin : dataStore.getBins()) {
                        if (bin.getBinId().equalsIgnoreCase(report.getBinId())) {
                            bin.setFillLevel(0);
                            bin.setStatus("Empty");
                            break;
                        }
                    }
                    return;
                }
                throw new Exception("No vehicle assigned to report " + reportId + ". Cannot mark as completed.");
            }
        }
        throw new Exception("Report ID " + reportId + " not found.");
    }

    
    public void updateReportStatus(String reportId, String status) throws Exception {
        for (Reports report : dataStore.getReports()) {
            if (report.getReportId().equalsIgnoreCase(reportId)) {
                report.updateStatus(status);
                return;
            }
        }
        throw new Exception("Report ID " + reportId + " not found.");
    }
}
