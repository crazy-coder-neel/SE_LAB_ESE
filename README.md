# Smart Waste Management System

## Report Overflowing Bin — CLI Application

A complete Java-based Command Line Interface (CLI) application implementing the **Report Overflowing Bin** use case using the **Boundary-Control-Entity (BCE)** architectural pattern.

---

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Package Structure](#package-structure)
- [Use Case: Report Overflowing Bin](#use-case-report-overflowing-bin)
- [Entity Classes](#entity-classes)
- [Control Classes](#control-classes)
- [Boundary Classes](#boundary-classes)
- [Validation Rules](#validation-rules)
- [Menu Flow](#menu-flow)
- [Test Credentials](#test-credentials)
- [How to Compile and Run](#how-to-compile-and-run)
- [Running Tests](#running-tests)
- [Test Cases Summary](#test-cases-summary)

---

## Overview

| Item | Detail |
|------|--------|
| **Project Type** | CLI Simulation (not real IoT) |
| **Architecture** | Boundary-Control-Entity (BCE) |
| **Language** | Java (OOP) |
| **Persistence** | In-memory using ArrayList |
| **Primary Use Case** | Report Overflowing Bin |
| **Primary Actor** | Citizen |
| **Supporting Actors** | Admin, Driver |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    BOUNDARY LAYER (CLI)                      │
│  LoginUI │ ReportObinUI │ AdminUI │ DriverUI                │
├─────────────────────────────────────────────────────────────┤
│                    CONTROL LAYER (Logic)                     │
│  LoginController │ ReportObinController │ IoTController      │
│  VehicleAssignmentController                                 │
├─────────────────────────────────────────────────────────────┤
│                    ENTITY LAYER (Data)                       │
│  User │ Citizen │ Admin │ Driver │ Bin │ Reports │ Vehicle   │
├─────────────────────────────────────────────────────────────┤
│                    DATA STORE (Persistence)                  │
│  DataStore (Singleton - ArrayList collections)               │
└─────────────────────────────────────────────────────────────┘
```

---

## Package Structure

```
com/smartwaste/
├── boundary/                          # CLI User Interface classes
│   ├── LoginUI.java                   # Login form display and input
│   ├── ReportObinUI.java              # Report overflowing bin form
│   ├── AdminUI.java                   # Admin dashboard
│   └── DriverUI.java                  # Driver task management
├── control/                           # Business logic classes
│   ├── LoginController.java           # Authentication logic
│   ├── ReportObinController.java      # Main report controller
│   ├── IoTController.java             # Simulated IoT sensor logic
│   └── VehicleAssignmentController.java # Vehicle management
├── entity/                            # Data/model classes
│   ├── User.java                      # Base user class
│   ├── Citizen.java                   # Citizen (extends User)
│   ├── Admin.java                     # Admin (extends User)
│   ├── Driver.java                    # Driver (extends User)
│   ├── Bin.java                       # Waste bin entity
│   ├── Reports.java                   # Complaint report entity
│   └── Vehicle.java                   # Collection vehicle entity
└── main/                              # Application entry points
    ├── SmartWasteApp.java             # Main interactive CLI app
    ├── SmartWasteTestRunner.java       # Automated test runner
    └── DataStore.java                 # In-memory data persistence
```

**Total: 17 Java source files across 4 packages**

---

## Use Case: Report Overflowing Bin

### Main Flow

1. Citizen logs in with valid credentials
2. `ReportObinUI` displays the report form
3. Citizen enters: Bin ID, Location, Fill Level (%), Description
4. `ReportObinController` validates all input fields
5. `IoTController` evaluates fill level (>= 80% = Overflow)
6. If valid, `Reports` entity is created with status "Pending"
7. Admin is notified via notification system
8. Admin assigns a collection vehicle to the report
9. Driver completes the collection task
10. Complaint status changes to "Resolved"
11. Citizen can view updated complaint status

### Precondition

- Citizen must log in successfully before reporting

---

## Entity Classes

### User (Base Class)
Fields: `userId`, `username`, `password`, `role`

### Citizen (extends User)
Additional field: `ward` — determines which bins the citizen can report

### Admin (extends User)
Can view reports, assign vehicles, update statuses

### Driver (extends User)
Additional field: `available` — can view assigned tasks, mark completed

### Bin
Fields: `binId`, `location`, `fillLevel`, `status`

### Reports (Main Entity)
Fields: `reportId`, `citizenId`, `binId`, `location`, `fillLevel`, `description`, `reportDate`, `complaintStatus`, `assignedVehicleId`

Status lifecycle: **Pending → Assigned → In Progress → Resolved**

### Vehicle
Fields: `vehicleId`, `driverId`, `availability`

---

## Control Classes

| Controller | Responsibility |
|------------|---------------|
| `LoginController` | Authenticate users by matching credentials |
| `ReportObinController` | Validate input, prevent duplicates, create reports, notify admin |
| `IoTController` | Simulate sensor logic: fillLevel >= 80 → "Overflow", else → "Empty" |
| `VehicleAssignmentController` | Assign vehicles, release vehicles, mark tasks complete |

---

## Boundary Classes

| Boundary | Responsibility |
|----------|---------------|
| `LoginUI` | Display login form, collect credentials |
| `ReportObinUI` | Display report form, collect bin details, show status |
| `AdminUI` | Display complaints, assign vehicles, update statuses |
| `DriverUI` | Display assigned tasks, mark tasks completed |

---

## Validation Rules

| Rule | Condition |
|------|-----------|
| Bin ID | Cannot be blank, must exist in system |
| Location | Cannot be blank |
| Fill Level | Must be 0–100, complaint only if >= 80% |
| Description | Cannot be empty |
| Ward Check | Citizen can only report bins in their own ward |
| Duplicate | Unresolved report for same bin by same citizen is blocked |

---

## Menu Flow

```
MAIN MENU
├── 1. Login
│   ├── Citizen Menu
│   │   ├── 1. Report Overflowing Bin
│   │   ├── 2. View Complaint Status
│   │   └── 3. Logout
│   ├── Admin Menu
│   │   ├── 1. View Reports
│   │   ├── 2. Assign Vehicle
│   │   ├── 3. Update Status
│   │   └── 4. Logout
│   └── Driver Menu
│       ├── 1. View Assigned Reports
│       ├── 2. Mark Completed
│       └── 3. Logout
└── 2. Exit
```

---

## Test Credentials

| Role | Username | Password | Ward |
|------|----------|----------|------|
| Citizen | `citizen1` | `pass123` | Ward-A |
| Citizen | `citizen2` | `pass456` | Ward-B |
| Citizen | `citizen3` | `pass789` | Ward-A |
| Admin | `admin1` | `admin123` | — |
| Driver | `driver1` | `driver123` | — |
| Driver | `driver2` | `driver456` | — |

**Valid Bin IDs:** BIN001 (Ward-A), BIN002 (Ward-A), BIN003 (Ward-B), BIN004 (Ward-B), BIN005 (Ward-A)

**Vehicle IDs:** VH001, VH002, VH003

---

## How to Compile and Run

### Prerequisites
- Java JDK 8 or higher installed

### Compile
```bash
cd "/home/exam1/Desktop/SE LAB ESE"
javac com/smartwaste/entity/*.java com/smartwaste/control/*.java com/smartwaste/boundary/*.java com/smartwaste/main/*.java
```

### Run Interactive Application
```bash
java com.smartwaste.main.SmartWasteApp
```

### Run Test Runner (with menu)
```bash
java com.smartwaste.main.SmartWasteTestRunner
```

---

## Running Tests

The `SmartWasteTestRunner` provides a menu-driven test interface:

```
==========================================================
  SMART WASTE MANAGEMENT SYSTEM - MAIN MENU
==========================================================
  1. Manual Operation (Interactive)
  2. Run Black Box Tests (15 cases)
  3. Run White Box Tests (15 cases)
  4. Run All Tests (30 cases)
  5. Exit
==========================================================
```

- **Option 2**: Runs all 15 Black Box test cases with formatted output
- **Option 3**: Runs all 15 White Box test cases with coverage type info
- **Option 4**: Runs all 30 tests (Black Box + White Box)

Each test displays: Input, Expected, Given (Actual), and Status (PASS/FAIL)

---

## Test Cases Summary

### Black Box Test Cases (15)

| ID | Description | Status |
|----|-------------|--------|
| BB-01 | Citizen reports overflowing bin after login | ✅ PASS |
| BB-02 | Report without login blocked | ✅ PASS |
| BB-03 | Valid bin ID accepted | ✅ PASS |
| BB-04 | Invalid bin ID rejected | ✅ PASS |
| BB-05 | Empty description rejected | ✅ PASS |
| BB-06 | Fill level ≥ 80% accepted | ✅ PASS |
| BB-07 | Fill level < 80% rejected | ✅ PASS |
| BB-08 | Own ward bin accepted | ✅ PASS |
| BB-09 | Other ward bin denied | ✅ PASS |
| BB-10 | Duplicate complaint prevented | ✅ PASS |
| BB-11 | Valid location accepted | ✅ PASS |
| BB-12 | Empty location rejected | ✅ PASS |
| BB-13 | Admin receives notification | ✅ PASS |
| BB-14 | Vehicle assigned successfully | ✅ PASS |
| BB-15 | Citizen sees Resolved status | ✅ PASS |

### White Box Test Cases (15)

| ID | Description | Coverage Type | Status |
|----|-------------|---------------|--------|
| WB-01 | LoginController valid path | Statement | ✅ PASS |
| WB-02 | Empty username branch | Statement | ✅ PASS |
| WB-03 | IoT boundary value 80 | Branch | ✅ PASS |
| WB-04 | IoT boundary value 79 | Branch | ✅ PASS |
| WB-05 | validateBin loop traversal | Path | ✅ PASS |
| WB-06 | Ward mismatch branch | Branch | ✅ PASS |
| WB-07 | Negative fill level | Branch | ✅ PASS |
| WB-08 | Fill level above 100 | Branch | ✅ PASS |
| WB-09 | Duplicate after Resolved | Path | ✅ PASS |
| WB-10 | Reports.createReport() | Statement | ✅ PASS |
| WB-11 | assignVehicle state update | Statement | ✅ PASS |
| WB-12 | markCompleted bin reset | Path | ✅ PASS |
| WB-13 | DataStore singleton | Branch | ✅ PASS |
| WB-14 | submitReport 9-step path | Path | ✅ PASS |
| WB-15 | Sequential ID generation | Statement | ✅ PASS |

**Overall: 30/30 PASSED**

---

## Java Features Used

- Object-Oriented Programming (Inheritance, Polymorphism)
- Constructors, Getters/Setters
- Method Overriding (`toString()`)
- ArrayList collections
- Scanner (CLI input)
- LocalDateTime (timestamps)
- Exception handling (try-catch)
- Singleton pattern (DataStore)

---

## Author

Software Engineering Lab — Academic Project
Architecture: Boundary-Control-Entity (BCE)
