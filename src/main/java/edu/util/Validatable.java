package edu.ccrm.util;

/**
 * Interface for objects that can be validated
 * Demonstrates interface usage
 */
public interface Validatable {
    
    /**
     * Validate the object
     * @return true if the object is valid, false otherwise
     */
    boolean isValid();
    
    /**
     * Get validation error messages
     * @return list of validation error messages
     */
    java.util.List<String> getValidationErrors();
}
