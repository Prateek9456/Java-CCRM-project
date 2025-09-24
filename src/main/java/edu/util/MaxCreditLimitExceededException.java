package edu.ccrm.util;

/**
 * Custom checked exception for credit limit exceeded scenarios
 */
public class MaxCreditLimitExceededException extends Exception {
    
    /**
     * Constructor that accepts a message string
     * @param message the detail message
     */
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }
    
    /**
     * Constructor that accepts a message string and cause
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public MaxCreditLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
