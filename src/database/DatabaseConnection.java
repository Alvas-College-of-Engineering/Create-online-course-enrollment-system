package database;

import java.sql.*;

/**
 * Database connection manager using JDBC
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/course_enrollment";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private static Connection connection = null;
    
    // Get database connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    // Close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Initialize database tables
    public static void initializeDatabase() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        
        // Create students table
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS students (" +
                "student_id VARCHAR(20) PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "semester INT NOT NULL, " +
                "department VARCHAR(50) NOT NULL)";
        stmt.executeUpdate(createStudentsTable);
        
        // Create courses table
        String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses (" +
                "course_id VARCHAR(20) PRIMARY KEY, " +
                "course_name VARCHAR(100) NOT NULL, " +
                "instructor VARCHAR(100) NOT NULL, " +
                "total_seats INT NOT NULL, " +
                "available_seats INT NOT NULL, " +
                "eligibility_criteria VARCHAR(50), " +
                "fees DOUBLE NOT NULL)";
        stmt.executeUpdate(createCoursesTable);
        
        // Create enrollments table
        String createEnrollmentsTable = "CREATE TABLE IF NOT EXISTS enrollments (" +
                "enrollment_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "student_id VARCHAR(20), " +
                "course_id VARCHAR(20), " +
                "enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "status VARCHAR(20) DEFAULT 'Active', " +
                "FOREIGN KEY (student_id) REFERENCES students(student_id), " +
                "FOREIGN KEY (course_id) REFERENCES courses(course_id), " +
                "UNIQUE KEY unique_enrollment (student_id, course_id))";
        stmt.executeUpdate(createEnrollmentsTable);
        
        stmt.close();
    }
}
