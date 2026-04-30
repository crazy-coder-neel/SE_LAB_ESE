package com.smartwaste.boundary;

import com.smartwaste.control.ReportObinController;
import com.smartwaste.control.VehicleAssignmentController;
import com.smartwaste.entity.Reports;
import com.smartwaste.entity.Vehicle;
import com.smartwaste.main.DataStore;
import java.util.ArrayList;
import java.util.Scanner;


public class AdminUI {
    private Scanner scanner;
    private ReportObinController reportController;
    private VehicleAssignmentController vehicleController;
    private DataStore dataStore;

    public AdminUI(Scanner scanner) {
        this.scanner = scanner;
        this.reportController = new ReportObinController();
        this.vehicleController = new VehicleAssignmentController();
        this.dataStore = DataStore.getInstance();
    }

    
    public void displayComplaints() {
        System.out.println("\n=========================================");
        System.out.println("        ADMIN - ALL COMPLAINTS");
        System.out.println("=========================================");

        
        ArrayList<String> notifications = dataStore.getAdminNotifications();
        if (!notifications.isEmpty()) {
            System.out.println("\n  --- NOTIFICATIONS ---");
            for (String notification : notifications) {
                System.out.println("  " + notification);
            }
            System.out.println("  ---------------------\n");
        }

        
        ArrayList<Reports> reports = reportController.getAllReports();
        if (reports.isEmpty()) {
            System.out.println("  No complaints found in the system.");
        } else {
            System.out.println("  Total Complaints: " + reports.size());
            System.out.println("-----------------------------------------");
            for (Reports report : reports) {
                report.displayReport();
                System.out.println();
            }
        }
    }

    
    public void assignVehicleMenu() {
        System.out.println("\n=========================================");
        System.out.println("      ADMIN - ASSIGN VEHICLE");
        System.out.println("=========================================");

        
        ArrayList<Reports> reports = reportController.getAllReports();
        System.out.println("  Pending/Unassigned Reports:");
        boolean hasPending = false;
        for (Reports r : reports) {
            if (r.getComplaintStatus().equals("Pending")) {
                System.out.println("    Report ID: " + r.getReportId()
                        + " | Bin: " + r.getBinId()
                        + " | Location: " + r.getLocation());
                hasPending = true;
            }
        }
        if (!hasPending) {
            System.out.println("    No pending reports to assign.");
            return;
        }

        
        System.out.println("\n  Available Vehicles:");
        ArrayList<Vehicle> vehicles = dataStore.getVehicles();
        boolean hasAvailable = false;
        for (Vehicle v : vehicles) {
            if (v.isAvailability()) {
                System.out.println("    Vehicle ID: " + v.getVehicleId()
                        + " | Driver: " + (v.getDriverId() != null ? v.getDriverId() : "Unassigned"));
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("    No vehicles available.");
            return;
        }

        System.out.println("-----------------------------------------");
        System.out.print("  Enter Report ID  : ");
        String reportId = scanner.nextLine().trim();
        System.out.print("  Enter Vehicle ID : ");
        String vehicleId = scanner.nextLine().trim();

        try {
            Vehicle vehicle = vehicleController.assignVehicle(reportId, vehicleId);
            System.out.println("\n  [SUCCESS] Vehicle " + vehicle.getVehicleId()
                    + " assigned to Report " + reportId + " successfully!");
        } catch (Exception e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        }
    }

    
    public void updateStatusMenu() {
        System.out.println("\n=========================================");
        System.out.println("      ADMIN - UPDATE REPORT STATUS");
        System.out.println("=========================================");

        System.out.print("  Enter Report ID : ");
        String reportId = scanner.nextLine().trim();

        System.out.println("  Status Options: Pending | Assigned | In Progress | Resolved");
        System.out.print("  Enter New Status: ");
        String status = scanner.nextLine().trim();

        try {
            vehicleController.updateReportStatus(reportId, status);
            System.out.println("\n  [SUCCESS] Report " + reportId
                    + " status updated to '" + status + "'.");
        } catch (Exception e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        }
    }
}
