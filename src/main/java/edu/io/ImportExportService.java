package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Import/Export Service using NIO.2 and Java Streams
 * Demonstrates try-with-resources and modern file I/O
 */
public class ImportExportService {
    
    private static final String STUDENT_FILE = "students.csv";
    private static final String COURSE_FILE = "courses.csv";
    private static final String ENROLLMENT_FILE = "enrollments.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private final AppConfig config;
    
    public ImportExportService() {
        this.config = AppConfig.getInstance();
    }
    
    /**
     * Export students and courses data to CSV files in specified directory
     * Uses NIO.2 and try-with-resources
     * @param students list of students to export
     * @param courses list of courses to export
     * @param directory target directory for export files
     * @throws IOException if file operations fail
     */
    public void exportData(List<Student> students, List<Course> courses, Path directory) throws IOException {
        // Create directory if it doesn't exist
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        
        // Export students
        exportStudents(students, directory.resolve(STUDENT_FILE));
        
        // Export courses
        exportCourses(courses, directory.resolve(COURSE_FILE));
        
        System.out.println("Data exported successfully to: " + directory);
    }
    
    /**
     * Export students to CSV file
     * @param students list of students
     * @param filePath target file path
     * @throws IOException if writing fails
     */
    private void exportStudents(List<Student> students, Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        
        // CSV Header
        lines.add("ID,FullName,Email,RegNo,Status,RegistrationDate");
        
        // Student data using Java Streams
        students.stream()
               .map(student -> String.format("%d,%s,%s,%s,%s,%s",
                   student.getId(),
                   escapeCSV(student.getFullName()),
                   escapeCSV(student.getEmail()),
                   student.getRegNo(),
                   student.getStatus(),
                   student.getRegistrationDate().format(DATE_FORMATTER)))
               .forEach(lines::add);
        
        // Write to file using try-with-resources
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to export students: " + e.getMessage(), e);
        }
    }
    
    /**
     * Export courses to CSV file
     * @param courses list of courses
     * @param filePath target file path
     * @throws IOException if writing fails
     */
    private void exportCourses(List<Course> courses, Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        
        // CSV Header
        lines.add("CourseCode,Title,Credits,Department,Semester,InstructorID,InstructorName");
        
        // Course data using Java Streams
        courses.stream()
               .map(course -> String.format("%s,%s,%d,%s,%s,%d,%s",
                   course.getCourseCode(),
                   escapeCSV(course.getTitle()),
                   course.getCredits(),
                   escapeCSV(course.getDepartment()),
                   course.getSemester(),
                   course.getInstructor() != null ? course.getInstructor().getId() : 0,
                   course.getInstructor() != null ? escapeCSV(course.getInstructor().getFullName()) : "TBD"))
               .forEach(lines::add);
        
        // Write to file using try-with-resources
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to export courses: " + e.getMessage(), e);
        }
    }
    
    /**
     * Import students from CSV file using Java Streams and try-with-resources
     * @param filePath path to the CSV file
     * @return list of imported students
     * @throws IOException if reading fails
     */
    public List<Student> importStudents(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("Student file not found: " + filePath);
        }
        
        List<Student> students = new ArrayList<>();
        
        // Read and process lines using try-with-resources and Streams
        try (Stream<String> lines = Files.lines(filePath)) {
            students = lines.skip(1) // Skip header
                           .filter(line -> !line.trim().isEmpty())
                           .map(this::parseStudentFromCSV)
                           .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Failed to import students: " + e.getMessage(), e);
        }
        
        return students;
    }
    
    /**
     * Import courses from CSV file
     * @param filePath path to the CSV file
     * @return list of imported courses
     * @throws IOException if reading fails
     */
    public List<Course> importCourses(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("Course file not found: " + filePath);
        }
        
        List<Course> courses = new ArrayList<>();
        
        // Read and process lines using try-with-resources and Streams
        try (Stream<String> lines = Files.lines(filePath)) {
            courses = lines.skip(1) // Skip header
                          .filter(line -> !line.trim().isEmpty())
                          .map(this::parseCourseFromCSV)
                          .collect(java.util.stream.Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Failed to import courses: " + e.getMessage(), e);
        }
        
        return courses;
    }
    
    /**
     * Parse a student from CSV line
     * @param csvLine the CSV line
     * @return Student object
     */
    private Student parseStudentFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid student CSV format: " + csvLine);
        }
        
        Student student = new Student();
        student.setId(Long.parseLong(parts[0]));
        student.setFullName(unescapeCSV(parts[1]));
        student.setEmail(unescapeCSV(parts[2]));
        student.setRegNo(parts[3]);
        student.setStatus(StudentStatus.valueOf(parts[4]));
        student.setRegistrationDate(LocalDate.parse(parts[5], DATE_FORMATTER));
        
        return student;
    }
    
    /**
     * Parse a course from CSV line
     * @param csvLine the CSV line
     * @return Course object
     */
    private Course parseCourseFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        
        if (parts.length < 7) {
            throw new IllegalArgumentException("Invalid course CSV format: " + csvLine);
        }
        
        // Create instructor if provided
        Instructor instructor = null;
        long instructorId = Long.parseLong(parts[5]);
        if (instructorId > 0) {
            instructor = new Instructor();
            instructor.setId(instructorId);
            instructor.setFullName(unescapeCSV(parts[6]));
        }
        
        return new Course.Builder()
                .courseCode(parts[0])
                .title(unescapeCSV(parts[1]))
                .credits(Integer.parseInt(parts[2]))
                .department(unescapeCSV(parts[3]))
                .semester(Semester.valueOf(parts[4]))
                .instructor(instructor)
                .build();
    }
    
    /**
     * Escape CSV special characters
     * @param value the value to escape
     * @return escaped value
     */
    private String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
    
    /**
     * Unescape CSV special characters
     * @param value the value to unescape
     * @return unescaped value
     */
    private String unescapeCSV(String value) {
        if (value == null) return "";
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        return value;
    }
    
    /**
     * Get default export directory
     * @return default export directory path
     */
    public Path getDefaultExportDirectory() {
        return Paths.get(config.getDataPath());
    }
}
