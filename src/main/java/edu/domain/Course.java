package edu.ccrm.domain;

/**
 * Course class implementing Builder Design Pattern
 * Demonstrates nested class and builder pattern
 */
public class Course {
    
    private final String courseCode;
    private final String title;
    private final int credits;
    private final Instructor instructor;
    private final Semester semester;
    private final String department;
    
    // Private constructor - can only be accessed through Builder
    private Course(Builder builder) {
        this.courseCode = builder.courseCode;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
    }
    
    // Getters (no setters as fields are final - immutable object)
    public String getCourseCode() {
        return courseCode;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public Instructor getInstructor() {
        return instructor;
    }
    
    public Semester getSemester() {
        return semester;
    }
    
    public String getDepartment() {
        return department;
    }
    
    @Override
    public String toString() {
        return String.format("Course [%s: %s, Credits: %d, Department: %s, Semester: %s, Instructor: %s]",
                           courseCode, title, credits, department, semester, 
                           instructor != null ? instructor.getFullName() : "TBD");
    }
    
    /**
     * Static nested Builder class implementing Builder Design Pattern
     */
    public static class Builder {
        private String courseCode;
        private String title;
        private int credits;
        private Instructor instructor;
        private Semester semester;
        private String department;
        
        public Builder courseCode(String courseCode) {
            this.courseCode = courseCode;
            return this;
        }
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }
        
        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        
        public Course build() {
            return new Course(this);
        }
    }
}
