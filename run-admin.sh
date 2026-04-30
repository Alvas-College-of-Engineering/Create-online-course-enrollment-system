#!/bin/bash
# Run Admin Panel on Linux/Mac

echo "Starting Admin Panel..."
java -cp "build:lib/*" admin.AdminPanel

if [ $? -ne 0 ]; then
    echo "Failed to start Admin Panel!"
    echo "Make sure you have:"
    echo "1. Compiled the project (run build.sh)"
    echo "2. MySQL Connector JAR in lib folder"
    echo "3. MySQL database running"
fi
