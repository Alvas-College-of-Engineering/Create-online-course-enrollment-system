<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Course Enrollment System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .menu {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 30px;
        }
        .menu-item {
            background: #667eea;
            color: white;
            padding: 20px;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
            transition: background 0.3s;
        }
        .menu-item:hover {
            background: #764ba2;
        }
        .student-form {
            margin-top: 20px;
            padding: 20px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
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
        }
        button:hover {
            background: #764ba2;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🎓 Online Course Enrollment System</h1>
        <p style="text-align: center; color: #666;">Student ID: 4AL23CS056 | Name: JOSNA FERNANDES</p>
        
        <div class="menu">
            <a href="enrollment?action=viewCourses" class="menu-item">
                📚 View Available Courses
            </a>
            
            <div class="student-form">
                <h3>View My Enrollments</h3>
                <form action="enrollment" method="get">
                    <input type="hidden" name="action" value="myEnrollments">
                    <input type="text" name="studentId" placeholder="Enter Student ID" required>
                    <button type="submit">View My Enrollments</button>
                </form>
            </div>
        </div>
        
        <% 
        HttpSession sess = request.getSession(false);
        if (sess != null && sess.getAttribute("message") != null) {
            String message = (String) sess.getAttribute("message");
        %>
            <div style="margin-top: 20px; padding: 15px; background: #d4edda; border: 1px solid #c3e6cb; border-radius: 4px; color: #155724;">
                <%= message %>
            </div>
        <%
            sess.removeAttribute("message");
        }
        %>
    </div>
</body>
</html>
