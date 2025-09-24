package edu.ccrm.domain;

import edu.ccrm.util.Validatable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Student class extending Person - demonstrates Inheritance and Interface Implementation
 */
public class Student extends Person implements Validatable {
    
    private String regNo;
    private StudentStatus status;
    private List<Enrollment> enrolledCourses;
    private LocalDate registrationDate;
    
    // Default constructor
    public Student() {
        super();
        this.enrolledCourses = new ArrayList<>();
        this.status = StudentStatus.ACTIVE;
        this.registrationDate = LocalDate.now();
    }
    
    // Parameterized constructor
    public Student(long id, String fullName, String email, String regNo) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.enrolledCourses = new ArrayList<>();
        this.status = StudentStatus.ACTIVE;
        this.registrationDate = LocalDate.now();
    }
    
    // Getters and Setters
    public String getRegNo() {
        return regNo;
    }
    
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
    
    public StudentStatus getStatus() {
        return status;
    }
    
    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    
    public List<Enrollment> getEnrolledCourses() {
        return enrolledCourses;
    }
    
    public void setEnrolledCourses(List<Enrollment> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    // Implementation of abstract method from Person
    @Override
    public String getProfile() {
        return String.format("Student Profile - %s, Registration: %s, Status: %s, Courses: %d", 
                           super.toString(), regNo, status, enrolledCourses.size());
    }
    
    @Override
    public String toString() {
        return String.format("Student [%s, RegNo: %s, Status: %s, Registered: %s]", 
                           super.toString(), regNo, status, registrationDate);
    }
    
    // Implementation of Validatable interface
    @Override
    public boolean isValid() {
        List<String> errors = getValidationErrors();
        return errors.isEmpty();
    }
    
    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        
        if (getId() <= 0) {
            errors.add("Student ID must be positive");
        }
        if (getFullName() == null || getFullName().trim().isEmpty()) {
            errors.add("Student name cannot be empty");
        }
        if (getEmail() == null || !getEmail().contains("@")) {
            errors.add("Valid email is required");
        }
        if (regNo == null || regNo.trim().isEmpty()) {
            errors.add("Registration number cannot be empty");
        }
        if (registrationDate == null) {
            errors.add("Registration date cannot be null");
        } else if (registrationDate.isAfter(LocalDate.now())) {
            errors.add("Registration date cannot be in the future");
        }
        
        return errors;
    }
}
