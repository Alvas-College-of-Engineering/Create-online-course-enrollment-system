package models;

import java.util.Date;

/**
 * Enrollment class representing an enrollment record
 */
public class Enrollment {
    private int enrollmentId;
    private String studentId;
    private String courseId;
    private Date enrollmentDate;
    private String status;
    
    // Constructor
    public Enrollment(int enrollmentId, String studentId, String courseId, Date enrollmentDate, String status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }
    
    // Getters and Setters
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    
    public Date getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return String.format("Enrollment[ID=%d, Student=%s, Course=%s, Date=%s, Status=%s]",
                enrollmentId, studentId, courseId, enrollmentDate, status);
    }
}
