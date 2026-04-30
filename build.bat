@echo off
REM Build script for Windows

echo ========================================
echo Building Online Course Enrollment System
echo ========================================

REM Create build directory
if not exist build mkdir build

REM Compile Java files
echo Compiling Java files...
javac -cp "lib\*" -d build src\database\*.java
javac -cp "lib\*;build" -d build src\models\*.java
javac -cp "lib\*;build" -d build src\servlets\*.java
javac -cp "lib\*;build" -d build src\admin\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Build successful!
echo ========================================
echo.
echo Next steps:
echo 1. Setup MySQL database using database/sample_data.sql
echo 2. Update database credentials in DatabaseConnection.java
echo 3. Deploy to Tomcat or run AdminPanel
echo.
pause
