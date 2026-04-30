-- Sample data for Online Course Enrollment System
-- Create database
CREATE DATABASE IF NOT EXISTS course_enrollment;
USE course_enrollment;

-- Sample Students
INSERT INTO students (student_id, name, email, semester, department) VALUES
('4AL23CS056', 'JOSNA FERNANDES', 'josna.fernandes@example.com', 5, 'Computer Science'),
('4AL23CS001', 'Rahul Kumar', 'rahul.kumar@example.com', 3, 'Computer Science'),
('4AL23CS002', 'Priya Sharma', 'priya.sharma@example.com', 4, 'Computer Science'),
('4AL23EC001', 'Amit Patel', 'amit.patel@example.com', 2, 'Electronics'),
('4AL23ME001', 'Sneha Reddy', 'sneha.reddy@example.com', 6, 'Mechanical');

-- Sample Courses
INSERT INTO courses (course_id, course_name, instructor, total_seats, available_seats, eligibility_criteria, fees) VALUES
('CS501', 'Advanced Java Programming', 'Dr. Rajesh Kumar', 30, 30, 'Semester 3+', 5000.00),
('CS502', 'Database Management Systems', 'Prof. Anita Desai', 40, 40, 'Semester 3+', 4500.00),
('CS503', 'Web Technologies', 'Dr. Suresh Nair', 35, 35, 'Semester 4+', 4800.00),
('CS504', 'Data Structures and Algorithms', 'Prof. Meena Iyer', 45, 45, 'Semester 2+', 5200.00),
('CS505', 'Machine Learning Basics', 'Dr. Vikram Singh', 25, 25, 'Semester 5+', 6000.00),
('CS506', 'Mobile Application Development', 'Prof. Kavita Joshi', 30, 30, 'Semester 4+', 5500.00),
('CS507', 'Cloud Computing', 'Dr. Arun Mehta', 20, 20, 'Semester 6+', 6500.00),
('CS508', 'Artificial Intelligence', 'Prof. Deepak Verma', 25, 25, 'Semester 5+', 6200.00);
