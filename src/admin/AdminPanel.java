package admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import database.DatabaseConnection;

/**
 * Swing-based Admin Panel for managing courses and students
 */
public class AdminPanel extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable courseTable, studentTable;
    private DefaultTableModel courseModel, studentModel;
    
    public AdminPanel() {
        setTitle("Course Enrollment - Admin Panel");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        
        // Course Management Tab
        JPanel coursePanel = createCoursePanel();
        tabbedPane.addTab("Courses", coursePanel);
        
        // Student Management Tab
        JPanel studentPanel = createStudentPanel();
        tabbedPane.addTab("Students", studentPanel);
        
        add(tabbedPane);
    }
    
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table
        String[] columns = {"Course ID", "Course Name", "Instructor", "Total Seats", "Available", "Eligibility", "Fees"};
        courseModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(courseModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Course");
        JButton refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> showAddCourseDialog());
        refreshBtn.addActionListener(e -> loadCourses());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table
        String[] columns = {"Student ID", "Name", "Email", "Semester", "Department"};
        studentModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(studentModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Student");
        JButton refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> showAddStudentDialog());
        refreshBtn.addActionListener(e -> loadStudents());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void showAddCourseDialog() {
        JDialog dialog = new JDialog(this, "Add Course", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField instructorField = new JTextField();
        JTextField seatsField = new JTextField();
        JTextField eligibilityField = new JTextField();
        JTextField feesField = new JTextField();
        
        panel.add(new JLabel("Course ID:"));
        panel.add(idField);
        panel.add(new JLabel("Course Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Instructor:"));
        panel.add(instructorField);
        panel.add(new JLabel("Total Seats:"));
        panel.add(seatsField);
        panel.add(new JLabel("Eligibility:"));
        panel.add(eligibilityField);
        panel.add(new JLabel("Fees:"));
        panel.add(feesField);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            try {
                addCourse(idField.getText(), nameField.getText(), instructorField.getText(),
                         Integer.parseInt(seatsField.getText()), eligibilityField.getText(),
                         Double.parseDouble(feesField.getText()));
                dialog.dispose();
                loadCourses();
                JOptionPane.showMessageDialog(this, "Course added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        panel.add(saveBtn);
        panel.add(cancelBtn);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showAddStudentDialog() {
        JDialog dialog = new JDialog(this, "Add Student", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField semesterField = new JTextField();
        JTextField deptField = new JTextField();
        
        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Semester:"));
        panel.add(semesterField);
        panel.add(new JLabel("Department:"));
        panel.add(deptField);
        
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> {
            try {
                addStudent(idField.getText(), nameField.getText(), emailField.getText(),
                          Integer.parseInt(semesterField.getText()), deptField.getText());
                dialog.dispose();
                loadStudents();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        panel.add(saveBtn);
        panel.add(cancelBtn);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void loadData() {
        loadCourses();
        loadStudents();
    }
    
    private void loadCourses() {
        courseModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
            
            while (rs.next()) {
                courseModel.addRow(new Object[]{
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getString("instructor"),
                    rs.getInt("total_seats"),
                    rs.getInt("available_seats"),
                    rs.getString("eligibility_criteria"),
                    rs.getDouble("fees")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage());
        }
    }
    
    private void loadStudents() {
        studentModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            
            while (rs.next()) {
                studentModel.addRow(new Object[]{
                    rs.getString("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("semester"),
                    rs.getString("department")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }
    
    private void addCourse(String id, String name, String instructor, int seats, 
                          String eligibility, double fees) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO courses VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, name);
        pstmt.setString(3, instructor);
        pstmt.setInt(4, seats);
        pstmt.setInt(5, seats);
        pstmt.setString(6, eligibility);
        pstmt.setDouble(7, fees);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    private void addStudent(String id, String name, String email, int semester, 
                           String dept) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, name);
        pstmt.setString(3, email);
        pstmt.setInt(4, semester);
        pstmt.setString(5, dept);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
            SwingUtilities.invokeLater(() -> {
                AdminPanel panel = new AdminPanel();
                panel.setVisible(true);
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }
}
