package com.petproject.tools.apmt.common.utils;

import java.io.Serializable;

public class NumericUtils {
	
    /**
     * Validates if input String is a number
     */
    public static boolean isNumber(Serializable in) {
        
        try {
            Float.parseFloat(in.toString());
        }
        catch (NumberFormatException ex) {
            return false;
        }
        
        return true;
    }
}