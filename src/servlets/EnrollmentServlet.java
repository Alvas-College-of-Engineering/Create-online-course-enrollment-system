package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import models.*;
import database.DatabaseConnection;

/**
 * Servlet for handling enrollment operations
 */
public class EnrollmentServlet extends HttpServlet {
    private EnrollmentManager enrollmentManager;
    
    @Override
    public void init() throws ServletException {
        enrollmentManager = new EnrollmentManager();
        try {
            DatabaseConnection.initializeDatabase();
            enrollmentManager.loadData();
        } catch (SQLException e) {
            throw new ServletException("Database initialization failed", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) action = "home";
        
        try {
            switch (action) {
                case "viewCourses":
                    viewCourses(request, response);
                    break;
                case "enrollmentForm":
                    showEnrollmentForm(request, response);
                    break;
                case "myEnrollments":
                    viewMyEnrollments(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("enroll".equals(action)) {
                processEnrollment(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void viewCourses(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        enrollmentManager.loadData();
        request.setAttribute("courses", enrollmentManager.getAvailableCourses());
        request.getRequestDispatcher("/courses.jsp").forward(request, response);
    }
    
    private void showEnrollmentForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        String courseId = request.getParameter("courseId");
        enrollmentManager.loadData();
        Course course = enrollmentManager.getCourse(courseId);
        request.setAttribute("course", course);
        request.getRequestDispatcher("/enroll.jsp").forward(request, response);
    }
    
    private void processEnrollment(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        
        String result = enrollmentManager.enrollStudent(studentId, courseId);
        
        HttpSession session = request.getSession();
        session.setAttribute("message", result);
        
        response.sendRedirect("enrollment?action=viewCourses");
    }
    
    private void viewMyEnrollments(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        String studentId = request.getParameter("studentId");
        enrollmentManager.loadData();
        
        request.setAttribute("enrollments", enrollmentManager.getStudentEnrollments(studentId));
        request.setAttribute("manager", enrollmentManager);
        request.getRequestDispatcher("/myEnrollments.jsp").forward(request, response);
    }
}
