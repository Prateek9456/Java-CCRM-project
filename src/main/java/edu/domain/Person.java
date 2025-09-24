package edu.ccrm.domain;

/**
 * Abstract Person class demonstrating Inheritance and Abstraction
 * Serves as base class for Student and Instructor
 */
public abstract class Person {
    
    // Private fields demonstrating Encapsulation
    private long id;
    private String fullName;
    private String email;
    
    // Default constructor
    public Person() {}
    
    // Parameterized constructor
    public Person(long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
    
    // Getters and Setters (Encapsulation)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Abstract method to be implemented by subclasses (Abstraction)
    public abstract String getProfile();
    
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s", id, fullName, email);
    }
}
