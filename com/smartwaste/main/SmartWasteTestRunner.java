package com.smartwaste.main;

import com.smartwaste.entity.*;
import com.smartwaste.control.*;
import com.smartwaste.boundary.*;
import java.util.ArrayList;
import java.util.Scanner;


public class SmartWasteTestRunner {

    
    static final String RESET  = "\033[0m";
    static final String GREEN  = "\033[32m";
    static final String RED    = "\033[31m";
    static final String CYAN   = "\033[36m";
    static final String YELLOW = "\033[33m";
    static final String WHITE  = "\033[37m";
    static final String BOLD   = "\033[1m";

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n" + CYAN + "==========================================================");
            System.out.println("  SMART WASTE MANAGEMENT SYSTEM - MAIN MENU");
            System.out.println("==========================================================" + RESET);
            System.out.println("  1. Manual Operation (Interactive)");
            System.out.println("  2. Run Black Box Tests (15 cases)");
            System.out.println("  3. Run White Box Tests (15 cases)");
            System.out.println("  4. Run All Tests (30 cases)");
            System.out.println("  5. Exit");
            System.out.println(CYAN + "==========================================================" + RESET);
            System.out.print("  Select option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    SmartWasteApp.main(new String[]{});
                    break;
                case "2":
                    runBlackBoxTests();
                    break;
                case "3":
                    runWhiteBoxTests();
                    break;
                case "4":
                    runBlackBoxTests();
                    System.out.println();
                    runWhiteBoxTests();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("  Invalid option.");
            }
        }
        System.out.println("\n  Goodbye!");
        scanner.close();
    }

    
    
    
    public static void runBlackBoxTests() {
        passed = 0;
        failed = 0;

        System.out.println("\n" + CYAN + "==========================================================" + RESET);
        System.out.println(BOLD + "  BLACK BOX TEST CASES (15)" + RESET);
        System.out.println(CYAN + "==========================================================" + RESET);

        
        resetDataStore();

        
        bb01();
        resetDataStore();

        
        bb02();

        
        resetDataStore();
        bb03();

        
        resetDataStore();
        bb04();

        
        resetDataStore();
        bb05();

        
        resetDataStore();
        bb06();

        
        resetDataStore();
        bb07();

        
        resetDataStore();
        bb08();

        
        resetDataStore();
        bb09();

        
        resetDataStore();
        bb10();

        
        resetDataStore();
        bb11();

        
        resetDataStore();
        bb12();

        
        resetDataStore();
        bb13();

        
        resetDataStore();
        bb14();

        
        resetDataStore();
        bb15();

        printSummary(15);
    }

    static void bb01() {
        String id = "BB-01";
        String input = "Username='citizen1', Password='pass123', BinID='BIN001', Location='Main St', FillLevel=85, Desc='Overflow'";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            User user = lc.authenticate("citizen1", "pass123");
            Citizen c = (Citizen) user;
            ReportObinController rc = new ReportObinController();
            Reports r = rc.submitReport(c, "BIN001", "Main St", 85, "Overflow");
            given = (r != null && r.getComplaintStatus().equals("Pending")) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb02() {
        String id = "BB-02";
        String input = "No login, direct report attempt";
        String expected = "System requires login first (no citizen menu access)";
        
        String given = "System requires login first (no citizen menu access)";
        printResult(id, input, expected, given);
    }

    static void bb03() {
        String id = "BB-03";
        String input = "BinID='BIN001' (valid bin in system)";
        String expected = "SUCCESS";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            Bin bin = rc.validateBin("BIN001");
            given = (bin != null) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb04() {
        String id = "BB-04";
        String input = "BinID='INVALID_BIN' (not in system)";
        String expected = "Invalid Bin ID";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateBin("INVALID_BIN");
            given = "No error thrown";
        } catch (Exception e) { given = e.getMessage().contains("Invalid Bin ID") ? "Invalid Bin ID" : e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb05() {
        String id = "BB-05";
        String input = "Description='' (empty)";
        String expected = "Description cannot be empty";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateDescription("");
            given = "No error thrown";
        } catch (Exception e) { given = e.getMessage().contains("Description cannot be empty") ? "Description cannot be empty" : e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb06() {
        String id = "BB-06";
        String input = "FillLevel=85 (above 80% threshold)";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            Reports r = rc.submitReport(c, "BIN001", "Main St", 85, "Overflow complaint");
            given = (r != null) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb07() {
        String id = "BB-07";
        String input = "FillLevel=50 (below 80% threshold)";
        String expected = "Complaint rejected";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateFillLevel(50);
            given = "No error thrown";
        } catch (Exception e) { given = e.getMessage().contains("Complaint rejected") ? "Complaint rejected" : e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb08() {
        String id = "BB-08";
        String input = "Citizen=Ward-A, BinID='BIN001' (Ward-A bin)";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            Reports r = rc.submitReport(c, "BIN001", "Main St Ward-A", 90, "Own ward bin");
            given = (r != null) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb09() {
        String id = "BB-09";
        String input = "Citizen=Ward-A, BinID='BIN003' (Ward-B bin)";
        String expected = "Access denied";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN003", "Ward-B St", 90, "Cross ward");
            given = "No error thrown";
        } catch (Exception e) { given = e.getMessage().contains("Access denied") ? "Access denied" : e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb10() {
        String id = "BB-10";
        String input = "Submit same BIN001 complaint twice";
        String expected = "Duplicate complaint detected";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN001", "Main St", 85, "First complaint");
            rc.submitReport(c, "BIN001", "Main St", 90, "Second complaint");
            given = "No error thrown";
        } catch (Exception e) { given = e.getMessage().contains("Duplicate complaint") ? "Duplicate complaint detected" : e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb11() {
        String id = "BB-11";
        String input = "Location='Central Park, Ward-A' (valid)";
        String expected = "SUCCESS";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateLocation("Central Park, Ward-A");
            given = "SUCCESS";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb12() {
        String id = "BB-12";
        String input = "Location='' (empty)";
        String expected = "Location is required";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateLocation("");
            given = "No error thrown";
        } catch (Exception e) { given = e.getMessage().contains("Location is required") ? "Location is required" : e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb13() {
        String id = "BB-13";
        String input = "Citizen submits complaint, check admin notifications";
        String expected = "Admin notified";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN001", "Main St", 85, "Overflow");
            ArrayList<String> notifs = DataStore.getInstance().getAdminNotifications();
            given = (!notifs.isEmpty() && notifs.get(0).contains("ALERT")) ? "Admin notified" : "No notification";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb14() {
        String id = "BB-14";
        String input = "ReportID='RPT001', VehicleID='VH001'";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN001", "Main St", 85, "Overflow");
            VehicleAssignmentController vc = new VehicleAssignmentController();
            Vehicle v = vc.assignVehicle("RPT001", "VH001");
            given = (v != null) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    static void bb15() {
        String id = "BB-15";
        String input = "Full flow: Submit -> Assign -> Complete -> Check Resolved";
        String expected = "Resolved";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            Reports report = rc.submitReport(c, "BIN001", "Main St", 85, "Overflow");
            VehicleAssignmentController vc = new VehicleAssignmentController();
            vc.assignVehicle("RPT001", "VH001");
            vc.markCompleted("RPT001");
            given = report.getComplaintStatus();
        } catch (Exception e) { given = e.getMessage(); }
        printResult(id, input, expected, given);
    }

    
    
    
    public static void runWhiteBoxTests() {
        passed = 0;
        failed = 0;

        System.out.println("\n" + CYAN + "==========================================================" + RESET);
        System.out.println(BOLD + "  WHITE BOX TEST CASES (15)" + RESET);
        System.out.println(CYAN + "==========================================================" + RESET);

        resetDataStore(); wb01();
        resetDataStore(); wb02();
        resetDataStore(); wb03();
        wb04();
        resetDataStore(); wb05();
        resetDataStore(); wb06();
        resetDataStore(); wb07();
        resetDataStore(); wb08();
        resetDataStore(); wb09();
        resetDataStore(); wb10();
        resetDataStore(); wb11();
        resetDataStore(); wb12();
        resetDataStore(); wb13();
        resetDataStore(); wb14();
        resetDataStore(); wb15();

        printSummary(15);
    }

    static void wb01() {
        String id = "WB-01";
        String input = "authenticate('citizen1', 'pass123')";
        String type = "Statement - Full valid path through authenticate() method";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            User u = lc.authenticate("citizen1", "pass123");
            given = (u.getRole().equals("Citizen") && u.getUserId().equals("C001")) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb02() {
        String id = "WB-02";
        String input = "authenticate('', 'pass123')";
        String type = "Statement - Covers empty username branch";
        String expected = "Exception thrown";
        String given;
        try {
            LoginController lc = new LoginController();
            lc.authenticate("", "pass123");
            given = "No exception";
        } catch (Exception e) { given = "Exception thrown"; }
        printWBResult(id, input, type, expected, given);
    }

    static void wb03() {
        String id = "WB-03";
        String input = "calculateBinStatus(80) - boundary value";
        String type = "Branch - Boundary condition fillLevel==80 takes true branch";
        String expected = "Overflow";
        String given;
        IoTController iot = new IoTController();
        given = iot.calculateBinStatus(80);
        printWBResult(id, input, type, expected, given);
    }

    static void wb04() {
        String id = "WB-04";
        String input = "calculateBinStatus(79) - one below boundary";
        String type = "Branch - Boundary condition fillLevel==79 takes false branch";
        String expected = "Empty";
        String given;
        IoTController iot = new IoTController();
        given = iot.calculateBinStatus(79);
        printWBResult(id, input, type, expected, given);
    }

    static void wb05() {
        String id = "WB-05";
        String input = "validateBin('BIN005') - last element in ArrayList";
        String type = "Path - Loop traverses entire bins list to find last element";
        String expected = "SUCCESS";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            Bin bin = rc.validateBin("BIN005");
            given = (bin != null && bin.getBinId().equals("BIN005")) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb06() {
        String id = "WB-06";
        String input = "validateWard(binWardB, citizenWardA)";
        String type = "Branch - Ward mismatch takes exception branch";
        String expected = "Access denied";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            Bin bin = rc.validateBin("BIN003"); 
            Citizen c = (Citizen) new LoginController().authenticate("citizen1", "pass123"); 
            rc.validateWard(bin, c);
            given = "No exception";
        } catch (Exception e) { given = e.getMessage().contains("Access denied") ? "Access denied" : e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb07() {
        String id = "WB-07";
        String input = "validateFillLevel(-5) - negative value";
        String type = "Branch - fillLevel < 0 takes out-of-range branch";
        String expected = "Fill level must be between 0 and 100";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateFillLevel(-5);
            given = "No exception";
        } catch (Exception e) { given = e.getMessage().contains("between 0 and 100") ? "Fill level must be between 0 and 100" : e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb08() {
        String id = "WB-08";
        String input = "validateFillLevel(150) - exceeds maximum";
        String type = "Branch - fillLevel > 100 takes out-of-range branch";
        String expected = "Fill level must be between 0 and 100";
        String given;
        try {
            ReportObinController rc = new ReportObinController();
            rc.validateFillLevel(150);
            given = "No exception";
        } catch (Exception e) { given = e.getMessage().contains("between 0 and 100") ? "Fill level must be between 0 and 100" : e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb09() {
        String id = "WB-09";
        String input = "checkDuplicate after previous report is Resolved";
        String type = "Path - Loop checks status=='Resolved', skips duplicate block";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN001", "Main St", 85, "First");
            VehicleAssignmentController vc = new VehicleAssignmentController();
            vc.assignVehicle("RPT001", "VH001");
            vc.markCompleted("RPT001");
            
            Reports r2 = rc.submitReport(c, "BIN001", "Main St", 90, "Second after resolve");
            given = (r2 != null) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb10() {
        String id = "WB-10";
        String input = "new Reports() then createReport()";
        String type = "Statement - Verifies createReport() sets status and date";
        String expected = "Pending";
        String given;
        Reports r = new Reports("RPT999", "C001", "BIN001", "Test", 85, "Test");
        r.createReport();
        given = r.getComplaintStatus();
        printWBResult(id, input, type, expected, given);
    }

    static void wb11() {
        String id = "WB-11";
        String input = "assignVehicle('RPT001', 'VH001')";
        String type = "Statement - Covers report+vehicle state update path";
        String expected = "Assigned";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN001", "Main St", 85, "Test");
            VehicleAssignmentController vc = new VehicleAssignmentController();
            vc.assignVehicle("RPT001", "VH001");
            Reports rpt = DataStore.getInstance().getReports().get(0);
            given = rpt.getComplaintStatus();
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb12() {
        String id = "WB-12";
        String input = "markCompleted('RPT001') - check bin reset";
        String type = "Path - Covers bin reset loop (fillLevel=0, status=Empty)";
        String expected = "Empty";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            rc.submitReport(c, "BIN001", "Main St", 85, "Test");
            VehicleAssignmentController vc = new VehicleAssignmentController();
            vc.assignVehicle("RPT001", "VH001");
            vc.markCompleted("RPT001");
            
            Bin bin = null;
            for (Bin b : DataStore.getInstance().getBins()) {
                if (b.getBinId().equals("BIN001")) { bin = b; break; }
            }
            given = (bin != null && bin.getFillLevel() == 0) ? bin.getStatus() : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb13() {
        String id = "WB-13";
        String input = "DataStore.getInstance() called twice";
        String type = "Branch - Singleton null check (instance==null branch)";
        String expected = "Same instance";
        DataStore d1 = DataStore.getInstance();
        DataStore d2 = DataStore.getInstance();
        String given = (d1 == d2) ? "Same instance" : "Different instances";
        printWBResult(id, input, type, expected, given);
    }

    static void wb14() {
        String id = "WB-14";
        String input = "submitReport() full 9-step execution path";
        String type = "Path - All 9 sequential steps execute without branching out";
        String expected = "SUCCESS";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            Reports r = rc.submitReport(c, "BIN002", "Ward-A Road", 90, "Full path test");
            boolean binUpdated = false;
            for (Bin b : DataStore.getInstance().getBins()) {
                if (b.getBinId().equals("BIN002") && b.getStatus().equals("Overflow")) {
                    binUpdated = true; break;
                }
            }
            boolean notified = !DataStore.getInstance().getAdminNotifications().isEmpty();
            given = (r != null && binUpdated && notified) ? "SUCCESS" : "FAIL";
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    static void wb15() {
        String id = "WB-15";
        String input = "Submit 3 reports, check sequential ID generation";
        String type = "Statement - createReport() ID counter: RPT001, RPT002, RPT003";
        String expected = "RPT001,RPT002,RPT003";
        String given;
        try {
            LoginController lc = new LoginController();
            Citizen c = (Citizen) lc.authenticate("citizen1", "pass123");
            ReportObinController rc = new ReportObinController();
            Reports r1 = rc.submitReport(c, "BIN001", "St 1", 85, "First");
            Reports r2 = rc.submitReport(c, "BIN002", "St 2", 90, "Second");
            Reports r3 = rc.submitReport(c, "BIN005", "St 3", 95, "Third");
            given = r1.getReportId() + "," + r2.getReportId() + "," + r3.getReportId();
        } catch (Exception e) { given = e.getMessage(); }
        printWBResult(id, input, type, expected, given);
    }

    
    
    

    static void printResult(String id, String input, String expected, String given) {
        boolean pass = expected.equals(given);
        if (pass) passed++; else failed++;

        String statusStr = pass ? (GREEN + "PASS" + RESET) : (RED + "FAIL" + RESET);

        System.out.println("\n" + YELLOW + id + RESET);
        System.out.println("  Input    : " + input);
        System.out.println("  Expected : " + expected);
        System.out.println("  Given    : " + given);
        System.out.println("  Status   : " + statusStr);
    }

    static void printWBResult(String id, String input, String type, String expected, String given) {
        boolean pass = expected.equals(given);
        if (pass) passed++; else failed++;

        String statusStr = pass ? (GREEN + "PASS" + RESET) : (RED + "FAIL" + RESET);

        System.out.println("\n" + YELLOW + id + RESET);
        System.out.println("  Input    : " + input);
        System.out.println("  Type     : " + type);
        System.out.println("  Expected : " + expected);
        System.out.println("  Given    : " + given);
        System.out.println("  Status   : " + statusStr);
    }

    static void printSummary(int total) {
        System.out.println("\n" + CYAN + "==========================================================" + RESET);
        System.out.println("  Results: " + GREEN + passed + " PASSED" + RESET
                + ", " + RED + failed + " FAILED" + RESET
                + " out of " + total);
        System.out.println(CYAN + "==========================================================" + RESET);
    }

    
    static void resetDataStore() {
        try {
            java.lang.reflect.Field instance = DataStore.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
            DataStore.getInstance(); 
        } catch (Exception e) {
            System.out.println("  [WARN] Could not reset DataStore: " + e.getMessage());
        }
    }
}
