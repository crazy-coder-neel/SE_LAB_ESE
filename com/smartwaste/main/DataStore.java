package com.smartwaste.main;

import com.smartwaste.entity.*;
import java.util.ArrayList;

/**
 * DataStore - Singleton class for in-memory data persistence.
 * Stores all entity data using ArrayList collections.
 * Pre-loaded with sample data for testing and demonstration.
 *
 * This simulates a database using in-memory collections.
 * All controllers access data through this centralized store.
 */
public class DataStore {
    // Singleton instance
    private static DataStore instance;

    // In-memory data collections
    private ArrayList<User> users;
    private ArrayList<Bin> bins;
    private ArrayList<Reports> reports;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<String> adminNotifications;

    /**
     * Private constructor - initializes all collections and loads sample data.
     */
    private DataStore() {
        users = new ArrayList<>();
        bins = new ArrayList<>();
        reports = new ArrayList<>();
        vehicles = new ArrayList<>();
        adminNotifications = new ArrayList<>();
        loadSampleData();
    }

    /**
     * Get the singleton instance of DataStore.
     * @return the DataStore instance
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    /**
     * Load pre-configured sample data for testing.
     * Includes users (citizens, admin, driver), bins across wards,
     * and available vehicles.
     */
    private void loadSampleData() {
        // ========== USERS ==========
        // Citizens - each assigned to a specific ward
        users.add(new Citizen("C001", "citizen1", "pass123", "Ward-A"));
        users.add(new Citizen("C002", "citizen2", "pass456", "Ward-B"));
        users.add(new Citizen("C003", "citizen3", "pass789", "Ward-A"));

        // Admin
        users.add(new Admin("A001", "admin1", "admin123"));

        // Drivers
        users.add(new Driver("D001", "driver1", "driver123"));
        users.add(new Driver("D002", "driver2", "driver456"));

        // ========== BINS ==========
        // Bins distributed across different wards
        bins.add(new Bin("BIN001", "Ward-A", 0, "Empty"));
        bins.add(new Bin("BIN002", "Ward-A", 0, "Empty"));
        bins.add(new Bin("BIN003", "Ward-B", 0, "Empty"));
        bins.add(new Bin("BIN004", "Ward-B", 0, "Empty"));
        bins.add(new Bin("BIN005", "Ward-A", 0, "Empty"));

        // ========== VEHICLES ==========
        // Collection vehicles assigned to drivers
        vehicles.add(new Vehicle("VH001", "D001", true));
        vehicles.add(new Vehicle("VH002", "D002", true));
        vehicles.add(new Vehicle("VH003", null, true));
    }

    // ========== GETTERS ==========

    public ArrayList<User> getUsers() { return users; }

    public ArrayList<Bin> getBins() { return bins; }

    public ArrayList<Reports> getReports() { return reports; }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    public ArrayList<String> getAdminNotifications() { return adminNotifications; }
}
