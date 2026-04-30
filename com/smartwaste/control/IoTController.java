package com.smartwaste.control;

/**
 * IoTController - Simulates IoT sensor validation for bin monitoring.
 * This is NOT a real IoT system - it simulates sensor logic within the CLI.
 * Rule: fillLevel >= 80 => "Overflow", otherwise => "Empty"
 */
public class IoTController {

    /**
     * Calculate and return the bin status based on fill level.
     */
    public String calculateBinStatus(int fillLevel) {
        if (fillLevel >= 80) {
            return "Overflow";
        } else {
            return "Empty";
        }
    }

    /**
     * Check whether the bin is overflowing.
     */
    public boolean isOverflow(int fillLevel) {
        return fillLevel >= 80;
    }
}
