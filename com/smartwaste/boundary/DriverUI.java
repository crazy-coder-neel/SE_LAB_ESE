package com.smartwaste.boundary;

import com.smartwaste.control.VehicleAssignmentController;
import com.smartwaste.entity.Driver;
import com.smartwaste.entity.Reports;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * DriverUI - Boundary class for the Driver dashboard.
 * Provides CLI interface for drivers to view assigned tasks
 * and mark bin collections as completed.
 */
public class DriverUI {
    private Scanner scanner;
    private VehicleAssignmentController vehicleController;

    public DriverUI(Scanner scanner) {
        this.scanner = scanner;
        this.vehicleController = new VehicleAssignmentController();
    }

    /**
     * Display all tasks assigned to the logged-in driver.
     */
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

    /**
     * Allow driver to mark a task as completed (TC_15).
     */
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
