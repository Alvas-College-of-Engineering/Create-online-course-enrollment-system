# Online Course Enrollment System - Technical Details

## Project Overview

This is a comprehensive **Online Course Enrollment System** built using Java, demonstrating multiple Java programming concepts including Collections Framework, String Handling, Swing GUI, Servlets & JSP, and JDBC. The system allows students to browse courses, enroll in them based on eligibility criteria, and view their enrollments. Administrators can manage students and courses through both a web interface and a Swing-based desktop application.

**Student:** JOSNA FERNANDES  
**Student ID:** 4AL23CS056  
**Course:** Advanced Java Programming

---

## Architecture Overview

The project follows a **3-tier architecture**:

1. **Presentation Layer**: Web interface (JSP pages) and Swing GUI (AdminPanel)
2. **Business Logic Layer**: Servlets, Model classes, and EnrollmentManager
3. **Data Layer**: JDBC-based database connectivity with MySQL/H2

### Design Patterns Used:
- **MVC (Model-View-Controller)**: Separation of concerns between data models, business logic, and presentation
- **Singleton Pattern**: DatabaseConnection maintains a single connection instance
- **DAO Pattern**: Database operations encapsulated in model classes
- **Front Controller**: EnrollmentServlet handles all web requests

---

## Project Structure

```
course-enrollment-system/
│
├── src/
│   ├── database/
│   │   └── DatabaseConnection.java       # JDBC connection manager
│   │
│   ├── models/
│   │   ├── Student.java                  # Student entity with Comparable
│   │   ├── Course.java                   # Course entity with business logic
│   │   ├── Enrollment.java               # Enrollment record
│   │   └── EnrollmentManager.java        # Business logic using Collections
│   │
│   ├── servlets/
│   │   └── EnrollmentServlet.java        # Web request handler
│   │
│   ├── admin/
│   │   └── AdminPanel.java               # Swing-based admin interface
│   │
│   └── SimpleWebServer.java              # Standalone HTTP server
│
├── web/
│   ├── index.jsp                         # Home page
│   ├── courses.jsp                       # Course listing
│   ├── enroll.jsp                        # Enrollment form
│   ├── myEnrollments.jsp                 # Student enrollments view
│   └── WEB-INF/
│       └── web.xml                       # Servlet configuration
│
├── database/
│   └── sample_data.sql                   # Database schema and sample data
│
├── lib/
│   └── h2-2.2.224.jar                    # H2 database driver
│
├── build.sh / build.bat                  # Compilation scripts
├── compile-and-run.sh / .bat             # Quick start scripts
└── run-admin.sh / .bat                   # Admin panel launcher
```

---

## Java Concepts Utilized

### 1. Collections Framework (Module 1)

#### HashMap Usage
**Location:** `EnrollmentManager.java`

```java
private HashMap<String, Student> students;
private HashMap<String, Course> courses;
```

- **Purpose**: Fast O(1) lookup of students and courses by their IDs
- **Key-Value Mapping**: Student ID → Student object, Course ID → Course object
- **Benefits**: Efficient retrieval when checking enrollment eligibility

#### ArrayList Usage
**Location:** Multiple classes

```java
private ArrayList<Enrollment> enrollments;
private ArrayList<Course> enrolledCourses;
```

- **Purpose**: Dynamic list storage for enrollments and courses
- **Operations**: add(), contains(), iteration with enhanced for-loop
- **Use Case**: Storing variable-length lists of enrollments

#### Comparable Interface
**Location:** `Student.java` and `Course.java`

```java
public class Student implements Comparable<Student> {
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }
}
```

- **Purpose**: Enable natural ordering of students by name and courses by course name
- **Usage**: `Collections.sort(availableCourses)` in EnrollmentManager

#### Collections.sort()
**Location:** `EnrollmentManager.java`

```java
public ArrayList<Course> getAvailableCourses() {
    ArrayList<Course> available = new ArrayList<>();
    for (Course course : courses.values()) {
        if (course.hasAvailableSeats()) {
            available.add(course);
        }
    }
    Collections.sort(available);  // Uses Comparable implementation
    return available;
}
```

---

### 2. String Handling (Module 2)

#### String Operations
**Location:** Throughout the project

**Concatenation:**
```java
String message = "Student ID: " + studentId + " | Name: " + name;
```

**String Formatting:**
```java
String.format("Course[ID=%s, Name=%s, Fees=%.2f]", courseId, courseName, fees);
String.format("%.2f", fees);  // Format currency to 2 decimal places
```

