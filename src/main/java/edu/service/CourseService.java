package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Course Service for managing course operations
 * Demonstrates Java Stream API usage
 */
public class CourseService {
    
    private List<Course> courses;
    
    public CourseService() {
        this.courses = new ArrayList<>();
    }
    
    /**
     * Add a new course to the system
     * @param course the course to add
     * @return true if course was added successfully
     */
    public boolean addCourse(Course course) {
        if (course != null && !isDuplicateCourse(course)) {
            courses.add(course);
            return true;
        }
        return false;
    }
    
    /**
     * Find a course by course code
     * @param courseCode the course code to search for
     * @return Optional containing the course if found, empty otherwise
     */
    public Optional<Course> findCourseByCode(String courseCode) {
        return courses.stream()
                     .filter(course -> course.getCourseCode().equals(courseCode))
                     .findFirst();
    }
    
    /**
     * Search courses using a predicate filter - demonstrates Stream API
     * @param filter the predicate to filter courses
     * @return list of courses matching the filter
     */
    public List<Course> searchCourses(Predicate<Course> filter) {
        return courses.stream()
                     .filter(filter)
                     .collect(Collectors.toList());
    }
    
    /**
     * Find courses by instructor
     * @param instructor the instructor to search for
     * @return list of courses taught by the instructor
     */
    public List<Course> findCoursesByInstructor(Instructor instructor) {
        return searchCourses(course -> course.getInstructor() != null && 
                           course.getInstructor().getId() == instructor.getId());
    }
    
    /**
     * Find courses by department
     * @param department the department to search for
     * @return list of courses in the department
     */
    public List<Course> findCoursesByDepartment(String department) {
        return searchCourses(course -> course.getDepartment().equalsIgnoreCase(department));
    }
    
    /**
     * Find courses by semester
     * @param semester the semester to search for
     * @return list of courses in the semester
     */
    public List<Course> findCoursesBySemester(Semester semester) {
        return searchCourses(course -> course.getSemester() == semester);
    }
    
    /**
     * Get all courses
     * @return list of all courses
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
    
    /**
     * Remove a course by course code
     * @param courseCode the course code to remove
     * @return true if course was removed successfully
     */
    public boolean removeCourse(String courseCode) {
        return courses.removeIf(course -> course.getCourseCode().equals(courseCode));
    }
    
    /**
     * Get courses with minimum credits
     * @param minCredits minimum credit requirement
     * @return list of courses with at least minCredits
     */
    public List<Course> getCoursesWithMinCredits(int minCredits) {
        return searchCourses(course -> course.getCredits() >= minCredits);
    }
    
    /**
     * Check if a course already exists (by course code)
     * @param course the course to check
     * @return true if duplicate exists
     */
    private boolean isDuplicateCourse(Course course) {
        return courses.stream()
                     .anyMatch(c -> c.getCourseCode().equals(course.getCourseCode()));
    }
    
    /**
     * Get total number of courses
     * @return total count of courses
     */
    public int getCourseCount() {
        return courses.size();
    }
    
    /**
     * Get total credits for all courses
     * @return sum of all course credits
     */
    public int getTotalCredits() {
        return courses.stream()
                     .mapToInt(Course::getCredits)
                     .sum();
    }
    
    /**
     * Get courses sorted by title using anonymous inner class
     * @return list of courses sorted by title
     */
    public List<Course> getCoursesSortedByTitle() {
        List<Course> sortedCourses = new ArrayList<>(courses);
        
        // Using anonymous inner class for Comparator
        sortedCourses.sort(new java.util.Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getTitle().compareToIgnoreCase(c2.getTitle());
            }
        });
        
        return sortedCourses;
    }
}
