package models;

import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

/**
 * EnrollmentManager - Manages enrollment operations using Collections and JDBC
 */
public class EnrollmentManager {
    private HashMap<String, Student> students;
    private HashMap<String, Course> courses;
    private ArrayList<Enrollment> enrollments;
    
    public EnrollmentManager() {
        students = new HashMap<>();
        courses = new HashMap<>();
        enrollments = new ArrayList<>();
    }
    
    // Load all data from database
    public void loadData() throws SQLException {
        loadStudents();
        loadCourses();
        loadEnrollments();
    }
    
    private void loadStudents() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
        
        while (rs.next()) {
            Student student = new Student(
                rs.getString("student_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("semester"),
                rs.getString("department")
            );
            students.put(student.getStudentId(), student);
        }
        rs.close();
        stmt.close();
    }
    
    private void loadCourses() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
        
        while (rs.next()) {
            Course course = new Course(
                rs.getString("course_id"),
                rs.getString("course_name"),
                rs.getString("instructor"),
                rs.getInt("total_seats"),
                rs.getString("eligibility_criteria"),
                rs.getDouble("fees")
            );
            course.setAvailableSeats(rs.getInt("available_seats"));
            courses.put(course.getCourseId(), course);
        }
        rs.close();
        stmt.close();
    }
    
    private void loadEnrollments() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM enrollments");
        
        while (rs.next()) {
            Enrollment enrollment = new Enrollment(
                rs.getInt("enrollment_id"),
                rs.getString("student_id"),
                rs.getString("course_id"),
                rs.getTimestamp("enrollment_date"),
                rs.getString("status")
            );
            enrollments.add(enrollment);
        }
        rs.close();
        stmt.close();
    }
    
    // Enroll student in course
    public String enrollStudent(String studentId, String courseId) throws SQLException {
        Student student = students.get(studentId);
        Course course = courses.get(courseId);
        
        if (student == null) return "Student not found";
        if (course == null) return "Course not found";
        if (!course.hasAvailableSeats()) return "No seats available";
        if (!course.checkEligibility(student)) return "Student not eligible";
        
        // Check if already enrolled
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId) && e.getCourseId().equals(courseId)) {
                return "Already enrolled";
            }
        }
        
        // Insert into database
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentId);
        pstmt.setString(2, courseId);
        pstmt.executeUpdate();
        
        // Update available seats
        sql = "UPDATE courses SET available_seats = available_seats - 1 WHERE course_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, courseId);
        pstmt.executeUpdate();
        pstmt.close();
        
        // Update in-memory data
        course.setAvailableSeats(course.getAvailableSeats() - 1);
        loadEnrollments();
        
        return "Enrollment successful";
    }
    
    // Get all courses
    public Collection<Course> getAllCourses() {
        return courses.values();
    }
    
    // Get available courses (sorted)
    public ArrayList<Course> getAvailableCourses() {
        ArrayList<Course> available = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.hasAvailableSeats()) {
                available.add(course);
            }
        }
        Collections.sort(available);
        return available;
    }
    
    // Get student enrollments
    public ArrayList<Enrollment> getStudentEnrollments(String studentId) {
        ArrayList<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId)) {
                studentEnrollments.add(e);
            }
        }
        return studentEnrollments;
    }
    
    public Student getStudent(String studentId) {
        return students.get(studentId);
    }
    
    public Course getCourse(String courseId) {
        return courses.get(courseId);
    }
}