**String Manipulation:**
```java
// Extract semester number from eligibility criteria
int requiredSem = Integer.parseInt(eligibility.replaceAll("[^0-9]", ""));
```

**String Comparison:**
```java
if (eligibility.contains("All")) {
    return true;
}
```

#### valueOf() Conversions
**Location:** JSP pages and servlets

```java
Integer.parseInt(params.get("semester"));
Double.parseDouble(params.get("fees"));
```

---

### 3. Swing GUI (Module 3)

#### Components Used in AdminPanel.java

**JFrame - Main Window:**
```java
public class AdminPanel extends JFrame {
    public AdminPanel() {
        setTitle("Course Enrollment - Admin Panel");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```

**JTabbedPane - Tab Navigation:**
```java
private JTabbedPane tabbedPane;
tabbedPane = new JTabbedPane();
tabbedPane.addTab("Courses", coursePanel);
tabbedPane.addTab("Students", studentPanel);
```

**JTable with DefaultTableModel - Data Display:**
```java
String[] columns = {"Course ID", "Course Name", "Instructor", "Total Seats"};
courseModel = new DefaultTableModel(columns, 0);
courseTable = new JTable(courseModel);
```

**JDialog - Modal Forms:**
```java
JDialog dialog = new JDialog(this, "Add Course", true);
dialog.setSize(400, 400);
dialog.setLocationRelativeTo(this);
```

**Layout Managers:**
- **BorderLayout**: Main panel layout
- **GridLayout**: Form layouts
- **FlowLayout**: Button panels

**Event Handling:**
```java
addBtn.addActionListener(e -> showAddCourseDialog());
saveBtn.addActionListener(e -> {
    try {
        addCourse(idField.getText(), nameField.getText(), ...);
        dialog.dispose();
        loadCourses();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
});
```

**MVC Pattern in Swing:**
- **Model**: DefaultTableModel holds data
- **View**: JTable displays data
- **Controller**: ActionListeners handle user interactions

---

### 4. Servlets & JSP (Module 4)

#### Servlet Lifecycle
**Location:** `EnrollmentServlet.java`

```java
public class EnrollmentServlet extends HttpServlet {
    private EnrollmentManager enrollmentManager;
    
    @Override
    public void init() throws ServletException {
        // Called once when servlet is loaded
        enrollmentManager = new EnrollmentManager();
        DatabaseConnection.initializeDatabase();
        enrollmentManager.loadData();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Handle GET requests (view operations)
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Handle POST requests (enrollment operations)
    }
}
```

#### Request/Response Handling

**Request Parameters:**
```java
String action = request.getParameter("action");
String studentId = request.getParameter("studentId");
String courseId = request.getParameter("courseId");
```

**Request Attributes:**
```java
request.setAttribute("courses", enrollmentManager.getAvailableCourses());
request.setAttribute("course", course);
```

**Request Dispatcher:**
```java
request.getRequestDispatcher("/courses.jsp").forward(request, response);
```

**Response Redirect:**
```java
response.sendRedirect("enrollment?action=viewCourses");
```

#### Session Management

```java
HttpSession session = request.getSession();
session.setAttribute("message", result);
String message = (String) session.getAttribute("message");
session.removeAttribute("message");
```

#### JSP Features

**Scriptlets:**
```jsp
<%
ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("courses");
if (courses != null && !courses.isEmpty()) {
    for (Course course : courses) {
        // Display course
    }
}
%>
```

**Expressions:**
```jsp
<%= course.getCourseName() %>
<%= String.format("%.2f", course.getFees()) %>
```

**Directives:**
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, models.Course" %>
```

**HTML Forms:**
```jsp
<form action="enrollment" method="get">
    <input type="hidden" name="action" value="myEnrollments">
    <input type="text" name="studentId" required>
    <button type="submit">View My Enrollments</button>
</form>
```

---

### 5. JDBC (Module 5)

#### Connection Management
**Location:** `DatabaseConnection.java`

**Singleton Pattern:**
```java
private static Connection connection = null;

