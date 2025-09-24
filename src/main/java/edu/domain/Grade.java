package edu.ccrm.domain;

/**
 * Grade enum with corresponding grade point values
 */
public enum Grade {
    S(10.0), A(9.0), B(8.0), C(7.0), D(6.0), F(0.0);
    
    private final double gradePoint;
    
    // Constructor
    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }
    
    // Getter for grade point
    public double getGradePoint() {
        return gradePoint;
    }
}
