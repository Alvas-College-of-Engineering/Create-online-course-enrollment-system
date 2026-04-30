package com.myproject.enrollment;

import java.util.ArrayList;
import java.util.Scanner;

// -------------------- Course Class --------------------
class Course {
    private String courseId;
    private String courseName;
    private int availableSeats;
    private double minGpaRequired;

    public Course(String courseId, String courseName, int availableSeats, double minGpaRequired) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.availableSeats = availableSeats;
        this.minGpaRequired = minGpaRequired;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getMinGpaRequired() {
        return minGpaRequired;
    }

    public boolean hasAvailableSeats() {
        return availableSeats > 0;
    }

    public boolean isEligible(double gpa) {
        return gpa >= minGpaRequired;
    }

    public void reduceSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }

    public void displayCourse() {
        System.out.println("-------------------------------------------------");
        System.out.println("Course ID       : " + courseId);
        System.out.println("Course Name     : " + courseName);
        System.out.println("Available Seats : " + availableSeats);
        System.out.println("Minimum GPA     : " + minGpaRequired);
        System.out.println("-------------------------------------------------");
    }
}

// -------------------- Student Class --------------------
class Student {
    private String studentId;
    private String name;
    private double gpa;
    private ArrayList<Course> enrolledCourses;

    public Student(String studentId, String name, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
        enrolledCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public double getGpa() {
        return gpa;
    }

    public String getName() {
        return name;
    }

    public boolean isAlreadyEnrolled(Course course) {
        return enrolledCourses.contains(course);
    }

    public void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void displayEnrolledCourses() {
        System.out.println("\nCourses enrolled by " + name + ":");
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled yet.");
        } else {
            for (Course c : enrolledCourses) {
                System.out.println("- " + c.getCourseName());
            }
        }
    }
}

// -------------------- Enrollment System Class --------------------
class EnrollmentSystem {
    private ArrayList<Course> courses;
    private ArrayList<Student> students;

    public EnrollmentSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Course findCourse(String id) {
        for (Course c : courses) {
            if (c.getCourseId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public Student findStudent(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public void displayAllCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course c : courses) {
            c.displayCourse();
        }
    }
}

// -------------------- Main Class --------------------
public class OnlineCourseEnrollmentSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EnrollmentSystem system = new EnrollmentSystem();

        // ----------- Adding Courses -----------
        system.addCourse(new Course("C101", "Java Programming", 2, 2.5));
        system.addCourse(new Course("C102", "Data Structures", 2, 3.0));
        system.addCourse(new Course("C103", "Database Systems", 1, 2.8));
        system.addCourse(new Course("C104", "Operating Systems", 2, 3.2));
        system.addCourse(new Course("C105", "Computer Networks", 3, 2.7));

        // ----------- Adding Students -----------
        system.addStudent(new Student("S001", "Alice", 3.5));
        system.addStudent(new Student("S002", "Bob", 2.6));
        system.addStudent(new Student("S003", "Charlie", 3.1));
        system.addStudent(new Student("S004", "David", 2.4));
        system.addStudent(new Student("S005", "Emma", 3.8));

        int choice;

        do {
            System.out.println("\n========== ONLINE COURSE ENROLLMENT SYSTEM ==========");
            System.out.println("1. View All Courses");
            System.out.println("2. Enroll Student in Course");
            System.out.println("3. View Student Enrollments");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    system.displayAllCourses();
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    String sid = sc.nextLine();
                    Student student = system.findStudent(sid);

                    if (student == null) {
                        System.out.println("Student not found!");
                        break;
                    }

                    System.out.print("Enter Course ID: ");
                    String cid = sc.nextLine();
                    Course course = system.findCourse(cid);

                    if (course == null) {
                        System.out.println("Course not found!");
                        break;
                    }

                    if (student.isAlreadyEnrolled(course)) {
                        System.out.println("Student already enrolled in this course!");
                    }
                    else if (!course.hasAvailableSeats()) {
                        System.out.println("Enrollment failed! No seats available.");
                    }
                    else if (!course.isEligible(student.getGpa())) {
                        System.out.println("Enrollment failed! GPA requirement not met.");
                    }
                    else {
                        course.reduceSeat();
                        student.enrollCourse(course);
                        System.out.println("Enrollment successful for " + student.getName() +
                                " in " + course.getCourseName());
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextLine();
                    student = system.findStudent(sid);

                    if (student != null) {
                        student.displayEnrolledCourses();
                    } else {
                        System.out.println("Student not found!");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}