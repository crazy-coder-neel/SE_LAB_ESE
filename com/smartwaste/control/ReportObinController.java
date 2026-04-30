package com.smartwaste.control;

import com.smartwaste.entity.*;
import com.smartwaste.main.DataStore;
import java.util.ArrayList;

/**
 * ReportObinController - Main control class for Report Overflowing Bin use case.
 * Handles validation, duplicate checking, report creation, and admin notification.
 * Uses plain Exception with try-catch for all error handling.
 */
public class ReportObinController {
    private DataStore dataStore;
    private IoTController iotController;

    public ReportObinController() {
        this.dataStore = DataStore.getInstance();
        this.iotController = new IoTController();
    }

    /**
     * Validate that the Bin ID exists in the system (TC_03, TC_04).
     */
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

    /**
     * Validate that the bin belongs to the citizen's ward (TC_08, TC_09).
     */
    public void validateWard(Bin bin, Citizen citizen) throws Exception {
        if (!bin.getLocation().equalsIgnoreCase(citizen.getWard())) {
            throw new Exception("Access denied. You can only report bins in your own ward ("
                + citizen.getWard() + "). This bin belongs to " + bin.getLocation() + ".");
        }
    }

    /**
     * Validate that the location field is not empty (TC_11, TC_12).
     */
    public void validateLocation(String location) throws Exception {
        if (location == null || location.trim().isEmpty()) {
            throw new Exception("Location is required. Please enter a valid location.");
        }
    }

    /**
     * Validate fill level range and overflow threshold using IoTController (TC_06, TC_07).
     */
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

    /**
     * Validate that the description is not empty (TC_05).
     */
    public void validateDescription(String description) throws Exception {
        if (description == null || description.trim().isEmpty()) {
            throw new Exception("Description cannot be empty. Please provide a complaint description.");
        }
    }

    /**
     * Check for existing unresolved complaints for the same bin (TC_10).
     */
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

    /**
     * Submit a complete overflowing bin report after all validations pass.
     * Main flow: validate bin -> check ward -> validate location -> validate fill level
     *            -> validate description -> check duplicate -> create report -> notify admin
     */
    public Reports submitReport(Citizen citizen, String binId, String location,
                                int fillLevel, String description) throws Exception {

        // Step 1: Validate Bin ID exists in the system
        Bin bin = validateBin(binId);

        // Step 2: Validate the bin is in the citizen's ward
        validateWard(bin, citizen);

        // Step 3: Validate location is not empty
        validateLocation(location);

        // Step 4: Validate fill level using IoT simulation
        validateFillLevel(fillLevel);

        // Step 5: Validate description is not empty
        validateDescription(description);

        // Step 6: Check for duplicate unresolved complaint
        checkDuplicateComplaint(binId, citizen.getUserId());

        // Step 7: Create the report entity
        Reports report = createReport(citizen.getUserId(), binId, location, fillLevel, description);

        // Step 8: Update the bin status via IoT simulation
        String binStatus = iotController.calculateBinStatus(fillLevel);
        bin.setFillLevel(fillLevel);
        bin.setStatus(binStatus);

        // Step 9: Notify admin about the new complaint
        notifyAdmin(report);

        return report;
    }

    /**
     * Create a new Reports entity and add it to the data store.
     */
    public Reports createReport(String citizenId, String binId, String location,
                                int fillLevel, String description) {
        String reportId = "RPT" + String.format("%03d", dataStore.getReports().size() + 1);
        Reports report = new Reports(reportId, citizenId, binId, location, fillLevel, description);
        report.createReport();
        dataStore.getReports().add(report);
        return report;
    }

    /**
     * Send notification to admin about a new complaint (TC_13).
     */
    private void notifyAdmin(Reports report) {
        String notification = "[ALERT] New overflowing bin complaint received! "
                + "Report ID: " + report.getReportId()
                + " | Bin: " + report.getBinId()
                + " | Location: " + report.getLocation()
                + " | Fill Level: " + report.getFillLevel() + "%";
        dataStore.getAdminNotifications().add(notification);
        System.out.println("\n  >> Admin has been notified about this complaint.");
    }

    /**
     * Get all reports submitted by a specific citizen.
     */
    public ArrayList<Reports> getReportsByCitizen(String citizenId) {
        ArrayList<Reports> citizenReports = new ArrayList<>();
        for (Reports report : dataStore.getReports()) {
            if (report.getCitizenId().equals(citizenId)) {
                citizenReports.add(report);
            }
        }
        return citizenReports;
    }

    /**
     * Get all reports in the system.
     */
    public ArrayList<Reports> getAllReports() {
        return dataStore.getReports();
    }
}
