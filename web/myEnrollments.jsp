<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, models.*, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Enrollments</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background: #667eea;
            color: white;
        }
        tr:hover {
            background: #f5f5f5;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #667eea;
            text-decoration: none;
        }
        .status-active {
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="index.jsp" class="back-link">← Back to Home</a>
        <h1>📋 My Enrollments</h1>
        
        <%
        ArrayList<Enrollment> enrollments = (ArrayList<Enrollment>) request.getAttribute("enrollments");
        EnrollmentManager manager = (EnrollmentManager) request.getAttribute("manager");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        
        if (enrollments != null && !enrollments.isEmpty()) {
        %>
        <table>
            <thead>
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                    <th>Instructor</th>
                    <th>Enrollment Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <%
                for (Enrollment enrollment : enrollments) {
                    Course course = manager.getCourse(enrollment.getCourseId());
                    if (course != null) {
                %>
                <tr>
                    <td><%= course.getCourseId() %></td>
                    <td><%= course.getCourseName() %></td>
                    <td><%= course.getInstructor() %></td>
                    <td><%= sdf.format(enrollment.getEnrollmentDate()) %></td>
                    <td class="status-active"><%= enrollment.getStatus() %></td>
                </tr>
                <%
                    }
                }
                %>
            </tbody>
        </table>
        <%
        } else {
        %>
        <p style="text-align: center; margin-top: 30px; color: #666;">No enrollments found.</p>
        <%
        }
        %>
    </div>
</body>
</html>
