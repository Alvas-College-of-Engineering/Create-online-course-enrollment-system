@echo off
echo ========================================
echo Compiling and Running Course Enrollment System
echo ========================================
echo.

REM Compile the simple web server
echo Compiling...
javac -cp "lib\*" src\SimpleWebServer.java

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed!
    echo Make sure you have JDK installed and H2 database JAR in lib folder
    pause
    exit /b 1
)

echo.
echo Starting server...
echo.
java -cp "src;lib\*" SimpleWebServer

pause
