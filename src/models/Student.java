package models;

import java.util.*;

/**
 * Student class representing a student with enrollment tracking
 */
public class Student implements Comparable<Student> {
    private String studentId;
    private String name;
    private String email;
    private int semester;
    private String department;
    private ArrayList<Course> enrolledCourses;
    
    // Constructor
    public Student(String studentId, String name, String email, int semester, String department) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.semester = semester;
        this.department = department;
        this.enrolledCourses = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public ArrayList<Course> getEnrolledCourses() { return enrolledCourses; }
    
    // Business methods
    public void addCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }
    
    public boolean isEnrolledIn(String courseId) {
        for (Course course : enrolledCourses) {
            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }
    
    @Override
    public String toString() {
        return String.format("Student[ID=%s, Name=%s, Semester=%d, Dept=%s]",
                studentId, name, semester, department);
    }
}
