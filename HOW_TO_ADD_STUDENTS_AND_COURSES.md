# 📖 How to Add New Students and Courses

## ✅ Server is Running!

Your Online Course Enrollment System is now live with an Admin Panel!

**URL:** http://localhost:8080

---

## 🔧 Method 1: Using Admin Panel (Web Interface) - EASIEST!

### Step 1: Access Admin Panel
1. Open your browser and go to: **http://localhost:8080**
2. Click on the green button: **"🔧 Admin Panel (Add Students/Courses)"**
3. Or directly visit: **http://localhost:8080/admin**

### Step 2: Add a New Student

Fill in the form on the left side:

**Example:**
- **Student ID:** 4AL23CS057
- **Name:** John Doe
- **Email:** john.doe@example.com
- **Semester:** 4
- **Department:** Computer Science

Click **"Add Student"** button.

You'll see a success message and the student will appear in the "All Students" table below!

### Step 3: Add a New Course

Fill in the form on the right side:

**Example:**
- **Course ID:** CS509
- **Course Name:** Artificial Intelligence
- **Instructor:** Dr. Vikram Singh
- **Total Seats:** 25
- **Eligibility Criteria:** Semester 5+
- **Fees:** 6000

Click **"Add Course"** button.

You'll see a success message and the course will appear in the "All Courses" table below!

### Step 4: Enroll the New Student in the New Course

1. Go back to home: **http://localhost:8080**
2. Click **"📚 View Available Courses"**
3. Find your new course (CS509 - Artificial Intelligence)
4. Click **"Enroll Now"**
5. Enter the new Student ID: **4AL23CS057**
6. Click **"Confirm Enrollment"**
7. ✅ Done! Enrollment successful!

### Step 5: Verify the Enrollment

1. Go back to home
2. In the "View My Enrollments" section
3. Enter Student ID: **4AL23CS057**
4. Click **"View My Enrollments"**
5. You'll see the enrolled course!

---

## 📊 Current Data in System

### Pre-loaded Students:
- **4AL23CS056** - JOSNA FERNANDES (Semester 5, CS)
- **4AL23CS001** - Rahul Kumar (Semester 3, CS)
- **4AL23CS002** - Priya Sharma (Semester 4, CS)

### Pre-loaded Courses:
- **CS501** - Advanced Java Programming (30 seats, Semester 3+)
- **CS502** - Database Management Systems (40 seats, Semester 3+)
- **CS503** - Web Technologies (35 seats, Semester 4+)
- **CS504** - Data Structures and Algorithms (45 seats, Semester 2+)
- **CS505** - Machine Learning Basics (25 seats, Semester 5+)
- **CS506** - Mobile Application Development (30 seats, Semester 4+)

---

## 🎯 Quick Test Scenario

**Add a new student and enroll them:**

1. **Add Student:**
   - ID: 4AL23CS100
   - Name: Test Student
   - Email: test@example.com
   - Semester: 6
   - Department: Computer Science

2. **Add Course:**
   - ID: CS600
   - Name: Cloud Computing Advanced
   - Instructor: Prof. Sarah Johnson
   - Seats: 20
   - Eligibility: Semester 5+
   - Fees: 7000

3. **Enroll:**
   - Go to courses page
   - Find CS600
   - Enroll student 4AL23CS100

4. **Verify:**
   - Check enrollments for student 4AL23CS100

---

## ⚠️ Important Notes

### Eligibility Rules:
- Format must be: "Semester X+" where X is a number
- Examples: "Semester 3+", "Semester 5+"
- Student's semester must be >= required semester

### Validation:
- ✅ Student ID must be unique
- ✅ Email must be unique
- ✅ Course ID must be unique
- ✅ Semester must be 1-8
- ✅ Seats must be positive number
- ✅ Fees must be positive number

### Error Messages:
- "Student ID already exists" - Choose different ID
- "Email already exists" - Use different email
- "Course ID already exists" - Choose different course ID
- "Student not eligible" - Check semester requirements
- "No seats available" - Course is full
- "Already enrolled" - Student already in that course

---

## 🌐 All Available URLs

- **Home:** http://localhost:8080
- **View Courses:** http://localhost:8080/courses
- **Admin Panel:** http://localhost:8080/admin
- **My Enrollments:** http://localhost:8080/my-enrollments?studentId=YOUR_ID

---

## 🛑 Stop the Server

When you're done testing, let me know and I'll stop the server!

---

## 💡 Tips

1. **Test with existing student first:** Use 4AL23CS056 to test enrollment
2. **Check eligibility:** Make sure student's semester meets course requirements
3. **View admin panel:** See all students and courses in one place
4. **Real-time updates:** All changes are immediate
5. **Seat tracking:** Watch available seats decrease after enrollment

---

**Student:** JOSNA FERNANDES (4AL23CS056)  
**Project:** Online Course Enrollment System  
**Status:** ✅ RUNNING on http://localhost:8080
