# Coupit Application Troubleshooting Guide

## Issue: Square Login and Database Connection Problems

### Initial Problem
- User encountered "Failed to login" error after Square authentication
- Backend logs showed database connection issues
- PostgreSQL container was not running properly

### Root Cause
1. PostgreSQL container was not running
2. Missing environment configuration
3. Database connection timeout errors

### Solution Steps

1. **Check PostgreSQL Container Status**
```bash
docker ps | grep postgres
```

2. **Stop Existing Containers**
```bash
cd coupit-backend
docker-compose down
```

3. **Start Containers with Environment Variables**
```bash
POSTGRES_USER=coupit POSTGRES_PASSWORD=coupit123 POSTGRES_DB=coupit docker-compose up -d
```

4. **Verify Container Status**
```bash
docker ps
```
Expected output should show:
- PostgreSQL container running on port 5432
- Adminer container running on port 7676

5. **Restart Backend Application**
```bash
cd coupit-backend
./gradlew bootRun
```

### Important Notes
- The backend application requires a running PostgreSQL database
- Environment variables must be properly configured
- The database connection must be established before attempting Square authentication

### Additional Information
- Backend runs on port 8080
- Adminer (database management) is available on port 7676
- PostgreSQL runs on port 5432

### Troubleshooting Tips
1. Always ensure PostgreSQL container is running before starting the backend
2. Check environment variables are properly set
3. Verify network connectivity between containers
4. Monitor backend logs for database connection errors

### Common Error Messages
1. "Connection to 127.0.0.1:5432 refused" - PostgreSQL not running
2. "Failed to login" - Database connection issues
3. "ContainerConfig" errors - Docker configuration problems

### Required Environment Variables
```
POSTGRES_USER=coupit
POSTGRES_PASSWORD=coupit123
POSTGRES_DB=coupit
```

### Next Steps After Fix
1. Close the Coupit app completely on mobile device
2. Reopen the app
3. Try the Square login process again

### Monitoring
- Check backend logs for successful database connections
- Verify Square authentication flow in logs
- Monitor container health status

### Support
If issues persist:
1. Check backend logs for specific error messages
2. Verify all containers are running properly
3. Ensure environment variables are correctly set
4. Confirm network connectivity between services 