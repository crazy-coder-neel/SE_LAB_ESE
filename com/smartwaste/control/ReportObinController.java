package com.smartwaste.control;

import com.smartwaste.entity.*;
import com.smartwaste.main.DataStore;
import java.util.ArrayList;


public class ReportObinController {
    private DataStore dataStore;
    private IoTController iotController;

    public ReportObinController() {
        this.dataStore = DataStore.getInstance();
        this.iotController = new IoTController();
    }

    
    public Bin validateBin(String binId) throws Exception {
        if (binId == null || binId.trim().isEmpty()) {
            throw new Exception("Invalid Bin ID. Bin ID cannot be blank.");
        }
        ArrayList<Bin> bins = dataStore.getBins();
        for (Bin bin : bins) {
            if (bin.getBinId().equalsIgnoreCase(binId.trim())) {
                return bin;
            }
        }
        throw new Exception("Invalid Bin ID: " + binId + ". Bin not found in the system.");
    }

    
    public void validateWard(Bin bin, Citizen citizen) throws Exception {
        if (!bin.getLocation().equalsIgnoreCase(citizen.getWard())) {
            throw new Exception("Access denied. You can only report bins in your own ward ("
                + citizen.getWard() + "). This bin belongs to " + bin.getLocation() + ".");
        }
    }

    
    public void validateLocation(String location) throws Exception {
        if (location == null || location.trim().isEmpty()) {
            throw new Exception("Location is required. Please enter a valid location.");
        }
    }

    
    public void validateFillLevel(int fillLevel) throws Exception {
        if (fillLevel < 0 || fillLevel > 100) {
            throw new Exception("Fill level must be between 0 and 100.");
        }
        if (!iotController.isOverflow(fillLevel)) {
            String status = iotController.calculateBinStatus(fillLevel);
            throw new Exception("Complaint rejected. Bin status is '" + status
                + "' (Fill Level: " + fillLevel
                + "%). Complaints are only accepted when fill level is 80% or above.");
        }
    }

    
    public void validateDescription(String description) throws Exception {
        if (description == null || description.trim().isEmpty()) {
            throw new Exception("Description cannot be empty. Please provide a complaint description.");
        }
    }

    
    public void checkDuplicateComplaint(String binId, String citizenId) throws Exception {
        ArrayList<Reports> reports = dataStore.getReports();
        for (Reports report : reports) {
            if (report.getBinId().equalsIgnoreCase(binId)
                    && report.getCitizenId().equals(citizenId)
                    && !report.getComplaintStatus().equals("Resolved")) {
                throw new Exception("Duplicate complaint detected. An unresolved report (ID: "
                    + report.getReportId() + ") already exists for Bin " + binId + ".");
            }
        }
    }

    
    public Reports submitReport(Citizen citizen, String binId, String location,
                                int fillLevel, String description) throws Exception {

        
        Bin bin = validateBin(binId);

        
        validateWard(bin, citizen);

        
        validateLocation(location);

        
        validateFillLevel(fillLevel);

        
        validateDescription(description);

        
        checkDuplicateComplaint(binId, citizen.getUserId());

        
        Reports report = createReport(citizen.getUserId(), binId, location, fillLevel, description);

        
        String binStatus = iotController.calculateBinStatus(fillLevel);
        bin.setFillLevel(fillLevel);
        bin.setStatus(binStatus);

        
        notifyAdmin(report);

        return report;
    }

    
    public Reports createReport(String citizenId, String binId, String location,
                                int fillLevel, String description) {
        String reportId = "RPT" + String.format("%03d", dataStore.getReports().size() + 1);
        Reports report = new Reports(reportId, citizenId, binId, location, fillLevel, description);
        report.createReport();
        dataStore.getReports().add(report);
        return report;
    }

    
    private void notifyAdmin(Reports report) {
        String notification = "[ALERT] New overflowing bin complaint received! "
                + "Report ID: " + report.getReportId()
                + " | Bin: " + report.getBinId()
                + " | Location: " + report.getLocation()
                + " | Fill Level: " + report.getFillLevel() + "%";
        dataStore.getAdminNotifications().add(notification);
        System.out.println("\n  >> Admin has been notified about this complaint.");
    }

    
    public ArrayList<Reports> getReportsByCitizen(String citizenId) {
        ArrayList<Reports> citizenReports = new ArrayList<>();
        for (Reports report : dataStore.getReports()) {
            if (report.getCitizenId().equals(citizenId)) {
                citizenReports.add(report);
            }
        }
        return citizenReports;
    }

    
    public ArrayList<Reports> getAllReports() {
        return dataStore.getReports();
    }
}
