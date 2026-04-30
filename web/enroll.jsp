<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enroll in Course</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            max-width: 600px;
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
        .course-details {
            background: #f5f5f5;
            padding: 20px;
            border-radius: 5px;
            margin: 20px 0;
        }
        .detail-row {
            margin: 10px 0;
            color: #555;
        }
        .form-group {
            margin: 20px 0;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background: #667eea;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }
        button:hover {
            background: #764ba2;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #667eea;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="enrollment?action=viewCourses" class="back-link">← Back to Courses</a>
        <h1>📝 Course Enrollment</h1>
        
        <%
        Course course = (Course) request.getAttribute("course");
        if (course != null) {
        %>
        <div class="course-details">
            <h2><%= course.getCourseName() %></h2>
            <div class="detail-row"><strong>Course ID:</strong> <%= course.getCourseId() %></div>
            <div class="detail-row"><strong>Instructor:</strong> <%= course.getInstructor() %></div>
            <div class="detail-row"><strong>Fees:</strong> ₹<%= String.format("%.2f", course.getFees()) %></div>
            <div class="detail-row"><strong>Eligibility:</strong> <%= course.getEligibilityCriteria() %></div>
            <div class="detail-row"><strong>Available Seats:</strong> <%= course.getAvailableSeats() %>/<%= course.getTotalSeats() %></div>
        </div>
        
        <form action="enrollment" method="post">
            <input type="hidden" name="action" value="enroll">
            <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">
            
            <div class="form-group">
                <label for="studentId">Student ID:</label>
                <input type="text" id="studentId" name="studentId" required placeholder="Enter your Student ID">
            </div>
            
            <button type="submit">Confirm Enrollment</button>
        </form>
        <%
        } else {
        %>
        <p>Course not found.</p>
        <%
        }
        %>
    </div>
</body>
</html>
