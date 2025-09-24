package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.*;
import edu.ccrm.util.DuplicateEnrollmentException;
import edu.ccrm.util.MaxCreditLimitExceededException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * CCRM Application - Campus Course Registration Management
 * Main entry point for the application with CLI interface
 * Demonstrates loops, switch statements, and jump controls
 */
public class CCRMApp {
    
    // Service instances
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final TranscriptService transcriptService = new TranscriptService();
    private static final ImportExportService importExportService = new ImportExportService();
    private static final BackupService backupService = new BackupService();
    
    // Application configuration
    private static final AppConfig config = AppConfig.getInstance();
    
    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Main method - entry point of the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("CCRM Application Started!");
        System.out.println("=".repeat(50));
        System.out.println(config.getAppName() + " v" + config.getAppVersion());
        System.out.println("=".repeat(50));
        
        // Initialize sample data
        initializeSampleData();
        
        // Main application loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    handleStudentManagement();
                    break;
                case 2:
                    handleCourseManagement();
                    break;
                case 3:
                    handleEnrollmentManagement();
                    break;
                case 4:
                    handleImportExport();
                    break;
                case 5:
                    handleBackup();
                    break;
                case 6:
                    handleTranscripts();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using CCRM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Import/Export Data");
        System.out.println("5. Backup Operations");
        System.out.println("6. Generate Transcripts");
        System.out.println("7. Exit");
        System.out.println("=".repeat(40));
    }
    
    /**
     * Handle student management operations
     */
    private static void handleStudentManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. List All Students");
            System.out.println("3. Find Student");
            System.out.println("4. Update Student");
            System.out.println("5. Remove Student");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listAllStudents();
                    break;
                case 3:
                    findStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Handle course management operations
     */
    private static void handleCourseManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. List All Courses");
            System.out.println("3. Find Course");
            System.out.println("4. Search Courses by Department");
            System.out.println("5. Search Courses by Semester");
            System.out.println("6. Remove Course");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    listAllCourses();
                    break;
                case 3:
                    findCourse();
                    break;
                case 4:
                    searchCoursesByDepartment();
                    break;
                case 5:
                    searchCoursesBySemester();
                    break;
                case 6:
                    removeCourse();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Handle enrollment management operations
     */
    private static void handleEnrollmentManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Drop Student from Course");
            System.out.println("3. Assign Grade");
            System.out.println("4. View Student Enrollments");
            System.out.println("5. View Course Enrollments");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    enrollStudentInCourse();
                    break;
                case 2:
                    dropStudentFromCourse();
                    break;
                case 3:
                    assignGrade();
                    break;
                case 4:
                    viewStudentEnrollments();
                    break;
                case 5:
                    viewCourseEnrollments();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Handle import/export operations
     */
    private static void handleImportExport() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Import/Export Operations ---");
            System.out.println("1. Export Data");
            System.out.println("2. Import Students");
            System.out.println("3. Import Courses");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    exportData();
                    break;
                case 2:
                    importStudents();
                    break;
                case 3:
                    importCourses();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Handle backup operations
     */
    private static void handleBackup() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Backup Operations ---");
            System.out.println("1. Create Backup");
            System.out.println("2. List Backups");
            System.out.println("3. Cleanup Old Backups");
            System.out.println("4. Calculate Backup Size");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createBackup();
                    break;
                case 2:
                    listBackups();
                    break;
                case 3:
                    cleanupBackups();
                    break;
                case 4:
                    calculateBackupSize();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Handle transcript operations
     */
    private static void handleTranscripts() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Transcript Operations ---");
            System.out.println("1. Generate Full Transcript");
            System.out.println("2. Generate Summary Transcript");
            System.out.println("3. Calculate Student GPA");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    generateFullTranscript();
                    break;
                case 2:
                    generateSummaryTranscript();
                    break;
                case 3:
                    calculateStudentGPA();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * Add a new student
     */
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        long id = getIntInput("Enter Student ID: ");
        String fullName = getStringInput("Enter Full Name: ");
        String email = getStringInput("Enter Email: ");
        String regNo = getStringInput("Enter Registration Number: ");
        
        Student student = new Student(id, fullName, email, regNo);
        
        if (studentService.addStudent(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student. Student may already exist.");
        }
    }
    
    /**
     * List all students - demonstrates enhanced for loop and continue
     */
    private static void listAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.printf("%-5s %-20s %-25s %-15s %-10s%n", 
                         "ID", "Name", "Email", "Reg No", "Status");
        System.out.println("-".repeat(80));
        
        // Enhanced for loop with continue demonstration
        for (Student student : students) {
            // Demonstrate continue - skip inactive students in listing
            if (student.getStatus() == StudentStatus.INACTIVE) {
                continue; // Skip inactive students
            }
            
            System.out.printf("%-5d %-20s %-25s %-15s %-10s%n",
                             student.getId(),
                             student.getFullName(),
                             student.getEmail(),
                             student.getRegNo(),
                             student.getStatus());
        }
        
        System.out.println("Total active students: " + 
                          students.stream()
                                 .filter(s -> s.getStatus() == StudentStatus.ACTIVE)
                                 .count());
    }
    
    /**
     * Find a student by ID or registration number
     */
    private static void findStudent() {
        System.out.println("\n--- Find Student ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Registration Number");
        
        int searchType = getIntInput("Enter search type: ");
        
        Optional<Student> studentOpt = Optional.empty();
        
        switch (searchType) {
            case 1:
                long id = getIntInput("Enter Student ID: ");
                studentOpt = studentService.findStudentById(id);
                break;
            case 2:
                String regNo = getStringInput("Enter Registration Number: ");
                studentOpt = studentService.findStudentByRegNo(regNo);
                break;
            default:
                System.out.println("Invalid search type.");
                return;
        }
        
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            System.out.println("Student found:");
            System.out.println(student.getProfile());
        } else {
            System.out.println("Student not found.");
        }
    }
    
    /**
     * Update student information
     */
    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        
        long id = getIntInput("Enter Student ID to update: ");
        Optional<Student> studentOpt = studentService.findStudentById(id);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        Student student = studentOpt.get();
        System.out.println("Current student info: " + student.toString());
        
        String fullName = getStringInput("Enter new Full Name (current: " + student.getFullName() + "): ");
        String email = getStringInput("Enter new Email (current: " + student.getEmail() + "): ");
        
        if (!fullName.trim().isEmpty()) {
            student.setFullName(fullName);
        }
        if (!email.trim().isEmpty()) {
            student.setEmail(email);
        }
        
        if (studentService.updateStudent(student)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Failed to update student.");
        }
    }
    
    /**
     * Remove a student
     */
    private static void removeStudent() {
        System.out.println("\n--- Remove Student ---");
        
        long id = getIntInput("Enter Student ID to remove: ");
        
        if (studentService.removeStudent(id)) {
            System.out.println("Student removed successfully!");
        } else {
            System.out.println("Student not found or failed to remove.");
        }
    }
    
    /**
     * Add a new course using Builder pattern
     */
    private static void addCourse() {
        System.out.println("\n--- Add New Course ---");
        
        String courseCode = getStringInput("Enter Course Code: ");
        String title = getStringInput("Enter Course Title: ");
        int credits = getIntInput("Enter Credits: ");
        String department = getStringInput("Enter Department: ");
        
        System.out.println("Select Semester:");
        Semester[] semesters = Semester.values();
        for (int i = 0; i < semesters.length; i++) {
            System.out.println((i + 1) + ". " + semesters[i]);
        }
        
        int semesterChoice = getIntInput("Enter semester choice: ") - 1;
        if (semesterChoice < 0 || semesterChoice >= semesters.length) {
            System.out.println("Invalid semester choice.");
            return;
        }
        
        // Create course using Builder pattern
        Course course = new Course.Builder()
                .courseCode(courseCode)
                .title(title)
                .credits(credits)
                .department(department)
                .semester(semesters[semesterChoice])
                .build();
        
        if (courseService.addCourse(course)) {
            System.out.println("Course added successfully!");
        } else {
            System.out.println("Failed to add course. Course may already exist.");
        }
    }
    
    /**
     * List all courses
     */
    private static void listAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        System.out.printf("%-10s %-30s %-8s %-15s %-10s%n", 
                         "Code", "Title", "Credits", "Department", "Semester");
        System.out.println("-".repeat(80));
        
        for (Course course : courses) {
            System.out.printf("%-10s %-30s %-8d %-15s %-10s%n",
                             course.getCourseCode(),
                             course.getTitle(),
                             course.getCredits(),
                             course.getDepartment(),
                             course.getSemester());
        }
    }
    
    /**
     * Find a course by code
     */
    private static void findCourse() {
        System.out.println("\n--- Find Course ---");
        String courseCode = getStringInput("Enter Course Code: ");
        
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            System.out.println("Course found:");
            System.out.println(course.toString());
        } else {
            System.out.println("Course not found.");
        }
    }
    
    /**
     * Search courses by department - demonstrates Stream API
     */
    private static void searchCoursesByDepartment() {
        System.out.println("\n--- Search Courses by Department ---");
        String department = getStringInput("Enter Department: ");
        
        List<Course> courses = courseService.findCoursesByDepartment(department);
        
        if (courses.isEmpty()) {
            System.out.println("No courses found in department: " + department);
        } else {
            System.out.println("Courses in " + department + ":");
            courses.forEach(System.out::println);
        }
    }
    
    /**
     * Search courses by semester
     */
    private static void searchCoursesBySemester() {
        System.out.println("\n--- Search Courses by Semester ---");
        
        System.out.println("Select Semester:");
        Semester[] semesters = Semester.values();
        for (int i = 0; i < semesters.length; i++) {
            System.out.println((i + 1) + ". " + semesters[i]);
        }
        
        int choice = getIntInput("Enter semester choice: ") - 1;
        if (choice < 0 || choice >= semesters.length) {
            System.out.println("Invalid semester choice.");
            return;
        }
        
        List<Course> courses = courseService.findCoursesBySemester(semesters[choice]);
        
        if (courses.isEmpty()) {
            System.out.println("No courses found for semester: " + semesters[choice]);
        } else {
            System.out.println("Courses in " + semesters[choice] + ":");
            courses.forEach(System.out::println);
        }
    }
    
    /**
     * Remove a course
     */
    private static void removeCourse() {
        System.out.println("\n--- Remove Course ---");
        
        String courseCode = getStringInput("Enter Course Code to remove: ");
        
        if (courseService.removeCourse(courseCode)) {
            System.out.println("Course removed successfully!");
        } else {
            System.out.println("Course not found or failed to remove.");
        }
    }
    
    /**
     * Enroll student in course - demonstrates exception handling
     */
    private static void enrollStudentInCourse() {
        System.out.println("\n--- Enroll Student in Course ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        String courseCode = getStringInput("Enter Course Code: ");
        
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        if (!courseOpt.isPresent()) {
            System.out.println("Course not found.");
            return;
        }
        
        try {
            enrollmentService.enrollStudent(studentOpt.get(), courseOpt.get());
            System.out.println("Student enrolled successfully!");
        } catch (DuplicateEnrollmentException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        } catch (MaxCreditLimitExceededException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        }
    }
    
    /**
     * Drop student from course
     */
    private static void dropStudentFromCourse() {
        System.out.println("\n--- Drop Student from Course ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        String courseCode = getStringInput("Enter Course Code: ");
        
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        if (!courseOpt.isPresent()) {
            System.out.println("Course not found.");
            return;
        }
        
        if (enrollmentService.dropCourse(studentOpt.get(), courseOpt.get())) {
            System.out.println("Student dropped from course successfully!");
        } else {
            System.out.println("Failed to drop student from course.");
        }
    }
    
    /**
     * Assign grade to enrollment
     */
    private static void assignGrade() {
        System.out.println("\n--- Assign Grade ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        String courseCode = getStringInput("Enter Course Code: ");
        
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        
        if (!studentOpt.isPresent() || !courseOpt.isPresent()) {
            System.out.println("Student or course not found.");
            return;
        }
        
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentOpt.get());
        Optional<Enrollment> enrollmentOpt = enrollments.stream()
                .filter(e -> e.getCourse().getCourseCode().equals(courseCode))
                .findFirst();
        
        if (!enrollmentOpt.isPresent()) {
            System.out.println("Enrollment not found.");
            return;
        }
        
        System.out.println("Select Grade:");
        Grade[] grades = Grade.values();
        for (int i = 0; i < grades.length; i++) {
            System.out.println((i + 1) + ". " + grades[i] + " (" + grades[i].getGradePoint() + " points)");
        }
        
        int gradeChoice = getIntInput("Enter grade choice: ") - 1;
        if (gradeChoice < 0 || gradeChoice >= grades.length) {
            System.out.println("Invalid grade choice.");
            return;
        }
        
        enrollmentOpt.get().setGrade(grades[gradeChoice]);
        System.out.println("Grade assigned successfully!");
    }
    
    /**
     * View student enrollments
     */
    private static void viewStudentEnrollments() {
        System.out.println("\n--- View Student Enrollments ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentOpt.get());
        
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for this student.");
        } else {
            System.out.println("Enrollments for " + studentOpt.get().getFullName() + ":");
            enrollments.forEach(System.out::println);
        }
    }
    
    /**
     * View course enrollments
     */
    private static void viewCourseEnrollments() {
        System.out.println("\n--- View Course Enrollments ---");
        
        String courseCode = getStringInput("Enter Course Code: ");
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        
        if (!courseOpt.isPresent()) {
            System.out.println("Course not found.");
            return;
        }
        
        List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseOpt.get());
        
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for this course.");
        } else {
            System.out.println("Enrollments for " + courseOpt.get().getTitle() + ":");
            enrollments.forEach(System.out::println);
        }
    }
    
    /**
     * Export data to files
     */
    private static void exportData() {
        System.out.println("\n--- Export Data ---");
        
        try {
            Path exportDir = importExportService.getDefaultExportDirectory();
            importExportService.exportData(
                studentService.getAllStudents(),
                courseService.getAllCourses(),
                exportDir
            );
            System.out.println("Data exported successfully to: " + exportDir);
        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }
    
    /**
     * Import students from file
     */
    private static void importStudents() {
        System.out.println("\n--- Import Students ---");
        
        String filePath = getStringInput("Enter file path (or press Enter for default): ");
        Path path = filePath.trim().isEmpty() ? 
                   Paths.get(config.getDataPath(), "students.csv") : 
                   Paths.get(filePath);
        
        try {
            List<Student> students = importExportService.importStudents(path);
            
            int imported = 0;
            for (Student student : students) {
                if (studentService.addStudent(student)) {
                    imported++;
                }
            }
            
            System.out.println("Imported " + imported + " students out of " + students.size());
        } catch (IOException e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }
    
    /**
     * Import courses from file
     */
    private static void importCourses() {
        System.out.println("\n--- Import Courses ---");
        
        String filePath = getStringInput("Enter file path (or press Enter for default): ");
        Path path = filePath.trim().isEmpty() ? 
                   Paths.get(config.getDataPath(), "courses.csv") : 
                   Paths.get(filePath);
        
        try {
            List<Course> courses = importExportService.importCourses(path);
            
            int imported = 0;
            for (Course course : courses) {
                if (courseService.addCourse(course)) {
                    imported++;
                }
            }
            
            System.out.println("Imported " + imported + " courses out of " + courses.size());
        } catch (IOException e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }
    
    /**
     * Create backup
     */
    private static void createBackup() {
        System.out.println("\n--- Create Backup ---");
        
        try {
            // First export current data
            Path exportDir = importExportService.getDefaultExportDirectory();
            importExportService.exportData(
                studentService.getAllStudents(),
                courseService.getAllCourses(),
                exportDir
            );
            
            // Then create backup
            Path backupDir = backupService.performBackup();
            System.out.println("Backup created successfully: " + backupDir);
        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }
    
    /**
     * List backups
     */
    private static void listBackups() {
        System.out.println("\n--- List Backups ---");
        
        try {
            List<Path> backups = backupService.listBackups();
            
            if (backups.isEmpty()) {
                System.out.println("No backups found.");
            } else {
                System.out.println("Available backups:");
                
                // Labeled break demonstration
                outerLoop: for (int i = 0; i < backups.size(); i++) {
                    Path backup = backups.get(i);
                    String info = backupService.getBackupInfo(backup);
                    System.out.println((i + 1) + ". " + info);
                    
                    // Demonstrate labeled break - break after showing 10 backups
                    if (i >= 9) {
                        System.out.println("... (showing first 10 backups only)");
                        break outerLoop;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to list backups: " + e.getMessage());
        }
    }
    
    /**
     * Cleanup old backups
     */
    private static void cleanupBackups() {
        System.out.println("\n--- Cleanup Old Backups ---");
        
        int keepCount = getIntInput("Enter number of recent backups to keep: ");
        
        try {
            int deleted = backupService.cleanupOldBackups(keepCount);
            System.out.println("Deleted " + deleted + " old backup(s).");
        } catch (IOException e) {
            System.out.println("Cleanup failed: " + e.getMessage());
        }
    }
    
    /**
     * Calculate backup size - demonstrates recursion
     */
    private static void calculateBackupSize() {
        System.out.println("\n--- Calculate Backup Size ---");
        
        try {
            List<Path> backups = backupService.listBackups();
            
            if (backups.isEmpty()) {
                System.out.println("No backups found.");
                return;
            }
            
            System.out.println("Select backup:");
            for (int i = 0; i < Math.min(10, backups.size()); i++) {
                System.out.println((i + 1) + ". " + backups.get(i).getFileName());
            }
            
            int choice = getIntInput("Enter backup choice: ") - 1;
            if (choice < 0 || choice >= backups.size()) {
                System.out.println("Invalid choice.");
                return;
            }
            
            Path selectedBackup = backups.get(choice);
            long size = backupService.calculateDirectorySize(selectedBackup);
            
            System.out.println("Backup size: " + formatFileSize(size));
            System.out.println("Path: " + selectedBackup);
        } catch (IOException e) {
            System.out.println("Failed to calculate backup size: " + e.getMessage());
        }
    }
    
    /**
     * Generate full transcript
     */
    private static void generateFullTranscript() {
        System.out.println("\n--- Generate Full Transcript ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        String transcript = transcriptService.generateTranscript(studentOpt.get());
        System.out.println(transcript);
    }
    
    /**
     * Generate summary transcript
     */
    private static void generateSummaryTranscript() {
        System.out.println("\n--- Generate Summary Transcript ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        String summary = transcriptService.generateSummaryTranscript(studentOpt.get());
        System.out.println(summary);
    }
    
    /**
     * Calculate student GPA
     */
    private static void calculateStudentGPA() {
        System.out.println("\n--- Calculate Student GPA ---");
        
        String regNo = getStringInput("Enter Student Registration Number: ");
        Optional<Student> studentOpt = studentService.findStudentByRegNo(regNo);
        
        if (!studentOpt.isPresent()) {
            System.out.println("Student not found.");
            return;
        }
        
        double gpa = transcriptService.calculateGPA(studentOpt.get());
        System.out.println("Student: " + studentOpt.get().getFullName());
        System.out.println("GPA: " + String.format("%.2f", gpa));
    }
    
    /**
     * Initialize sample data for demonstration
     */
    private static void initializeSampleData() {
        // Create sample instructors
        Instructor instructor1 = new Instructor(1, "Dr. John Smith", "john.smith@edu.ccrm", "Computer Science");
        Instructor instructor2 = new Instructor(2, "Dr. Jane Doe", "jane.doe@edu.ccrm", "Mathematics");
        
        // Create sample courses using Builder pattern
        Course course1 = new Course.Builder()
                .courseCode("CS101")
                .title("Introduction to Programming")
                .credits(3)
                .department("Computer Science")
                .semester(Semester.FALL)
                .instructor(instructor1)
                .build();
        
        Course course2 = new Course.Builder()
                .courseCode("MATH201")
                .title("Calculus II")
                .credits(4)
                .department("Mathematics")
                .semester(Semester.SPRING)
                .instructor(instructor2)
                .build();
        
        Course course3 = new Course.Builder()
                .courseCode("CS102")
                .title("Data Structures")
                .credits(3)
                .department("Computer Science")
                .semester(Semester.SPRING)
                .instructor(instructor1)
                .build();
        
        // Add courses to service
        courseService.addCourse(course1);
        courseService.addCourse(course2);
        courseService.addCourse(course3);
        
        // Create sample students
        Student student1 = new Student(1001, "Alice Johnson", "alice.johnson@student.edu", "2024001");
        Student student2 = new Student(1002, "Bob Wilson", "bob.wilson@student.edu", "2024002");
        Student student3 = new Student(1003, "Carol Brown", "carol.brown@student.edu", "2024003");
        
        // Add students to service
        studentService.addStudent(student1);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        
        // Create sample enrollments
        try {
            enrollmentService.enrollStudent(student1, course1);
            enrollmentService.enrollStudent(student1, course2);
            enrollmentService.enrollStudent(student2, course1);
            enrollmentService.enrollStudent(student2, course3);
            enrollmentService.enrollStudent(student3, course2);
            
            // Assign some grades
            List<Enrollment> alice_enrollments = enrollmentService.getStudentEnrollments(student1);
            if (!alice_enrollments.isEmpty()) {
                alice_enrollments.get(0).setGrade(Grade.A);
            }
            
            List<Enrollment> bob_enrollments = enrollmentService.getStudentEnrollments(student2);
            if (!bob_enrollments.isEmpty()) {
                bob_enrollments.get(0).setGrade(Grade.B);
            }
            
        } catch (Exception e) {
            System.out.println("Error initializing sample enrollments: " + e.getMessage());
        }
        
        System.out.println("Sample data initialized successfully!");
        System.out.println("- 3 courses added");
        System.out.println("- 3 students added");
        System.out.println("- Sample enrollments created");
    }
    
    /**
     * Get integer input from user with validation
     * @param prompt the prompt message
     * @return valid integer input
     */
    private static int getIntInput(String prompt) {
        int result;
        boolean validInput;
        
        // Demonstrate do-while loop
        do {
            try {
                System.out.print(prompt);
                result = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                result = 0;
                validInput = false;
            }
        } while (!validInput);
        
        return result;
    }
    
    /**
     * Get string input from user
     * @param prompt the prompt message
     * @return string input
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    /**
     * Format file size in human-readable format
     * @param bytes size in bytes
     * @return formatted size string
     */
    private static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
