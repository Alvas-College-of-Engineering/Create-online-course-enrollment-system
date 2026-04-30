# 🎓 Online Course Enrollment System

A comprehensive web-based course enrollment system built with Java, demonstrating advanced Java programming concepts including Collections Framework, JDBC, Servlets, JSP, and Swing GUI.

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Java Concepts Demonstrated](#java-concepts-demonstrated)
- [Screenshots](#screenshots)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

## ✨ Features

### Student Features
- 📚 Browse available courses with real-time seat availability
- ✅ Enroll in courses with automatic eligibility verification
- 📊 View personal enrollment history
- 🔍 Filter courses by availability
- 💰 View course fees and instructor information

### Admin Features
- ➕ Add new students and courses
- 📋 View all students and courses in tabular format
- 🖥️ Desktop GUI (Swing) for offline management
- 🌐 Web-based admin panel for remote access
- 🔄 Real-time data synchronization

### System Features
- 🚫 Duplicate enrollment prevention
- 🎯 Semester-based eligibility checking
- 💺 Automatic seat management
- 🔒 SQL injection prevention with PreparedStatements
- ⚡ Fast lookups using HashMap data structures
- 📱 Responsive web interface

## 🛠️ Technologies Used

| Technology | Purpose | Version |
|------------|---------|---------|
| **Java** | Core programming language | 11+ |
| **JDBC** | Database connectivity | Built-in |
| **Servlets** | Web request handling | Jakarta EE 9+ |
| **JSP** | Dynamic web pages | 2.3+ |
| **Swing** | Desktop GUI | Built-in |
| **MySQL** | Relational database (production) | 8.0+ |
| **H2 Database** | In-memory database (demo) | 2.2.224 |
| **Apache Tomcat** | Servlet container | 10.x |
| **HttpServer** | Standalone web server | com.sun.net.httpserver |

## 🏗️ Architecture

The project follows a **3-tier architecture**:

```
┌─────────────────────────────────────────┐
│     Presentation Layer                  │
│  (JSP Pages + Swing GUI)                │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Business Logic Layer                │
│  (Servlets + EnrollmentManager)         │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Data Access Layer                   │
│  (JDBC + DatabaseConnection)            │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Database                            │
│  (MySQL / H2)                           │
└─────────────────────────────────────────┘
```

**Design Patterns:**
- MVC (Model-View-Controller)
- Singleton (DatabaseConnection)
- DAO (Data Access Object)
- Front Controller (EnrollmentServlet)

## 📦 Prerequisites

- **JDK 11 or higher** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Apache Tomcat 10.x** (for production deployment) - [Download](https://tomcat.apache.org/download-10.cgi)
- **MySQL 8.0+** (for production) - [Download](https://dev.mysql.com/downloads/)
- **H2 Database** (included in `lib/` for quick demo)

## 🚀 Installation

### Option 1: Quick Start (Standalone Server)

**No Tomcat or MySQL required!** Uses H2 in-memory database.

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/course-enrollment-system.git
   cd course-enrollment-system
   ```

2. **Ensure H2 JAR is in lib folder:**
   ```bash
   # Already included: lib/h2-2.2.224.jar
   ```

3. **Run the application:**

   **Windows:**
   ```cmd
   compile-and-run.bat
   ```

   **Linux/Mac:**
   ```bash
   chmod +x compile-and-run.sh
   ./compile-and-run.sh
   ```

4. **Open your browser:**
   ```
   http://localhost:8080
   ```

### Option 2: Full Production Setup (Tomcat + MySQL)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/course-enrollment-system.git
   cd course-enrollment-system
   ```

2. **Setup MySQL database:**
   ```bash
   mysql -u root -p
   ```
   ```sql
   CREATE DATABASE course_enrollment;
   USE course_enrollment;
   SOURCE database/sample_data.sql;
   ```

3. **Configure database connection:**
   
   Edit `src/database/DatabaseConnection.java`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/course_enrollment";
   private static final String DB_USER = "root";
   private static final String DB_PASSWORD = "your_password";
   ```

4. **Download MySQL Connector:**
   
   Download [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) and place in `lib/` folder.

5. **Compile the project:**

   **Windows:**
   ```cmd
   build.bat
   ```

   **Linux/Mac:**
   ```bash
   chmod +x build.sh
   ./build.sh
   ```

6. **Deploy to Tomcat:**
   ```bash
   # Create deployment structure
   mkdir -p enrollment/WEB-INF/classes
   mkdir -p enrollment/WEB-INF/lib
   
   # Copy files
   cp -r build/* enrollment/WEB-INF/classes/
   cp web/WEB-INF/web.xml enrollment/WEB-INF/
   cp web/*.jsp enrollment/
   cp lib/*.jar enrollment/WEB-INF/lib/
   
   # Deploy to Tomcat
   cp -r enrollment $CATALINA_HOME/webapps/
   ```

7. **Start Tomcat:**
   ```bash
   $CATALINA_HOME/bin/startup.sh    # Linux/Mac
   %CATALINA_HOME%\bin\startup.bat  # Windows
   ```

8. **Access the application:**
   ```
   http://localhost:8080/enrollment/
   ```

### Option 3: Admin Panel (Swing GUI)

**Windows:**
```cmd
run-admin.bat
```

**Linux/Mac:**
```bash
chmod +x run-admin.sh
./run-admin.sh
```

## 📖 Usage

### Student Workflow

1. **View Available Courses:**
   - Navigate to home page
   - Click "View Available Courses"
   - Browse courses with seat availability

2. **Enroll in a Course:**
   - Click "Enroll Now" on desired course
   - Enter your Student ID (e.g., `4AL23CS056`)
   - Click "Confirm Enrollment"
   - System validates eligibility and seat availability

3. **View Your Enrollments:**
   - Go to home page
   - Enter Student ID in "View My Enrollments" section
   - Click "View My Enrollments"
   - See all enrolled courses with details

### Admin Workflow

**Web Admin Panel:**
1. Navigate to `http://localhost:8080/admin`
2. Fill in student/course forms
3. Click "Add Student" or "Add Course"
4. View all records in tables below

**Swing Admin Panel:**
1. Launch admin panel application
2. Use tabs to switch between Students and Courses
3. Click "Add" buttons to open forms
4. Fill in details and save
5. Click "Refresh" to reload data

## 📁 Project Structure

```
course-enrollment-system/
│
├── src/
│   ├── database/
│   │   └── DatabaseConnection.java       # JDBC connection manager
│   │
│   ├── models/
│   │   ├── Student.java                  # Student entity (implements Comparable)
│   │   ├── Course.java                   # Course entity with business logic
│   │   ├── Enrollment.java               # Enrollment record
│   │   └── EnrollmentManager.java        # Business logic with Collections
│   │
│   ├── servlets/
│   │   └── EnrollmentServlet.java        # Web request handler
│   │
│   ├── admin/
│   │   └── AdminPanel.java               # Swing-based admin GUI
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
│   └── sample_data.sql                   # Database schema + sample data
│
├── lib/
│   └── h2-2.2.224.jar                    # H2 database driver
│
├── build.sh / build.bat                  # Compilation scripts
├── compile-and-run.sh / .bat             # Quick start scripts
├── run-admin.sh / .bat                   # Admin panel launcher
├── SETUP_GUIDE.txt                       # Detailed setup instructions
├── QUICK_START.md                        # Quick start guide
├── details.md                            # Complete technical documentation
└── README.md                             # This file
```

## 🎯 Java Concepts Demonstrated

### Module 1: Collections Framework
- ✅ **HashMap** - Fast O(1) student/course lookups by ID
- ✅ **ArrayList** - Dynamic enrollment lists
- ✅ **Comparable Interface** - Natural ordering of students and courses
- ✅ **Collections.sort()** - Sorting available courses

### Module 2: String Handling
- ✅ String concatenation and manipulation
- ✅ **String.format()** - Currency and data formatting
- ✅ **replaceAll()** - Regex-based string parsing
- ✅ **valueOf()** - Type conversions

### Module 3: Swing GUI
- ✅ **JFrame** - Main application window
- ✅ **JTabbedPane** - Tab-based navigation
- ✅ **JTable + DefaultTableModel** - Data display
- ✅ **JDialog** - Modal forms
- ✅ **Layout Managers** - BorderLayout, GridLayout, FlowLayout
- ✅ **Event Handling** - ActionListeners
- ✅ **MVC Pattern** - Model-View-Controller in Swing

### Module 4: Servlets & JSP
- ✅ **Servlet Lifecycle** - init(), doGet(), doPost()
- ✅ **Request/Response Handling** - Parameters, attributes, forwarding
- ✅ **Session Management** - HttpSession for message passing
- ✅ **JSP Scriptlets** - Java code in JSP
- ✅ **JSP Expressions** - Dynamic content rendering
- ✅ **Request Dispatcher** - Forwarding and including

### Module 5: JDBC
- ✅ **Connection Management** - Singleton pattern
- ✅ **Statement** - Simple SQL queries
- ✅ **PreparedStatement** - Parameterized queries (SQL injection prevention)
- ✅ **ResultSet** - Query result processing
- ✅ **Transaction Handling** - Multiple operations as atomic unit
- ✅ **Exception Handling** - SQLException management

## 📸 Screenshots

### Home Page
```
┌─────────────────────────────────────────┐
│  🎓 Online Course Enrollment System     │
│                                         │
│  Student ID: 4AL23CS056                 │
│  Name: JOSNA FERNANDES                  │
│                                         │
│  ┌───────────────────────────────────┐ │
│  │ 📚 View Available Courses         │ │
│  └───────────────────────────────────┘ │
│                                         │
│  ┌───────────────────────────────────┐ │
│  │ 🔧 Admin Panel                    │ │
│  └───────────────────────────────────┘ │
│                                         │
│  View My Enrollments:                   │
│  [Enter Student ID] [View]              │
└─────────────────────────────────────────┘
```

### Course Listing
```
┌─────────────────────────────────────────┐
│  📚 Available Courses                    │
│                                         │
│  ┌─────────────────┐ ┌───────────────┐ │
│  │ Advanced Java   │ │ DBMS          │ │
│  │ Dr. Rajesh      │ │ Prof. Anita   │ │
│  │ ₹5000.00        │ │ ₹4500.00      │ │
│  │ Seats: 30/30    │ │ Seats: 40/40  │ │
│  │ [Enroll Now]    │ │ [Enroll Now]  │ │
│  └─────────────────┘ └───────────────┘ │
└─────────────────────────────────────────┘
```

## 🗄️ Database Schema

### Tables

**students**
| Column | Type | Constraints |
|--------|------|-------------|
| student_id | VARCHAR(20) | PRIMARY KEY |
| name | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE, NOT NULL |
| semester | INT | NOT NULL |
| department | VARCHAR(50) | NOT NULL |

**courses**
| Column | Type | Constraints |
|--------|------|-------------|
| course_id | VARCHAR(20) | PRIMARY KEY |
| course_name | VARCHAR(100) | NOT NULL |
| instructor | VARCHAR(100) | NOT NULL |
| total_seats | INT | NOT NULL |
| available_seats | INT | NOT NULL |
| eligibility_criteria | VARCHAR(50) | |
| fees | DOUBLE | NOT NULL |

**enrollments**
| Column | Type | Constraints |
|--------|------|-------------|
| enrollment_id | INT | PRIMARY KEY, AUTO_INCREMENT |
| student_id | VARCHAR(20) | FOREIGN KEY → students |
| course_id | VARCHAR(20) | FOREIGN KEY → courses |
| enrollment_date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| status | VARCHAR(20) | DEFAULT 'Active' |

**Relationships:**
- One student → Many enrollments
- One course → Many enrollments
- Students ↔ Courses (Many-to-Many through enrollments)

## 🔌 API Endpoints

### Student Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page |
| GET | `/courses` | List available courses |
| GET | `/enroll?courseId={id}` | Show enrollment form |
| POST | `/process-enroll` | Process enrollment |
| GET | `/my-enrollments?studentId={id}` | View student enrollments |

### Admin Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin` | Admin panel |
| POST | `/add-student` | Add new student |
| POST | `/add-course` | Add new course |

### Servlet Mapping (web.xml)

```xml
<servlet>
    <servlet-name>EnrollmentServlet</servlet-name>
    <servlet-class>servlets.EnrollmentServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>EnrollmentServlet</servlet-name>
    <url-pattern>/enrollment</url-pattern>
</servlet-mapping>
```

## 🧪 Testing

### Sample Test Data

**Students:**
- `4AL23CS056` - JOSNA FERNANDES (Semester 5, Computer Science)
- `4AL23CS001` - Rahul Kumar (Semester 3, Computer Science)
- `4AL23CS002` - Priya Sharma (Semester 4, Computer Science)

**Courses:**
- `CS501` - Advanced Java Programming (30 seats, Semester 3+, ₹5000)
- `CS502` - Database Management Systems (40 seats, Semester 3+, ₹4500)
- `CS503` - Web Technologies (35 seats, Semester 4+, ₹4800)
- `CS505` - Machine Learning Basics (25 seats, Semester 5+, ₹6000)

### Test Scenarios

1. **Successful Enrollment:**
   - Student: 4AL23CS056 (Semester 5)
   - Course: CS505 (requires Semester 5+)
   - Expected: ✅ Success

2. **Eligibility Failure:**
   - Student: 4AL23CS001 (Semester 3)
   - Course: CS505 (requires Semester 5+)
   - Expected: ❌ "Student not eligible"

3. **Duplicate Enrollment:**
   - Enroll same student in same course twice
   - Expected: ❌ "Already enrolled"

4. **No Seats Available:**
   - Course with 0 available seats
   - Expected: ❌ "No seats available"

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**JOSNA FERNANDES**
- Student ID: 4AL23CS056
- Course: Advanced Java Programming
- Institution: [Your Institution Name]

## 🙏 Acknowledgments

- Java Documentation - [Oracle Java Docs](https://docs.oracle.com/en/java/)
- Servlet & JSP Tutorial - [Jakarta EE](https://jakarta.ee/)
- MySQL Documentation - [MySQL Docs](https://dev.mysql.com/doc/)
- H2 Database - [H2 Database Engine](https://www.h2database.com/)

## 📞 Support

For support, email josna.fernandes@example.com or open an issue in the repository.

## 🔮 Future Enhancements

- [ ] User authentication and authorization
- [ ] Payment gateway integration
- [ ] Email notifications for enrollments
- [ ] Waitlist management for full courses
- [ ] Course prerequisites and dependencies
- [ ] Grade management system
- [ ] PDF report generation
- [ ] RESTful API for mobile apps
- [ ] Connection pooling (HikariCP)
- [ ] Logging framework (Log4j)

---

**⭐ If you find this project helpful, please give it a star!**

**📚 For detailed technical documentation, see [details.md](details.md)**