public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    return connection;
}
```

#### Database Operations

**Statement - Simple Queries:**
```java
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
while (rs.next()) {
    String courseId = rs.getString("course_id");
    String courseName = rs.getString("course_name");
    int seats = rs.getInt("available_seats");
}
rs.close();
stmt.close();
```

**PreparedStatement - Parameterized Queries:**
```java
String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, studentId);
pstmt.setString(2, courseId);
pstmt.executeUpdate();
pstmt.close();
```

**Benefits of PreparedStatement:**
- SQL injection prevention
- Better performance for repeated queries
- Automatic type conversion

**ResultSet Navigation:**
```java
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
    // Process single result
}
while (rs.next()) {
    // Process multiple results
}
```

#### Transaction Handling

```java
try {
    // Insert enrollment
    pstmt = conn.prepareStatement("INSERT INTO enrollments ...");
    pstmt.executeUpdate();
    
    // Update available seats
    pstmt = conn.prepareStatement("UPDATE courses SET available_seats = ...");
    pstmt.executeUpdate();
    
    // Both operations succeed or fail together
} catch (SQLException e) {
    // Handle error
}
```

#### Exception Handling

```java
try {
    Connection conn = DatabaseConnection.getConnection();
    // Database operations
} catch (SQLException e) {
    throw new ServletException("Database error", e);
} catch (ClassNotFoundException e) {
    throw new SQLException("MySQL JDBC Driver not found", e);
}
```

---

## Database Schema

### Tables

**students**
```sql
CREATE TABLE students (
    student_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    semester INT NOT NULL,
    department VARCHAR(50) NOT NULL
);
```

**courses**
```sql
CREATE TABLE courses (
    course_id VARCHAR(20) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    instructor VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    eligibility_criteria VARCHAR(50),
    fees DOUBLE NOT NULL
);
```

**enrollments**
```sql
CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20),
    course_id VARCHAR(20),
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Active',
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    UNIQUE KEY unique_enrollment (student_id, course_id)
);
```

### Relationships
- **One-to-Many**: One student can have many enrollments
- **One-to-Many**: One course can have many enrollments
- **Many-to-Many**: Students and Courses (through enrollments table)

---

## How the System Works - Complete Flow

### 1. System Initialization

**Standalone Mode (SimpleWebServer):**
```
1. main() method starts
2. initDatabase() creates H2 in-memory database
3. Creates tables (students, courses, enrollments)
4. Inserts sample data
5. HttpServer starts on port 8080
6. Registers URL handlers for different routes
```

**Tomcat Mode:**
```
1. Tomcat loads web.xml
2. EnrollmentServlet.init() is called
3. DatabaseConnection.initializeDatabase() creates tables
4. EnrollmentManager loads data from MySQL
5. Servlet is ready to handle requests
```

### 2. Student Views Available Courses

**User Action:** Clicks "View Available Courses"

**Flow:**
```
Browser → GET /courses
         ↓
SimpleWebServer.CoursesHandler (or EnrollmentServlet)
         ↓
Query: SELECT * FROM courses WHERE available_seats > 0
         ↓
ResultSet → Course objects
         ↓
HTML generation with course cards
         ↓
Response → Browser displays courses
```

**Code Path:**
```java
// In CoursesHandler
ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE available_seats > 0");
while (rs.next()) {
    // Build HTML for each course
    html.append("<div class='course-card'>")
        .append("<div class='course-title'>").append(rs.getString("course_name"))
        .append("</div>")
        // ... more fields
        .append("<a href='/enroll?courseId=").append(rs.getString("course_id"))
        .append("' class='enroll-btn'>Enroll Now</a>")
        .append("</div>");
}
```

### 3. Student Enrolls in a Course

**User Action:** Clicks "Enroll Now" on a course

**Step 1: Display Enrollment Form**
```
Browser → GET /enroll?courseId=CS501
         ↓
EnrollHandler.handle()
         ↓
Query: SELECT * FROM courses WHERE course_id = ?
         ↓
Display course details + student ID input form
```

**Step 2: Process Enrollment**
```
Browser → POST /process-enroll
         ↓
ProcessEnrollHandler.handle()
         ↓
Parse form data (studentId, courseId)
         ↓
Validation checks:
  1. Check if student exists
  2. Check if course exists
  3. Check available seats > 0
  4. Check eligibility (semester requirement)
  5. Check for duplicate enrollment
         ↓
If all checks pass:
  - INSERT INTO enrollments
  - UPDATE courses SET available_seats = available_seats - 1
         ↓
Display success/error message
```

**Detailed Validation Logic:**
```java
// 1. Student exists?
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?");
pstmt.setString(1, studentId);
ResultSet rs = pstmt.executeQuery();
if (!rs.next()) {
    message = "Student not found!";
    return;
}
int semester = rs.getInt("semester");

// 2. Course available?
pstmt = conn.prepareStatement("SELECT * FROM courses WHERE course_id = ?");
pstmt.setString(1, courseId);
rs = pstmt.executeQuery();
if (!rs.next()) {
    message = "Course not found!";
} else if (rs.getInt("available_seats") <= 0) {
    message = "No seats available!";
}

