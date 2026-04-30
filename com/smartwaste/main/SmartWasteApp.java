package com.smartwaste.main;

import com.smartwaste.boundary.*;
import com.smartwaste.entity.*;
import java.util.Scanner;

/**
 * SmartWasteApp - Main entry point for the Smart Waste Management System.
 * Implements CLI-based menu navigation for Citizen, Admin, and Driver roles.
 *
 * Architecture: Boundary-Control-Entity (BCE) Pattern
 * Use Case: Report Overflowing Bin
 *
 * System Flow:
 * 1. User logs in via LoginUI
 * 2. Role-based menu is displayed
 * 3. Citizen can report bins and view status
 * 4. Admin can view reports, assign vehicles, update status
 * 5. Driver can view tasks and mark them completed
 *
 * Pre-loaded Test Credentials:
 * - Citizen: citizen1/pass123 (Ward-A), citizen2/pass456 (Ward-B)
 * - Admin:   admin1/admin123
 * - Driver:  driver1/driver123, driver2/driver456
 *
 * Valid Bin IDs: BIN001, BIN002 (Ward-A), BIN003, BIN004 (Ward-B), BIN005 (Ward-A)
 * Valid Vehicle IDs: VH001, VH002, VH003
 */
public class SmartWasteApp {

    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    /**
     * Main method - application entry point.
     */
    public static void main(String[] args) {
        // Initialize DataStore (loads sample data)
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

    /**
     * Display the main menu (before login).
     * Enforces TC_02: Users must login before accessing any features.
     * @return false if user chooses to exit
     */
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

    /**
     * Display the Citizen menu after successful login.
     * Provides options to report overflowing bins and view complaint status.
     */
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

    /**
     * Display the Admin menu after successful login.
     * Provides options to view reports, assign vehicles, and update status.
     */
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

    /**
     * Display the Driver menu after successful login.
     * Provides options to view assigned tasks and mark them completed.
     */
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
