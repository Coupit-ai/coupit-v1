# Coupit Backend Server Setup and Running Guide

## Overview
The Coupit backend is a Spring Boot application built with Kotlin, providing essential services for the Coupit mobile application. This document outlines the setup process and how to run the server.

## Technical Stack
- **Language**: Kotlin 1.9.25
- **Framework**: Spring Boot 3.3.7
- **Java Version**: 21
- **Build Tool**: Gradle
- **Database**: PostgreSQL

## Key Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Web
- Spring Boot OAuth2 Client & Resource Server
- PostgreSQL Driver
- Square API Integration
- Firebase Admin
- JWT Support
- Dotenv Kotlin

## Server Setup Process

### 1. Prerequisites
- Java 21 installed
- Gradle installed
- PostgreSQL database running
- Required environment variables configured

### 2. Project Structure
```
coupit-backend/
├── src/                    # Source code
├── build/                  # Build output
├── gradle/                 # Gradle wrapper
├── docs/                   # Documentation
├── build.gradle.kts        # Build configuration
├── settings.gradle.kts     # Project settings
├── docker-compose.yaml     # Docker configuration
└── deploy.sh              # Deployment script
```

### 3. Running the Server

#### Method 1: Using Gradle
```bash
./gradlew bootRun
```
This command:
- Compiles the project
- Starts the Spring Boot application
- Enables development tools for hot reloading
- Runs the server in the background

#### Method 2: Using Docker
```bash
docker-compose up
```
This will start all required services including the database.

### 4. Server Features
- RESTful API endpoints
- OAuth2 authentication
- JWT token support
- Database integration
- Square payment processing
- Firebase integration
- PassKit support

### 5. Development Tools
The server runs with development tools enabled, providing:
- Automatic reloading on code changes
- Enhanced error reporting
- Development-specific configurations

### 6. Mobile App Integration
To connect the mobile app to the backend:
1. Ensure the mobile app is configured with the correct backend URL
2. Implement proper authentication token handling
3. Use the appropriate API endpoints for different features

### 7. Monitoring and Logs
- Server logs are stored in `coupit-backend.log`
- Process ID is stored in `app.pid`
- Use `ps aux | grep java` to check running processes

### 8. Common Issues and Solutions
1. **Server Not Starting**
   - Check Java version (must be 21)
   - Verify PostgreSQL is running
   - Check environment variables

2. **Database Connection Issues**
   - Verify PostgreSQL credentials
   - Check database connection string
   - Ensure database is running

3. **Mobile App Connection Issues**
   - Verify backend URL configuration
   - Check authentication tokens
   - Ensure proper API endpoint usage

## Security Considerations
- The server uses Spring Security for authentication
- OAuth2 is implemented for secure access
- JWT tokens are used for session management
- All sensitive data should be properly encrypted

## Maintenance
- Regular updates of dependencies
- Database backups
- Log rotation
- Security patches

## Support
For any issues or questions, please contact the development team or refer to the project documentation. 