package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Transcript Service for generating student transcripts
 * Demonstrates polymorphism through method overriding
 */
public class TranscriptService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Generate a comprehensive transcript for a student
     * @param student the student for whom to generate transcript
     * @return formatted transcript string
     */
    public String generateTranscript(Student student) {
        StringBuilder transcript = new StringBuilder();
        
        // Header
        transcript.append("=".repeat(60)).append("\n");
        transcript.append("OFFICIAL TRANSCRIPT\n");
        transcript.append("Campus Course Registration Management System\n");
        transcript.append("=".repeat(60)).append("\n\n");
        
        // Student Information - uses polymorphism (toString() override)
        transcript.append("STUDENT INFORMATION:\n");
        transcript.append("-".repeat(30)).append("\n");
        transcript.append("Student Profile: ").append(student.getProfile()).append("\n");
        transcript.append("Registration Date: ").append(student.getRegistrationDate().format(DATE_FORMATTER)).append("\n");
        transcript.append("Generated On: ").append(LocalDate.now().format(DATE_FORMATTER)).append("\n\n");
        
        // Enrolled Courses
        transcript.append("ENROLLED COURSES:\n");
        transcript.append("-".repeat(30)).append("\n");
        
        List<Enrollment> enrollments = student.getEnrolledCourses();
        if (enrollments.isEmpty()) {
            transcript.append("No courses enrolled.\n");
        } else {
            transcript.append(String.format("%-10s %-30s %-8s %-10s %-5s\n", 
                           "CODE", "TITLE", "CREDITS", "SEMESTER", "GRADE"));
            transcript.append("-".repeat(70)).append("\n");
            
            double totalGradePoints = 0.0;
            int totalCredits = 0;
            int gradedCredits = 0;
            
            for (Enrollment enrollment : enrollments) {
                String courseCode = enrollment.getCourse().getCourseCode();
                String title = enrollment.getCourse().getTitle();
                int credits = enrollment.getCourse().getCredits();
                String semester = enrollment.getCourse().getSemester().toString();
                String gradeStr = enrollment.getGrade() != null ? 
                                enrollment.getGrade().toString() : "IP"; // In Progress
                
                transcript.append(String.format("%-10s %-30s %-8d %-10s %-5s\n",
                               courseCode, title, credits, semester, gradeStr));
                
                totalCredits += credits;
                if (enrollment.getGrade() != null) {
                    totalGradePoints += enrollment.getGrade().getGradePoint() * credits;
                    gradedCredits += credits;
                }
            }
            
            transcript.append("-".repeat(70)).append("\n");
            
            // GPA Calculation
            double gpa = gradedCredits > 0 ? totalGradePoints / gradedCredits : 0.0;
            transcript.append(String.format("Total Credits Enrolled: %d\n", totalCredits));
            transcript.append(String.format("Graded Credits: %d\n", gradedCredits));
            transcript.append(String.format("Cumulative GPA: %.2f\n", gpa));
        }
        
        transcript.append("\n").append("=".repeat(60)).append("\n");
        transcript.append("End of Transcript\n");
        transcript.append("=".repeat(60)).append("\n");
        
        return transcript.toString();
    }
    
    /**
     * Calculate GPA for a student
     * @param student the student
     * @return calculated GPA
     */
    public double calculateGPA(Student student) {
        List<Enrollment> enrollments = student.getEnrolledCourses();
        
        double totalGradePoints = 0.0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                int credits = enrollment.getCourse().getCredits();
                totalGradePoints += enrollment.getGrade().getGradePoint() * credits;
                totalCredits += credits;
            }
        }
        
        return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }
    
    /**
     * Generate a summary transcript (shorter version)
     * @param student the student
     * @return summary transcript string
     */
    public String generateSummaryTranscript(Student student) {
        StringBuilder summary = new StringBuilder();
        
        summary.append("TRANSCRIPT SUMMARY\n");
        summary.append("-".repeat(20)).append("\n");
        summary.append("Student: ").append(student.getFullName()).append("\n");
        summary.append("Reg No: ").append(student.getRegNo()).append("\n");
        summary.append("Total Courses: ").append(student.getEnrolledCourses().size()).append("\n");
        summary.append("GPA: ").append(String.format("%.2f", calculateGPA(student))).append("\n");
        
        return summary.toString();
    }
}
