# Quick Start Guide - Run in Browser

## Option 1: Simple Standalone Server (Recommended for Quick Demo)

This version uses Java's built-in HTTP server and H2 in-memory database - no Tomcat or MySQL needed!

### Steps:

1. **Download H2 Database JAR** (if not already present):
   - Download from: https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar
   - Place it in the `lib/` folder

2. **Run the application**:
   
   **Windows:**
   ```
   compile-and-run.bat
   ```
   
   **Linux/Mac:**
   ```
   chmod +x compile-and-run.sh
   ./compile-and-run.sh
   ```

3. **Open your browser**:
   ```
   http://localhost:8080
   ```

4. **Test the system**:
   - Click "View Available Courses"
   - Select a course and click "Enroll Now"
   - Enter Student ID: `4AL23CS056`
   - Confirm enrollment
   - Go back and view "My Enrollments" with the same Student ID

### Pre-loaded Data:
- **Students**: 4AL23CS056 (JOSNA FERNANDES), 4AL23CS001, 4AL23CS002
- **Courses**: 6 courses including Advanced Java, DBMS, Web Technologies, etc.

---

## Option 2: Full Tomcat Deployment (Production Setup)

For the complete setup with Tomcat and MySQL, follow the instructions in `SETUP_GUIDE.txt`.

---

## Troubleshooting

**Port 8080 already in use:**
- Stop other applications using port 8080
- Or modify the PORT variable in SimpleWebServer.java

**H2 JAR not found:**
- Make sure h2-*.jar is in the lib/ folder
- Download from Maven Central if needed

**Java not found:**
- Install JDK 11 or higher
- Add Java to your PATH environment variable

---

## Features Demonstrated:

✅ View available courses with seat availability  
✅ Enroll in courses with eligibility checking  
✅ Prevent duplicate enrollments  
✅ Real-time seat updates  
✅ View personal enrollment history  
✅ Responsive web interface  

---

**Student:** JOSNA FERNANDES (4AL23CS056)  
**Project:** Online Course Enrollment System
