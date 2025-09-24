package edu.ccrm.domain;

/**
 * Instructor class extending Person - demonstrates Inheritance
 */
public class Instructor extends Person {
    
    private String department;
    
    // Default constructor
    public Instructor() {
        super();
    }
    
    // Parameterized constructor
    public Instructor(long id, String fullName, String email, String department) {
        super(id, fullName, email);
        this.department = department;
    }
    
    // Getter and Setter for department
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Implementation of abstract method from Person
    @Override
    public String getProfile() {
        return String.format("Instructor Profile - %s, Department: %s", 
                           super.toString(), department);
    }
    
    @Override
    public String toString() {
        return String.format("Instructor [%s, Department: %s]", 
                           super.toString(), department);
    }
}
