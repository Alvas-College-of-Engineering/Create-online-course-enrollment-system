package models;

import java.util.*;

/**
 * Course class representing a course with enrollment management
 */
public class Course implements Comparable<Course> {
    private String courseId;
    private String courseName;
    private String instructor;
    private int totalSeats;
    private int availableSeats;
    private String eligibilityCriteria;
    private double fees;
    private ArrayList<Student> enrolledStudents;
    
    // Constructor
    public Course(String courseId, String courseName, String instructor, 
                  int totalSeats, String eligibilityCriteria, double fees) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.eligibilityCriteria = eligibilityCriteria;
        this.fees = fees;
        this.enrolledStudents = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    
    public String getEligibilityCriteria() { return eligibilityCriteria; }
    public void setEligibilityCriteria(String eligibilityCriteria) { 
        this.eligibilityCriteria = eligibilityCriteria; 
    }
    
    public double getFees() { return fees; }
    public void setFees(double fees) { this.fees = fees; }
    
    public ArrayList<Student> getEnrolledStudents() { return enrolledStudents; }
    
    // Business methods
    public boolean hasAvailableSeats() {
        return availableSeats > 0;
    }
    
    public boolean enrollStudent(Student student) {
        if (hasAvailableSeats() && checkEligibility(student)) {
            enrolledStudents.add(student);
            availableSeats--;
            return true;
        }
        return false;
    }
    
    public boolean checkEligibility(Student student) {
        // Simple eligibility check based on semester
        if (eligibilityCriteria.contains("All")) {
            return true;
        }
        return student.getSemester() >= Integer.parseInt(eligibilityCriteria.replaceAll("[^0-9]", ""));
    }
    
    @Override
    public int compareTo(Course other) {
        return this.courseName.compareTo(other.courseName);
    }
    
    @Override
    public String toString() {
        return String.format("Course[ID=%s, Name=%s, Instructor=%s, Seats=%d/%d, Fees=%.2f]",
                courseId, courseName, instructor, availableSeats, totalSeats, fees);
    }
}
