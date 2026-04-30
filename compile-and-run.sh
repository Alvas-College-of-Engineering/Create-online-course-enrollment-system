#!/bin/bash
echo "========================================"
echo "Compiling and Running Course Enrollment System"
echo "========================================"
echo ""

# Compile the simple web server
echo "Compiling..."
javac -cp "lib/*" src/SimpleWebServer.java

if [ $? -ne 0 ]; then
    echo ""
    echo "Compilation failed!"
    echo "Make sure you have JDK installed and H2 database JAR in lib folder"
    exit 1
fi

echo ""
echo "Starting server..."
echo ""
java -cp "src:lib/*" SimpleWebServer
