package com.smartwaste.main;

import com.smartwaste.boundary.*;
import com.smartwaste.entity.*;
import java.util.Scanner;


public class SmartWasteApp {

    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    
    public static void main(String[] args) {
        
        DataStore.getInstance();

        System.out.println("==============================================");
        System.out.println("    SMART WASTE MANAGEMENT SYSTEM v1.0");
        System.out.println("    Report Overflowing Bin - CLI Application");
        System.out.println("==============================================");
        System.out.println("  Architecture : Boundary-Control-Entity");
        System.out.println("  Use Case     : Report Overflowing Bin");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = showMainMenu();
            } else {
                switch (currentUser.getRole()) {
                    case "Citizen":
                        showCitizenMenu();
                        break;
                    case "Admin":
                        showAdminMenu();
                        break;
                    case "Driver":
                        showDriverMenu();
                        break;
                    default:
                        System.out.println("  [ERROR] Unknown role. Logging out.");
                        currentUser = null;
                }
            }
        }

        System.out.println("\n==============================================");
        System.out.println("  Thank you for using Smart Waste Management!");
        System.out.println("==============================================");
        scanner.close();
    }

    
    private static boolean showMainMenu() {
        System.out.println("\n=========================================");
        System.out.println("            MAIN MENU");
        System.out.println("=========================================");
        System.out.println("  1. Login");
        System.out.println("  2. Exit");
        System.out.println("=========================================");
        System.out.print("  Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                LoginUI loginUI = new LoginUI(scanner);
                currentUser = loginUI.getCredentials();
                return true;
            case "2":
                return false;
            default:
                System.out.println("  [ERROR] Invalid option. Please select 1 or 2.");
                return true;
        }
    }

    
    private static void showCitizenMenu() {
        Citizen citizen = (Citizen) currentUser;
        ReportObinUI reportUI = new ReportObinUI(scanner);

        System.out.println("\n=========================================");
        System.out.println("          CITIZEN MENU");
        System.out.println("=========================================");
        System.out.println("  Welcome, " + citizen.getUsername()
                + " | Ward: " + citizen.getWard());
        System.out.println("-----------------------------------------");
        System.out.println("  1. Report Overflowing Bin");
        System.out.println("  2. View Complaint Status");
        System.out.println("  3. Logout");
        System.out.println("=========================================");
        System.out.print("  Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                reportUI.displayReportForm(citizen);
                break;
            case "2":
                reportUI.showReportStatus(citizen);
                break;
            case "3":
                System.out.println("\n  [INFO] Logged out successfully.");
                currentUser = null;
                break;
            default:
                System.out.println("  [ERROR] Invalid option. Please select 1, 2, or 3.");
        }
    }

    
    private static void showAdminMenu() {
        AdminUI adminUI = new AdminUI(scanner);

        System.out.println("\n=========================================");
        System.out.println("           ADMIN MENU");
        System.out.println("=========================================");
        System.out.println("  Welcome, " + currentUser.getUsername());
        System.out.println("-----------------------------------------");
        System.out.println("  1. View Reports");
        System.out.println("  2. Assign Vehicle");
        System.out.println("  3. Update Status");
        System.out.println("  4. Logout");
        System.out.println("=========================================");
        System.out.print("  Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                adminUI.displayComplaints();
                break;
            case "2":
                adminUI.assignVehicleMenu();
                break;
            case "3":
                adminUI.updateStatusMenu();
                break;
            case "4":
                System.out.println("\n  [INFO] Logged out successfully.");
                currentUser = null;
                break;
            default:
                System.out.println("  [ERROR] Invalid option. Please select 1-4.");
        }
    }

    
    private static void showDriverMenu() {
        Driver driver = (Driver) currentUser;
        DriverUI driverUI = new DriverUI(scanner);

        System.out.println("\n=========================================");
        System.out.println("          DRIVER MENU");
        System.out.println("=========================================");
        System.out.println("  Welcome, " + driver.getUsername());
        System.out.println("-----------------------------------------");
        System.out.println("  1. View Assigned Reports");
        System.out.println("  2. Mark Completed");
        System.out.println("  3. Logout");
        System.out.println("=========================================");
        System.out.print("  Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                driverUI.displayAssignedTasks(driver);
                break;
            case "2":
                driverUI.updateTaskStatus(driver);
                break;
            case "3":
                System.out.println("\n  [INFO] Logged out successfully.");
                currentUser = null;
                break;
            default:
                System.out.println("  [ERROR] Invalid option. Please select 1, 2, or 3.");
        }
    }
}
