@echo off
REM Run Admin Panel on Windows

echo Starting Admin Panel...
java -cp "build;lib\*" admin.AdminPanel

if %errorlevel% neq 0 (
    echo Failed to start Admin Panel!
    echo Make sure you have:
    echo 1. Compiled the project (run build.bat)
    echo 2. MySQL Connector JAR in lib folder
    echo 3. MySQL database running
    pause
)
