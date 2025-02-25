#!/bin/bash

set -e  # Exit on any error

echo "Starting deployment process..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker is not running"
    exit 1
fi

echo "Starting Docker containers..."
if ! docker compose up -d; then
    echo "Error: Failed to start Docker containers"
    exit 1
fi

echo "Building application..."
if ! ./gradlew clean build; then
    echo "Error: Build failed"
    exit 1
fi

JAR_FILE="build/libs/coupit-backend-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    exit 1
fi

echo "Starting application..."
PID_FILE="app.pid"

# Kill existing process if running
if [ -f "$PID_FILE" ]; then
    OLD_PID=$(cat "$PID_FILE")
    if ps -p "$OLD_PID" > /dev/null; then
        echo "Stopping existing application process..."
        kill "$OLD_PID"
        sleep 5
    fi
fi

# Start the application
nohup java -jar "$JAR_FILE" > coupit-backend.log 2>&1 &
echo $! > "$PID_FILE"

echo "Checking if application started successfully..."
sleep 10

if ps -p $(cat "$PID_FILE") > /dev/null; then
    echo "✅ Deployment completed successfully"
    echo "📝 Logs available at: coupit-backend.log"
else
    echo "❌ Application failed to start. Check logs for details"
    exit 1
fi