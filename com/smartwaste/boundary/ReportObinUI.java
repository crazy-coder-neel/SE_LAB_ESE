package com.smartwaste.boundary;

import com.smartwaste.control.ReportObinController;
import com.smartwaste.control.IoTController;
import com.smartwaste.entity.Citizen;
import com.smartwaste.entity.Reports;
import java.util.ArrayList;
import java.util.Scanner;


public class ReportObinUI {
    private Scanner scanner;
    private ReportObinController reportController;
    private IoTController iotController;

    public ReportObinUI(Scanner scanner) {
        this.scanner = scanner;
        this.reportController = new ReportObinController();
        this.iotController = new IoTController();
    }

    
    public void displayReportForm(Citizen citizen) {
        System.out.println("\n=========================================");
        System.out.println("      REPORT OVERFLOWING BIN FORM");
        System.out.println("=========================================");
        System.out.println("  Citizen : " + citizen.getUsername()
                + " | Ward : " + citizen.getWard());
        System.out.println("-----------------------------------------");

        
        String[] details = getBinDetails();
        if (details == null) return;

        String binId = details[0];
        String location = details[1];
        String fillLevelStr = details[2];
        String description = details[3];

        
        int fillLevel;
        try {
            fillLevel = Integer.parseInt(fillLevelStr);
        } catch (Exception e) {
            System.out.println("\n  [ERROR] Invalid fill level. Please enter a number between 0 and 100.");
            return;
        }

        
        String binStatus = iotController.calculateBinStatus(fillLevel);
        System.out.println("\n  [IoT Sensor Simulation]");
        System.out.println("  Fill Level : " + fillLevel + "%");
        System.out.println("  Bin Status : " + binStatus);

        
        try {
            Reports report = reportController.submitReport(citizen, binId, location,
                    fillLevel, description);
            System.out.println("\n  [SUCCESS] Complaint submitted successfully!");
            System.out.println("  Your Report ID: " + report.getReportId());
            System.out.println("-----------------------------------------");
            report.displayReport();
        } catch (Exception e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        }
    }

    
    public String[] getBinDetails() {
        System.out.print("  Enter Bin ID          : ");
        String binId = scanner.nextLine().trim();

        System.out.print("  Enter Bin Location    : ");
        String location = scanner.nextLine().trim();

        System.out.print("  Enter Fill Level (%)  : ");
        String fillLevel = scanner.nextLine().trim();

        System.out.print("  Enter Description     : ");
        String description = scanner.nextLine().trim();

        return new String[]{binId, location, fillLevel, description};
    }

    
    public void showReportStatus(Citizen citizen) {
        System.out.println("\n=========================================");
        System.out.println("        YOUR COMPLAINT STATUS");
        System.out.println("=========================================");

        ArrayList<Reports> reports = reportController.getReportsByCitizen(citizen.getUserId());

        if (reports.isEmpty()) {
            System.out.println("  No complaints found.");
        } else {
            System.out.println("  Total Complaints: " + reports.size());
            System.out.println("-----------------------------------------");
            for (Reports report : reports) {
                report.displayReport();
                System.out.println();
            }
        }
    }
}