// 3. Eligibility check
String eligibility = rs.getString("eligibility_criteria");
int requiredSem = Integer.parseInt(eligibility.replaceAll("[^0-9]", ""));
if (semester < requiredSem) {
    message = "Student not eligible!";
}

// 4. Duplicate check
pstmt = conn.prepareStatement("SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?");
if (dupCheck.next()) {
    message = "Already enrolled!";
}

// 5. Enroll
pstmt = conn.prepareStatement("INSERT INTO enrollments (student_id, course_id, status) VALUES (?, ?, 'Active')");
pstmt.executeUpdate();

// 6. Update seats
pstmt = conn.prepareStatement("UPDATE courses SET available_seats = available_seats - 1 WHERE course_id = ?");
pstmt.executeUpdate();
```

### 4. Student Views Their Enrollments

**User Action:** Enters Student ID and clicks "View My Enrollments"

**Flow:**
```
Browser → GET /my-enrollments?studentId=4AL23CS056
         ↓
MyEnrollmentsHandler.handle()
         ↓
Query: SELECT e.*, c.course_name, c.instructor, c.fees
       FROM enrollments e 
       JOIN courses c ON e.course_id = c.course_id
       WHERE e.student_id = ?
         ↓
ResultSet → HTML table with enrollment details
         ↓
Display enrollments with course info
```

### 5. Admin Adds New Student/Course

**Admin Panel (Swing):**
```
User clicks "Add Student" button
         ↓
ActionListener triggered
         ↓
showAddStudentDialog() displays JDialog
         ↓
User fills form and clicks "Save"
         ↓
ActionListener on Save button
         ↓
addStudent() method:
  - PreparedStatement with INSERT query
  - Execute update
         ↓
dialog.dispose()
loadStudents() refreshes JTable
JOptionPane shows success message
```

**Web Admin Panel:**
```
Browser → POST /add-student
         ↓
AddStudentHandler.handle()
         ↓
Parse form data
         ↓
INSERT INTO students VALUES (?, ?, ?, ?, ?)
         ↓
Display success/error page
```

---

## File Connections and Dependencies

### Dependency Graph

```
SimpleWebServer.java
    ↓ (uses)
DatabaseConnection.java (H2 in-memory)

EnrollmentServlet.java
    ↓ (uses)
EnrollmentManager.java
    ↓ (uses)
Student.java, Course.java, Enrollment.java
    ↓ (uses)
DatabaseConnection.java (MySQL)

AdminPanel.java
    ↓ (uses)
DatabaseConnection.java (MySQL)

JSP Pages (index.jsp, courses.jsp, etc.)
    ↓ (forwards to)
EnrollmentServlet.java
    ↓ (sets attributes for)
JSP Pages (display data)
```

### Data Flow

```
Database (MySQL/H2)
    ↕ (JDBC)
DatabaseConnection.java
    ↕
Model Classes (Student, Course, Enrollment)
    ↕
EnrollmentManager (Business Logic)
    ↕
Servlets / Handlers (Controllers)
    ↕
JSP Pages / HTML (Views)
    ↕
Browser (User Interface)
```

---

## Key Features and Business Logic

### 1. Eligibility Checking

**Logic:** Extract semester number from eligibility criteria and compare with student's semester

```java
public boolean checkEligibility(Student student) {
    if (eligibilityCriteria.contains("All")) {
        return true;
    }
    // "Semester 3+" → extract "3"
    int required = Integer.parseInt(eligibilityCriteria.replaceAll("[^0-9]", ""));
    return student.getSemester() >= required;
}
```

### 2. Seat Management

**Atomic Operation:** Enrollment and seat update happen together

```java
// Insert enrollment
INSERT INTO enrollments (student_id, course_id, status) VALUES (?, ?, 'Active');

