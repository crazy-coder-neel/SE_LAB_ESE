package com.smartwaste.boundary;

import com.smartwaste.control.VehicleAssignmentController;
import com.smartwaste.entity.Driver;
import com.smartwaste.entity.Reports;
import java.util.ArrayList;
import java.util.Scanner;


public class DriverUI {
    private Scanner scanner;
    private VehicleAssignmentController vehicleController;

    public DriverUI(Scanner scanner) {
        this.scanner = scanner;
        this.vehicleController = new VehicleAssignmentController();
    }

    
    public void displayAssignedTasks(Driver driver) {
        System.out.println("\n=========================================");
        System.out.println("      DRIVER - ASSIGNED TASKS");
        System.out.println("=========================================");
        System.out.println("  Driver: " + driver.getUsername()
                + " (ID: " + driver.getUserId() + ")");
        System.out.println("-----------------------------------------");

        ArrayList<Reports> tasks = vehicleController.getAssignedReports(driver.getUserId());

        if (tasks.isEmpty()) {
            System.out.println("  No assigned tasks at the moment.");
        } else {
            System.out.println("  Total Assigned Tasks: " + tasks.size());
            for (Reports report : tasks) {
                report.displayReport();
                System.out.println();
            }
        }
    }

    
    public void updateTaskStatus(Driver driver) {
        System.out.println("\n=========================================");
        System.out.println("    DRIVER - MARK TASK COMPLETED");
        System.out.println("=========================================");

        ArrayList<Reports> tasks = vehicleController.getAssignedReports(driver.getUserId());
        if (tasks.isEmpty()) {
            System.out.println("  No assigned tasks to complete.");
            return;
        }

        System.out.println("  Your Assigned Tasks:");
        for (Reports r : tasks) {
            System.out.println("    Report ID: " + r.getReportId()
                    + " | Bin: " + r.getBinId()
                    + " | Status: " + r.getComplaintStatus());
        }

        System.out.println("-----------------------------------------");
        System.out.print("  Enter Report ID to mark completed: ");
        String reportId = scanner.nextLine().trim();

        try {
            vehicleController.markCompleted(reportId);
            System.out.println("\n  [SUCCESS] Task " + reportId
                    + " marked as completed. Bin has been cleared.");
            System.out.println("  Complaint status updated to 'Resolved'.");
        } catch (Exception e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        }
    }
}
