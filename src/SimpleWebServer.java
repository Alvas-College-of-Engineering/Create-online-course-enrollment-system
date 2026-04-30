import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

/**
 * Simple HTTP Server to run the enrollment system without Tomcat
 */
public class SimpleWebServer {
    private static Connection conn;
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws Exception {
        // Initialize in-memory database
        initDatabase();
        
        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Register handlers
        server.createContext("/", new HomeHandler());
        server.createContext("/courses", new CoursesHandler());
        server.createContext("/enroll", new EnrollHandler());
        server.createContext("/process-enroll", new ProcessEnrollHandler());
        server.createContext("/my-enrollments", new MyEnrollmentsHandler());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/add-student", new AddStudentHandler());
        server.createContext("/add-course", new AddCourseHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("========================================");
        System.out.println("Online Course Enrollment System Started!");
        System.out.println("========================================");
        System.out.println("Open your browser and go to:");
        System.out.println("http://localhost:" + PORT);
        System.out.println("========================================");
        System.out.println("Press Ctrl+C to stop the server");
    }
    
    private static void initDatabase() throws SQLException {
        // Use H2 in-memory database
        conn = DriverManager.getConnection("jdbc:h2:mem:enrollment;DB_CLOSE_DELAY=-1", "sa", "");
        
        Statement stmt = conn.createStatement();
        
        // Create tables
        stmt.execute("CREATE TABLE students (" +
                "student_id VARCHAR(20) PRIMARY KEY, " +
                "name VARCHAR(100), email VARCHAR(100), " +
                "semester INT, department VARCHAR(50))");
        
        stmt.execute("CREATE TABLE courses (" +
                "course_id VARCHAR(20) PRIMARY KEY, " +
                "course_name VARCHAR(100), instructor VARCHAR(100), " +
                "total_seats INT, available_seats INT, " +
                "eligibility_criteria VARCHAR(50), fees DOUBLE)");
        
        stmt.execute("CREATE TABLE enrollments (" +
                "enrollment_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "student_id VARCHAR(20), course_id VARCHAR(20), " +
                "enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "status VARCHAR(20))");
        
        // Insert sample data
        stmt.execute("INSERT INTO students VALUES " +
                "('4AL23CS056', 'JOSNA FERNANDES', 'josna@example.com', 5, 'Computer Science')," +
                "('4AL23CS001', 'Rahul Kumar', 'rahul@example.com', 3, 'Computer Science')," +
                "('4AL23CS002', 'Priya Sharma', 'priya@example.com', 4, 'Computer Science')");
        
        stmt.execute("INSERT INTO courses VALUES " +
                "('CS501', 'Advanced Java Programming', 'Dr. Rajesh Kumar', 30, 30, 'Semester 3+', 5000.00)," +
                "('CS502', 'Database Management Systems', 'Prof. Anita Desai', 40, 40, 'Semester 3+', 4500.00)," +
                "('CS503', 'Web Technologies', 'Dr. Suresh Nair', 35, 35, 'Semester 4+', 4800.00)," +
                "('CS504', 'Data Structures and Algorithms', 'Prof. Meena Iyer', 45, 45, 'Semester 2+', 5200.00)," +
                "('CS505', 'Machine Learning Basics', 'Dr. Vikram Singh', 25, 25, 'Semester 5+', 6000.00)," +
                "('CS506', 'Mobile Application Development', 'Prof. Kavita Joshi', 30, 30, 'Semester 4+', 5500.00)");
        
        stmt.close();
        System.out.println("Database initialized with sample data");
    }
    
    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Course Enrollment System</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh}" +
                ".container{max-width:800px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1)}" +
                "h1{color:#333;text-align:center}" +
                ".menu{display:flex;flex-direction:column;gap:15px;margin-top:30px}" +
                ".menu-item{background:#667eea;color:white;padding:20px;text-decoration:none;border-radius:5px;text-align:center;transition:background 0.3s}" +
                ".menu-item:hover{background:#764ba2}" +
                ".student-form{margin-top:20px;padding:20px;background:#f5f5f5;border-radius:5px}" +
                "input[type='text']{width:100%;padding:10px;margin:10px 0;border:1px solid #ddd;border-radius:4px;box-sizing:border-box}" +
                "button{background:#667eea;color:white;padding:12px 30px;border:none;border-radius:4px;cursor:pointer;font-size:16px}" +
                "button:hover{background:#764ba2}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h1>🎓 Online Course Enrollment System</h1>" +
                "<p style='text-align:center;color:#666'>Student ID: 4AL23CS056 | Name: JOSNA FERNANDES</p>" +
                "<div class='menu'>" +
                "<a href='/courses' class='menu-item'>📚 View Available Courses</a>" +
                "<a href='/admin' class='menu-item' style='background:#28a745'>🔧 Admin Panel (Add Students/Courses)</a>" +
                "<div class='student-form'>" +
                "<h3>View My Enrollments</h3>" +
                "<form action='/my-enrollments' method='get'>" +
                "<input type='text' name='studentId' placeholder='Enter Student ID' required>" +
                "<button type='submit'>View My Enrollments</button>" +
                "</form></div></div></div></body></html>";
            