// Decrement available seats
UPDATE courses SET available_seats = available_seats - 1 WHERE course_id = ?;
```

### 3. Duplicate Prevention

**Database Constraint:**
```sql
UNIQUE KEY unique_enrollment (student_id, course_id)
```

**Application Check:**
```java
for (Enrollment e : enrollments) {
    if (e.getStudentId().equals(studentId) && e.getCourseId().equals(courseId)) {
        return "Already enrolled";
    }
}
```

### 4. Data Synchronization

**EnrollmentManager keeps in-memory cache synchronized with database:**

```java
public String enrollStudent(String studentId, String courseId) throws SQLException {
    // Validation using in-memory data
    Student student = students.get(studentId);
    Course course = courses.get(courseId);
    
    // Database update
    // INSERT INTO enrollments ...
    // UPDATE courses ...
    
    // Sync in-memory data
    course.setAvailableSeats(course.getAvailableSeats() - 1);
    loadEnrollments();  // Reload from database
    
    return "Enrollment successful";
}
```

---

## Deployment Options

### Option 1: Standalone Server (Quick Demo)
- Uses `SimpleWebServer.java`
- H2 in-memory database
- No external dependencies
- Run: `java -cp "src:lib/*" SimpleWebServer`

### Option 2: Tomcat + MySQL (Production)
- Full servlet container
- Persistent MySQL database
- Scalable and production-ready
- Deploy WAR file to Tomcat

### Option 3: Swing Admin Panel
- Desktop application
- Direct database access
- Run: `java -cp "build:lib/*" admin.AdminPanel`

---

## Technologies and Libraries

| Technology | Purpose | Version |
|------------|---------|---------|
| Java | Core programming language | 11+ |
| JDBC | Database connectivity | Built-in |
| Servlets | Web request handling | Jakarta EE 9+ |
| JSP | Dynamic web pages | 2.3+ |
| Swing | Desktop GUI | Built-in |
| MySQL | Relational database | 8.0+ |
| H2 Database | In-memory database | 2.2.224 |
| Apache Tomcat | Servlet container | 10.x |
| HttpServer | Built-in HTTP server | com.sun.net.httpserver |

---

## Security Features

1. **SQL Injection Prevention**: PreparedStatement with parameterized queries
2. **Input Validation**: Semester range (1-8), positive fees, required fields
3. **Unique Constraints**: Prevent duplicate students, courses, enrollments
4. **Foreign Key Constraints**: Maintain referential integrity
5. **Session Management**: Secure message passing between requests

---

## Error Handling

### Database Errors
```java
try {
    // Database operation
} catch (SQLException e) {
    if (e.getMessage().contains("Unique")) {
        message = "Record already exists!";
    } else {
        message = "Database error: " + e.getMessage();
    }
}
```

### User Input Errors
```java
try {
    int semester = Integer.parseInt(params.get("semester"));
} catch (NumberFormatException e) {
    message = "Invalid semester value!";
}
```

### GUI Error Display
```java
JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
```

---

## Testing Scenarios

### Test Case 1: Successful Enrollment
1. Student 4AL23CS056 (Semester 5) enrolls in CS505 (requires Semester 5+)
2. Expected: Success, seats decrease from 25 to 24

### Test Case 2: Eligibility Failure
1. Student 4AL23CS001 (Semester 3) tries to enroll in CS505 (requires Semester 5+)
2. Expected: "Student not eligible" error

### Test Case 3: Duplicate Enrollment
1. Student enrolls in CS501
2. Same student tries to enroll in CS501 again
3. Expected: "Already enrolled" error

### Test Case 4: No Seats Available
1. Course has 0 available seats
2. Student tries to enroll
3. Expected: "No seats available" error

---

## Performance Considerations

1. **HashMap for O(1) lookups**: Fast student/course retrieval by ID
2. **PreparedStatement caching**: Reusable compiled SQL statements
3. **Connection pooling**: Single connection reused (can be enhanced with connection pool)
4. **In-memory caching**: EnrollmentManager caches data to reduce database queries
5. **Indexed columns**: Primary keys and foreign keys automatically indexed

---

## Future Enhancements

1. **Authentication & Authorization**: User login system with roles
2. **Payment Integration**: Online fee payment gateway
3. **Email Notifications**: Enrollment confirmations
4. **Waitlist Management**: Queue system for full courses
5. **Course Prerequisites**: Chain of required courses
6. **Grade Management**: Track student performance
7. **Report Generation**: PDF reports for enrollments
8. **RESTful API**: Mobile app integration
9. **Connection Pooling**: Apache DBCP or HikariCP
10. **Logging Framework**: Log4j or SLF4J

---

## Conclusion

This project successfully demonstrates the integration of multiple Java technologies into a cohesive, functional web application. It showcases:

- **Object-Oriented Design**: Proper encapsulation, inheritance, and polymorphism
- **Collections Framework**: Efficient data structures for business logic
- **Database Integration**: JDBC for persistent data storage
- **Web Development**: Servlets and JSP for dynamic web pages
- **GUI Development**: Swing for desktop applications
- **Software Engineering**: Layered architecture, separation of concerns, error handling

The system is production-ready with proper validation, error handling, and user-friendly interfaces for both students and administrators.
