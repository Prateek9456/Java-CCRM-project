package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Enrollment class linking students to courses
 */
public class Enrollment {
    
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private Grade grade;
    
    // Default constructor
    public Enrollment() {
        this.enrollmentDate = LocalDate.now();
    }
    
    // Parameterized constructor
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.grade = null; // Grade assigned later
    }
    
    // Full constructor
    public Enrollment(Student student, Course course, LocalDate enrollmentDate, Grade grade) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.grade = grade;
    }
    
    // Getters and Setters
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public Grade getGrade() {
        return grade;
    }
    
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return String.format("Enrollment [Student: %s, Course: %s, Date: %s, Grade: %s]",
                           student != null ? student.getFullName() : "Unknown",
                           course != null ? course.getCourseCode() : "Unknown",
                           enrollmentDate,
                           grade != null ? grade : "Not Assigned");
    }
}
