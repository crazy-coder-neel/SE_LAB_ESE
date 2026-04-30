package com.smartwaste.control;


public class IoTController {

    
    public String calculateBinStatus(int fillLevel) {
        if (fillLevel >= 80) {
            return "Overflow";
        } else {
            return "Empty";
        }
    }

    
    public boolean isOverflow(int fillLevel) {
        return fillLevel >= 80;
    }
}
