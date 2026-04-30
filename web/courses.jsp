<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, models.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Courses</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            max-width: 1000px;
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
        .course-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .course-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background: #f9f9f9;
            transition: transform 0.2s;
        }
        .course-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        .course-title {
            color: #667eea;
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .course-info {
            margin: 8px 0;
            color: #555;
        }
        .enroll-btn {
            background: #667eea;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
        }
        .enroll-btn:hover {
            background: #764ba2;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #667eea;
            text-decoration: none;
        }
        .seats-available {
            color: green;
            font-weight: bold;
        }
        .seats-low {
            color: orange;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="index.jsp" class="back-link">← Back to Home</a>
        <h1>📚 Available Courses</h1>
        
        <div class="course-grid">
            <%
            ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("courses");
            if (courses != null && !courses.isEmpty()) {
                for (Course course : courses) {
            %>
                <div class="course-card">
                    <div class="course-title"><%= course.getCourseName() %></div>
                    <div class="course-info"><strong>Course ID:</strong> <%= course.getCourseId() %></div>
                    <div class="course-info"><strong>Instructor:</strong> <%= course.getInstructor() %></div>
                    <div class="course-info"><strong>Fees:</strong> ₹<%= String.format("%.2f", course.getFees()) %></div>
                    <div class="course-info"><strong>Eligibility:</strong> <%= course.getEligibilityCriteria() %></div>
                    <div class="course-info <%= course.getAvailableSeats() < 5 ? "seats-low" : "seats-available" %>">
                        <strong>Available Seats:</strong> <%= course.getAvailableSeats() %>/<%= course.getTotalSeats() %>
                    </div>
                    <a href="enrollment?action=enrollmentForm&courseId=<%= course.getCourseId() %>" class="enroll-btn">
                        Enroll Now
                    </a>
                </div>
            <%
                }
            } else {
            %>
                <p>No courses available at the moment.</p>
            <%
            }
            %>
        </div>
    </div>
</body>
</html>
