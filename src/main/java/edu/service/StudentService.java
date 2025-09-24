package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Student Service for managing student operations
 */
public class StudentService {
    
    private List<Student> students;
    
    public StudentService() {
        this.students = new ArrayList<>();
    }
    
    /**
     * Add a new student to the system
     * @param student the student to add
     * @return true if student was added successfully
     */
    public boolean addStudent(Student student) {
        if (student != null && !isDuplicateStudent(student)) {
            students.add(student);
            return true;
        }
        return false;
    }
    
    /**
     * Find a student by ID
     * @param id the student ID to search for
     * @return Optional containing the student if found, empty otherwise
     */
    public Optional<Student> findStudentById(long id) {
        return students.stream()
                      .filter(student -> student.getId() == id)
                      .findFirst();
    }
    
    /**
     * Find a student by registration number
     * @param regNo the registration number to search for
     * @return Optional containing the student if found, empty otherwise
     */
    public Optional<Student> findStudentByRegNo(String regNo) {
        return students.stream()
                      .filter(student -> student.getRegNo().equals(regNo))
                      .findFirst();
    }
    
    /**
     * Update an existing student
     * @param student the student with updated information
     * @return true if student was updated successfully
     */
    public boolean updateStudent(Student student) {
        Optional<Student> existingStudent = findStudentById(student.getId());
        if (existingStudent.isPresent()) {
            int index = students.indexOf(existingStudent.get());
            students.set(index, student);
            return true;
        }
        return false;
    }
    
    /**
     * Get all students
     * @return list of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    /**
     * Remove a student by ID
     * @param id the student ID to remove
     * @return true if student was removed successfully
     */
    public boolean removeStudent(long id) {
        return students.removeIf(student -> student.getId() == id);
    }
    
    /**
     * Check if a student already exists (by ID or registration number)
     * @param student the student to check
     * @return true if duplicate exists
     */
    private boolean isDuplicateStudent(Student student) {
        return students.stream()
                      .anyMatch(s -> s.getId() == student.getId() || 
                                   s.getRegNo().equals(student.getRegNo()));
    }
    
    /**
     * Get total number of students
     * @return total count of students
     */
    public int getStudentCount() {
        return students.size();
    }
}