            sendResponse(exchange, html);
        }
    }
    
    static class CoursesHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Available Courses</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh}" +
                ".container{max-width:1000px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1)}" +
                "h1{color:#333;text-align:center}" +
                ".course-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(300px,1fr));gap:20px;margin-top:30px}" +
                ".course-card{border:1px solid #ddd;border-radius:8px;padding:20px;background:#f9f9f9;transition:transform 0.2s}" +
                ".course-card:hover{transform:translateY(-5px);box-shadow:0 4px 12px rgba(0,0,0,0.15)}" +
                ".course-title{color:#667eea;font-size:18px;font-weight:bold;margin-bottom:10px}" +
                ".course-info{margin:8px 0;color:#555}" +
                ".enroll-btn{background:#667eea;color:white;padding:10px 20px;border:none;border-radius:4px;cursor:pointer;text-decoration:none;display:inline-block;margin-top:10px}" +
                ".enroll-btn:hover{background:#764ba2}" +
                ".back-link{display:inline-block;margin-bottom:20px;color:#667eea;text-decoration:none}" +
                ".seats-available{color:green;font-weight:bold}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<a href='/' class='back-link'>← Back to Home</a>" +
                "<h1>📚 Available Courses</h1>" +
                "<div class='course-grid'>");
            
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM courses WHERE available_seats > 0 ORDER BY course_name");
                
                while (rs.next()) {
                    html.append("<div class='course-card'>")
                        .append("<div class='course-title'>").append(rs.getString("course_name")).append("</div>")
                        .append("<div class='course-info'><strong>Course ID:</strong> ").append(rs.getString("course_id")).append("</div>")
                        .append("<div class='course-info'><strong>Instructor:</strong> ").append(rs.getString("instructor")).append("</div>")
                        .append("<div class='course-info'><strong>Fees:</strong> ₹").append(String.format("%.2f", rs.getDouble("fees"))).append("</div>")
                        .append("<div class='course-info'><strong>Eligibility:</strong> ").append(rs.getString("eligibility_criteria")).append("</div>")
                        .append("<div class='course-info seats-available'><strong>Available Seats:</strong> ")
                        .append(rs.getInt("available_seats")).append("/").append(rs.getInt("total_seats")).append("</div>")
                        .append("<a href='/enroll?courseId=").append(rs.getString("course_id")).append("' class='enroll-btn'>Enroll Now</a>")
                        .append("</div>");
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                html.append("<p>Error loading courses: ").append(e.getMessage()).append("</p>");
            }
            
            html.append("</div></div></body></html>");
            sendResponse(exchange, html.toString());
        }
    }
    
    static class EnrollHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            String courseId = params.get("courseId");
            
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Enroll in Course</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh}" +
                ".container{max-width:600px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1)}" +
                "h1{color:#333;text-align:center}" +
                ".course-details{background:#f5f5f5;padding:20px;border-radius:5px;margin:20px 0}" +
                ".detail-row{margin:10px 0;color:#555}" +
                ".form-group{margin:20px 0}" +
                "label{display:block;margin-bottom:5px;color:#333;font-weight:bold}" +
                "input[type='text']{width:100%;padding:10px;border:1px solid #ddd;border-radius:4px;box-sizing:border-box}" +
                "button{background:#667eea;color:white;padding:12px 30px;border:none;border-radius:4px;cursor:pointer;font-size:16px;width:100%}" +
                "button:hover{background:#764ba2}" +
                ".back-link{display:inline-block;margin-bottom:20px;color:#667eea;text-decoration:none}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<a href='/courses' class='back-link'>← Back to Courses</a>" +
                "<h1>📝 Course Enrollment</h1>");
            
            try {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM courses WHERE course_id = ?");
                pstmt.setString(1, courseId);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    html.append("<div class='course-details'>")
                        .append("<h2>").append(rs.getString("course_name")).append("</h2>")
                        .append("<div class='detail-row'><strong>Course ID:</strong> ").append(rs.getString("course_id")).append("</div>")
                        .append("<div class='detail-row'><strong>Instructor:</strong> ").append(rs.getString("instructor")).append("</div>")
                        .append("<div class='detail-row'><strong>Fees:</strong> ₹").append(String.format("%.2f", rs.getDouble("fees"))).append("</div>")
                        .append("<div class='detail-row'><strong>Eligibility:</strong> ").append(rs.getString("eligibility_criteria")).append("</div>")
                        .append("<div class='detail-row'><strong>Available Seats:</strong> ")
                        .append(rs.getInt("available_seats")).append("/").append(rs.getInt("total_seats")).append("</div>")
                        .append("</div>")
                        .append("<form action='/process-enroll' method='post'>")
                        .append("<input type='hidden' name='courseId' value='").append(courseId).append("'>")
                        .append("<div class='form-group'>")
                        .append("<label for='studentId'>Student ID:</label>")
                        .append("<input type='text' id='studentId' name='studentId' required placeholder='Enter your Student ID'>")
                        .append("</div>")
                        .append("<button type='submit'>Confirm Enrollment</button>")
                        .append("</form>");
                }
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                html.append("<p>Error: ").append(e.getMessage()).append("</p>");
            }
            
            html.append("</div></body></html>");
            sendResponse(exchange, html.toString());
        }
    }
    
    static class ProcessEnrollHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> params = queryToMap(formData);
            
            String studentId = params.get("studentId");
            String courseId = params.get("courseId");
            String message = "";
            
            try {
                // Check if student exists
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?");
                pstmt.setString(1, studentId);
                ResultSet rs = pstmt.executeQuery();
                
                if (!rs.next()) {
                    message = "Student not found!";
                } else {
                    int semester = rs.getInt("semester");
                    rs.close();
                    
                    // Check course availability
                    pstmt = conn.prepareStatement("SELECT * FROM courses WHERE course_id = ?");
                    pstmt.setString(1, courseId);
                    rs = pstmt.executeQuery();
                    
                    if (!rs.next()) {
                        message = "Course not found!";
                    } else if (rs.getInt("available_seats") <= 0) {
                        message = "No seats available!";
                    } else {
                        String eligibility = rs.getString("eligibility_criteria");
                        int requiredSem = Integer.parseInt(eligibility.replaceAll("[^0-9]", ""));
                        
                        if (semester < requiredSem) {
                            message = "Student not eligible for this course!";
                        } else {
                            // Check duplicate enrollment
                            pstmt = conn.prepareStatement("SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?");
                            pstmt.setString(1, studentId);
                            pstmt.setString(2, courseId);
                            ResultSet dupCheck = pstmt.executeQuery();
                            
                            if (dupCheck.next()) {
                                message = "Already enrolled in this course!";
                            } else {
                                // Enroll student
                                pstmt = conn.prepareStatement("INSERT INTO enrollments (student_id, course_id, status) VALUES (?, ?, 'Active')");
                                pstmt.setString(1, studentId);
                                pstmt.setString(2, courseId);
                                pstmt.executeUpdate();
                                
                                // Update available seats
                                pstmt = conn.prepareStatement("UPDATE courses SET available_seats = available_seats - 1 WHERE course_id = ?");
                                pstmt.setString(1, courseId);
                                pstmt.executeUpdate();
                                
                                message = "✅ Enrollment successful!";
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                message = "Error: " + e.getMessage();
            }
            
            String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Enrollment Result</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh}" +
                ".container{max-width:600px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1);text-align:center}" +
                "h1{color:#333}" +
                ".message{padding:20px;margin:20px 0;border-radius:5px;font-size:18px;" +
                (message.contains("successful") ? "background:#d4edda;color:#155724;border:1px solid #c3e6cb" : "background:#f8d7da;color:#721c24;border:1px solid #f5c6cb") + "}" +
                ".btn{background:#667eea;color:white;padding:12px 30px;border:none;border-radius:4px;cursor:pointer;text-decoration:none;display:inline-block;margin:10px}" +
                ".btn:hover{background:#764ba2}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h1>Enrollment Result</h1>" +
                "<div class='message'>" + message + "</div>" +
                "<a href='/courses' class='btn'>View Courses</a>" +
                "<a href='/' class='btn'>Go Home</a>" +
                "</div></body></html>";
            
            sendResponse(exchange, html);
        }
    }
    
    static class MyEnrollmentsHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            String studentId = params.get("studentId");
            
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>My Enrollments</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#667eea 0%,#764ba2 100%);min-height:100vh}" +
                ".container{max-width:900px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1)}" +
                "h1{color:#333;text-align:center}" +
                "table{width:100%;border-collapse:collapse;margin-top:20px}" +
                "th,td{padding:12px;text-align:left;border-bottom:1px solid #ddd}" +
                "th{background:#667eea;color:white}" +
                "tr:hover{background:#f5f5f5}" +
                ".back-link{display:inline-block;margin-bottom:20px;color:#667eea;text-decoration:none}" +
                ".status-active{color:green;font-weight:bold}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<a href='/' class='back-link'>← Back to Home</a>" +
                "<h1>📋 My Enrollments</h1>");
            
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT e.*, c.course_name, c.instructor, c.fees " +
                    "FROM enrollments e JOIN courses c ON e.course_id = c.course_id " +
                    "WHERE e.student_id = ? ORDER BY e.enrollment_date DESC");
                pstmt.setString(1, studentId);
                ResultSet rs = pstmt.executeQuery();
                
                boolean hasEnrollments = false;
                html.append("<table><thead><tr>" +
                    "<th>Course ID</th><th>Course Name</th><th>Instructor</th>" +
                    "<th>Fees</th><th>Enrollment Date</th><th>Status</th>" +
                    "</tr></thead><tbody>");
                
                while (rs.next()) {
                    hasEnrollments = true;
                    html.append("<tr>")
                        .append("<td>").append(rs.getString("course_id")).append("</td>")
                        .append("<td>").append(rs.getString("course_name")).append("</td>")
                        .append("<td>").append(rs.getString("instructor")).append("</td>")
                        .append("<td>₹").append(String.format("%.2f", rs.getDouble("fees"))).append("</td>")
                        .append("<td>").append(rs.getTimestamp("enrollment_date")).append("</td>")
                        .append("<td class='status-active'>").append(rs.getString("status")).append("</td>")
                        .append("</tr>");
                }
                
                html.append("</tbody></table>");
                
                if (!hasEnrollments) {
                    html.append("<p style='text-align:center;margin-top:30px;color:#666'>No enrollments found for Student ID: ")
                        .append(studentId).append("</p>");
                }
                
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                html.append("<p>Error: ").append(e.getMessage()).append("</p>");
            }
            
            html.append("</div></body></html>");
            sendResponse(exchange, html.toString());
        }
    }
    
    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
    
    private static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length > 1) {
                    try {
                        result.put(URLDecoder.decode(pair[0], "UTF-8"), 
                                 URLDecoder.decode(pair[1], "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
    
    static class AdminHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Admin Panel</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#28a745 0%,#20c997 100%);min-height:100vh}" +
                ".container{max-width:1200px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1)}" +
                "h1{color:#333;text-align:center}" +
                ".admin-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(500px,1fr));gap:30px;margin-top:30px}" +
                ".admin-section{border:2px solid #28a745;border-radius:8px;padding:25px;background:#f8f9fa}" +
                ".admin-section h2{color:#28a745;margin-top:0}" +
                ".form-group{margin:15px 0}" +
                "label{display:block;margin-bottom:5px;color:#333;font-weight:bold}" +
                "input[type='text'],input[type='number'],input[type='email']{width:100%;padding:10px;border:1px solid #ddd;border-radius:4px;box-sizing:border-box}" +
                "button{background:#28a745;color:white;padding:12px 30px;border:none;border-radius:4px;cursor:pointer;font-size:16px;width:100%;margin-top:10px}" +
                "button:hover{background:#218838}" +
                ".back-link{display:inline-block;margin-bottom:20px;color:#28a745;text-decoration:none;font-weight:bold}" +
                ".data-table{margin-top:30px}" +
                "table{width:100%;border-collapse:collapse;margin-top:20px}" +
                "th,td{padding:10px;text-align:left;border-bottom:1px solid #ddd;font-size:14px}" +
                "th{background:#28a745;color:white}" +
                "tr:hover{background:#f5f5f5}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<a href='/' class='back-link'>← Back to Home</a>" +
                "<h1>🔧 Admin Panel</h1>" +
                "<div class='admin-grid'>" +
                
                "<div class='admin-section'>" +
                "<h2>➕ Add New Student</h2>" +
                "<form action='/add-student' method='post'>" +
                "<div class='form-group'>" +
                "<label>Student ID:</label>" +
                "<input type='text' name='studentId' required placeholder='e.g., 4AL23CS057'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Name:</label>" +
                "<input type='text' name='name' required placeholder='Full Name'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Email:</label>" +
                "<input type='email' name='email' required placeholder='student@example.com'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Semester:</label>" +
                "<input type='number' name='semester' required min='1' max='8' placeholder='1-8'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Department:</label>" +
                "<input type='text' name='department' required placeholder='e.g., Computer Science'>" +
                "</div>" +
                "<button type='submit'>Add Student</button>" +
                "</form>" +
                "</div>" +
                
                "<div class='admin-section'>" +
                "<h2>➕ Add New Course</h2>" +
                "<form action='/add-course' method='post'>" +
                "<div class='form-group'>" +
                "<label>Course ID:</label>" +
                "<input type='text' name='courseId' required placeholder='e.g., CS509'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Course Name:</label>" +
                "<input type='text' name='courseName' required placeholder='Course Title'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Instructor:</label>" +
                "<input type='text' name='instructor' required placeholder='Instructor Name'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Total Seats:</label>" +
                "<input type='number' name='totalSeats' required min='1' placeholder='e.g., 30'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Eligibility Criteria:</label>" +
                "<input type='text' name='eligibility' required placeholder='e.g., Semester 3+'>" +
                "</div>" +
                "<div class='form-group'>" +
                "<label>Fees (₹):</label>" +
                "<input type='number' name='fees' required min='0' step='0.01' placeholder='e.g., 5000'>" +
                "</div>" +
                "<button type='submit'>Add Course</button>" +
                "</form>" +
                "</div>" +
                
                "</div>");
            
            // Display existing students
            html.append("<div class='data-table'>" +
                "<h2>👥 All Students</h2>" +
                "<table><thead><tr>" +
                "<th>Student ID</th><th>Name</th><th>Email</th><th>Semester</th><th>Department</th>" +
                "</tr></thead><tbody>");
            
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM students ORDER BY student_id");
                
                while (rs.next()) {
                    html.append("<tr>")
                        .append("<td>").append(rs.getString("student_id")).append("</td>")
                        .append("<td>").append(rs.getString("name")).append("</td>")
                        .append("<td>").append(rs.getString("email")).append("</td>")
                        .append("<td>").append(rs.getInt("semester")).append("</td>")
                        .append("<td>").append(rs.getString("department")).append("</td>")
                        .append("</tr>");
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                html.append("<tr><td colspan='5'>Error: ").append(e.getMessage()).append("</td></tr>");
            }
            
            html.append("</tbody></table></div>");
            
            // Display existing courses
            html.append("<div class='data-table'>" +
                "<h2>📚 All Courses</h2>" +
                "<table><thead><tr>" +
                "<th>Course ID</th><th>Course Name</th><th>Instructor</th><th>Seats</th><th>Eligibility</th><th>Fees</th>" +
                "</tr></thead><tbody>");
            
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM courses ORDER BY course_id");
                
                while (rs.next()) {
                    html.append("<tr>")
                        .append("<td>").append(rs.getString("course_id")).append("</td>")
                        .append("<td>").append(rs.getString("course_name")).append("</td>")
                        .append("<td>").append(rs.getString("instructor")).append("</td>")
                        .append("<td>").append(rs.getInt("available_seats")).append("/").append(rs.getInt("total_seats")).append("</td>")
                        .append("<td>").append(rs.getString("eligibility_criteria")).append("</td>")
                        .append("<td>₹").append(String.format("%.2f", rs.getDouble("fees"))).append("</td>")
                        .append("</tr>");
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                html.append("<tr><td colspan='6'>Error: ").append(e.getMessage()).append("</td></tr>");
            }
            
            html.append("</tbody></table></div>");
            html.append("</div></body></html>");
            
            sendResponse(exchange, html.toString());
        }
    }
    
    static class AddStudentHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> params = queryToMap(formData);
            
            String message = "";
            boolean success = false;
            
            try {
                PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO students (student_id, name, email, semester, department) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, params.get("studentId"));
                pstmt.setString(2, params.get("name"));
                pstmt.setString(3, params.get("email"));
                pstmt.setInt(4, Integer.parseInt(params.get("semester")));
                pstmt.setString(5, params.get("department"));
                pstmt.executeUpdate();
                pstmt.close();
                
                message = "✅ Student added successfully!<br>Student ID: " + params.get("studentId") + 
                         "<br>Name: " + params.get("name");
                success = true;
            } catch (SQLException e) {
                if (e.getMessage().contains("Unique")) {
                    message = "❌ Error: Student ID or Email already exists!";
                } else {
                    message = "❌ Error: " + e.getMessage();
                }
            }
            
            String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Add Student Result</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#28a745 0%,#20c997 100%);min-height:100vh}" +
                ".container{max-width:600px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1);text-align:center}" +
                "h1{color:#333}" +
                ".message{padding:20px;margin:20px 0;border-radius:5px;font-size:18px;" +
                (success ? "background:#d4edda;color:#155724;border:1px solid #c3e6cb" : "background:#f8d7da;color:#721c24;border:1px solid #f5c6cb") + "}" +
                ".btn{background:#28a745;color:white;padding:12px 30px;border:none;border-radius:4px;cursor:pointer;text-decoration:none;display:inline-block;margin:10px}" +
                ".btn:hover{background:#218838}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h1>Add Student Result</h1>" +
                "<div class='message'>" + message + "</div>" +
                "<a href='/admin' class='btn'>Back to Admin Panel</a>" +
                "<a href='/' class='btn'>Go Home</a>" +
                "</div></body></html>";
            
            sendResponse(exchange, html);
        }
    }
    
    static class AddCourseHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> params = queryToMap(formData);
            
            String message = "";
            boolean success = false;
            
            try {
                int totalSeats = Integer.parseInt(params.get("totalSeats"));
                PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO courses (course_id, course_name, instructor, total_seats, available_seats, eligibility_criteria, fees) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, params.get("courseId"));
                pstmt.setString(2, params.get("courseName"));
                pstmt.setString(3, params.get("instructor"));
                pstmt.setInt(4, totalSeats);
                pstmt.setInt(5, totalSeats);
                pstmt.setString(6, params.get("eligibility"));
                pstmt.setDouble(7, Double.parseDouble(params.get("fees")));
                pstmt.executeUpdate();
                pstmt.close();
                
                message = "✅ Course added successfully!<br>Course ID: " + params.get("courseId") + 
                         "<br>Course Name: " + params.get("courseName") +
                         "<br>Total Seats: " + totalSeats;
                success = true;
            } catch (SQLException e) {
                if (e.getMessage().contains("Unique") || e.getMessage().contains("PRIMARY")) {
                    message = "❌ Error: Course ID already exists!";
                } else {
                    message = "❌ Error: " + e.getMessage();
                }
            }
            
            String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Add Course Result</title>" +
                "<style>" +
                "body{font-family:Arial;margin:0;padding:20px;background:linear-gradient(135deg,#28a745 0%,#20c997 100%);min-height:100vh}" +
                ".container{max-width:600px;margin:0 auto;background:white;padding:30px;border-radius:10px;box-shadow:0 4px 6px rgba(0,0,0,0.1);text-align:center}" +
                "h1{color:#333}" +
                ".message{padding:20px;margin:20px 0;border-radius:5px;font-size:18px;" +
                (success ? "background:#d4edda;color:#155724;border:1px solid #c3e6cb" : "background:#f8d7da;color:#721c24;border:1px solid #f5c6cb") + "}" +
                ".btn{background:#28a745;color:white;padding:12px 30px;border:none;border-radius:4px;cursor:pointer;text-decoration:none;display:inline-block;margin:10px}" +
                ".btn:hover{background:#218838}" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<h1>Add Course Result</h1>" +
                "<div class='message'>" + message + "</div>" +
                "<a href='/admin' class='btn'>Back to Admin Panel</a>" +
                "<a href='/' class='btn'>Go Home</a>" +
                "</div></body></html>";
            
            sendResponse(exchange, html);
        }
    }
}
