package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.StudentStatus;
import edu.ccrm.util.DuplicateEnrollmentException;
import edu.ccrm.util.MaxCreditLimitExceededException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enrollment Service for managing student course enrollments
 * Demonstrates custom exception handling
 */
public class EnrollmentService {
    
    private static final int MAX_CREDIT_LIMIT = 24; // Maximum credits per semester
    private List<Enrollment> enrollments;
    
    public EnrollmentService() {
        this.enrollments = new ArrayList<>();
    }
    
    /**
     * Enroll a student in a course with business logic validation
     * @param student the student to enroll
     * @param course the course to enroll in
     * @throws DuplicateEnrollmentException if student is already enrolled in the course
     * @throws MaxCreditLimitExceededException if enrollment exceeds credit limit
     */
    public void enrollStudent(Student student, Course course) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        // Assertions for debugging - enable with -ea
        assert student != null : "Student cannot be null";
        assert course != null : "Course cannot be null";
        assert course.getCredits() > 0 : "Course credits must be positive";
        
        // Check if student is active
        if (student.getStatus() != StudentStatus.ACTIVE) {
            throw new IllegalStateException("Cannot enroll inactive student: " + student.getRegNo());
        }
        
        // Check for duplicate enrollment
        if (isAlreadyEnrolled(student, course)) {
            throw new DuplicateEnrollmentException(
                String.format("Student %s is already enrolled in course %s", 
                            student.getRegNo(), course.getCourseCode()));
        }
        
        // Check credit limit
        int currentCredits = calculateCurrentCredits(student, course.getSemester());
        if (currentCredits + course.getCredits() > MAX_CREDIT_LIMIT) {
            throw new MaxCreditLimitExceededException(
                String.format("Enrollment would exceed maximum credit limit. Current: %d, Course: %d, Limit: %d",
                            currentCredits, course.getCredits(), MAX_CREDIT_LIMIT));
        }
        
        // Create and add enrollment
        Enrollment enrollment = new Enrollment(student, course);
        enrollments.add(enrollment);
        student.getEnrolledCourses().add(enrollment);
    }
    
    /**
     * Check if student is already enrolled in a course
     * @param student the student to check
     * @param course the course to check
     * @return true if already enrolled
     */
    private boolean isAlreadyEnrolled(Student student, Course course) {
        return enrollments.stream()
                         .anyMatch(enrollment -> 
                             enrollment.getStudent().getId() == student.getId() &&
                             enrollment.getCourse().getCourseCode().equals(course.getCourseCode()));
    }
    
    /**
     * Calculate current credits for a student in a specific semester
     * @param student the student
     * @param semester the semester
     * @return total credits enrolled
     */
    private int calculateCurrentCredits(Student student, edu.ccrm.domain.Semester semester) {
        return enrollments.stream()
                         .filter(enrollment -> 
                             enrollment.getStudent().getId() == student.getId() &&
                             enrollment.getCourse().getSemester() == semester)
                         .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                         .sum();
    }
    
    /**
     * Drop a student from a course
     * @param student the student
     * @param course the course
     * @return true if successfully dropped
     */
    public boolean dropCourse(Student student, Course course) {
        boolean removed = enrollments.removeIf(enrollment ->
            enrollment.getStudent().getId() == student.getId() &&
            enrollment.getCourse().getCourseCode().equals(course.getCourseCode()));
        
        if (removed) {
            student.getEnrolledCourses().removeIf(enrollment ->
                enrollment.getCourse().getCourseCode().equals(course.getCourseCode()));
        }
        
        return removed;
    }
    
    /**
     * Get all enrollments for a student
     * @param student the student
     * @return list of enrollments
     */
    public List<Enrollment> getStudentEnrollments(Student student) {
        return enrollments.stream()
                         .filter(enrollment -> enrollment.getStudent().getId() == student.getId())
                         .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Get all enrollments for a course
     * @param course the course
     * @return list of enrollments
     */
    public List<Enrollment> getCourseEnrollments(Course course) {
        return enrollments.stream()
                         .filter(enrollment -> 
                             enrollment.getCourse().getCourseCode().equals(course.getCourseCode()))
                         .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Get all enrollments
     * @return list of all enrollments
     */
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
    
    /**
     * Get maximum credit limit
     * @return maximum credit limit
     */
    public static int getMaxCreditLimit() {
        return MAX_CREDIT_LIMIT;
    }
    
    /**
     * Get allowed credit values using Arrays utility
     * @return sorted array of common credit values
     */
    public int[] getAllowedCreditValues() {
        int[] credits = {1, 2, 3, 4, 5, 6};
        Arrays.sort(credits); // Using Arrays utility
        return credits;
    }
    
    /**
     * Check if credit value is allowed using Arrays.binarySearch
     * @param credits the credit value to check
     * @return true if credit value is allowed
     */
    public boolean isCreditValueAllowed(int credits) {
        int[] allowedCredits = getAllowedCreditValues();
        return Arrays.binarySearch(allowedCredits, credits) >= 0; // Using Arrays utility
    }
}
