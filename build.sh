#!/bin/bash
# Build script for Linux/Mac

echo "========================================"
echo "Building Online Course Enrollment System"
echo "========================================"

# Create build directory
mkdir -p build

# Compile Java files
echo "Compiling Java files..."
javac -cp "lib/*" -d build src/database/*.java
javac -cp "lib/*:build" -d build src/models/*.java
javac -cp "lib/*:build" -d build src/servlets/*.java
javac -cp "lib/*:build" -d build src/admin/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo ""
echo "========================================"
echo "Build successful!"
echo "========================================"
echo ""
echo "Next steps:"
echo "1. Setup MySQL database using database/sample_data.sql"
echo "2. Update database credentials in DatabaseConnection.java"
echo "3. Deploy to Tomcat or run AdminPanel"
echo ""
