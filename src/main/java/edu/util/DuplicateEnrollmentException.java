package edu.ccrm.util;

/**
 * Custom checked exception for duplicate enrollment scenarios
 */
public class DuplicateEnrollmentException extends Exception {
    
    /**
     * Constructor that accepts a message string
     * @param message the detail message
     */
    public DuplicateEnrollmentException(String message) {
        super(message);
    }
    
    /**
     * Constructor that accepts a message string and cause
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public DuplicateEnrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
