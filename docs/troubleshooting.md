# Coupit Application Troubleshooting Guide

## Common Issues and Solutions

### 1. Square Authentication Issues

#### Symptoms
- "Failed to login" error after Square authentication
- Authentication flow redirects but fails to complete
- Token generation failures

#### Root Causes
1. Database connection issues
2. Missing environment configuration
3. PostgreSQL container not running
4. Insufficient rewards configuration

#### Solutions

##### Database Connection Issues
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

##### Rewards Configuration
If you see the error: "There are less than 2 rewards configured"
1. Access the admin panel
2. Navigate to Rewards section
3. Add at least two rewards with:
   - Title
   - Description
   - Probability
   - Validity hours
   - Discount code

### 2. Mobile App Build Issues

#### Symptoms
- Gradle build failures
- Missing SDK location
- Task not found errors

#### Solutions

1. **Check Android SDK Location**
```bash
# Create or edit local.properties
echo "sdk.dir=/path/to/your/android/sdk" > local.properties
```

2. **Verify Gradle Tasks**
```bash
./gradlew tasks
```

3. **Build Debug Version**
```bash
./gradlew installDebug
```

### 3. Backend Service Issues

#### Symptoms
- Database connection timeouts
- Hibernate errors
- Service unavailability

#### Solutions

1. **Check Service Status**
```bash
# Check running containers
docker ps

# Check service logs
docker logs coupit-backend_postgres_1
```

2. **Verify Environment Variables**
Required variables:
```
POSTGRES_USER=coupit
POSTGRES_PASSWORD=coupit123
POSTGRES_DB=coupit
```

3. **Check Network Connectivity**
```bash
# Test database connection
nc -zv localhost 5432

# Test backend service
curl http://localhost:8080/health
```

### 4. Common Error Messages

1. **Database Errors**
   - "Connection to 127.0.0.1:5432 refused" - PostgreSQL not running
   - "HikariPool-1 - Connection is not available" - Connection pool issues
   - "ContainerConfig errors" - Docker configuration problems

2. **Authentication Errors**
   - "Failed to login" - Database connection issues
   - "Token generation failed" - OAuth configuration issues
   - "Invalid credentials" - Square API configuration issues

3. **Build Errors**
   - "Task not found" - Incorrect Gradle task name
   - "SDK location not found" - Missing Android SDK configuration
   - "Build failed" - Dependency or configuration issues

### 5. Monitoring and Logs

#### Backend Logs
- Location: `coupit-backend/logs/`
- Key indicators:
  - Database connection status
  - Authentication flow
  - API request/response
  - Error messages

#### Mobile App Logs
- Android: `adb logcat`
- iOS: Xcode console
- Key indicators:
  - Network requests
  - Authentication flow
  - UI state changes
  - Error messages

### 6. Support and Resources

1. **Documentation**
   - API Documentation: `/docs/api.md`
   - Architecture: `/docs/architecture.md`
   - Development Guide: `/docs/development.md`

2. **Tools**
   - Adminer: http://localhost:7676
   - Backend API: http://localhost:8080
   - Health Check: http://localhost:8080/health

3. **Contact**
   - Development Team: [Team Contact]
   - Issue Tracker: [Issue Tracker URL]
   - Support Email: [Support Email]

## Common Issues

### Installation Problems

#### Node.js Installation
- **Issue**: Node.js version mismatch
- **Solution**: Use nvm to install correct version
```bash
nvm install 14
nvm use 14
```

#### Database Connection
- **Issue**: Database connection failed
- **Solution**: Check connection string and credentials
```bash
# Verify database is running
pg_isready -h localhost -p 5432
```

### Development Issues

#### Build Failures
- **Issue**: Build process fails
- **Solution**: Clear cache and reinstall dependencies
```bash
rm -rf node_modules
npm cache clean --force
npm install
```

#### Test Failures
- **Issue**: Tests failing
- **Solution**: Check test environment setup
```bash
# Run tests with debug output
npm test -- --debug
```

### API Issues

#### Authentication Errors
- **Issue**: Invalid token
- **Solution**: Check token expiration and format
```bash
# Verify token
jwt decode <token>
```

#### Rate Limiting
- **Issue**: Too many requests
- **Solution**: Implement exponential backoff
```javascript
// Example backoff implementation
const backoff = (retries) => Math.min(1000 * Math.pow(2, retries), 30000);
```

### Database Issues

#### Migration Problems
- **Issue**: Migration fails
- **Solution**: Check migration files and database state
```bash
# List migrations
npm run migration:list
# Rollback last migration
npm run migration:rollback
```

#### Performance Issues
- **Issue**: Slow queries
- **Solution**: Optimize queries and add indexes
```sql
-- Check query performance
EXPLAIN ANALYZE SELECT * FROM users WHERE email = 'user@example.com';
```

### Deployment Issues

#### Docker Problems
- **Issue**: Container won't start
- **Solution**: Check logs and configuration
```bash
# View container logs
docker logs <container_id>
# Check container status
docker ps -a
```

#### Kubernetes Issues
- **Issue**: Pods not starting
- **Solution**: Check resource limits and configuration
```bash
# View pod status
kubectl get pods
# Check pod logs
kubectl logs <pod_name>
```

## Error Messages

### Common Error Codes

#### 400 Bad Request
- Check request parameters
- Validate input data
- Review API documentation

#### 401 Unauthorized
- Verify authentication token
- Check token expiration
- Ensure proper headers

#### 403 Forbidden
- Check user permissions
- Verify role assignments
- Review access control

#### 404 Not Found
- Check resource existence
- Verify API endpoints
- Review routing configuration

#### 500 Internal Server Error
- Check server logs
- Review error stack trace
- Verify database connection

## Performance Issues

### Slow Response Times
1. Check database queries
2. Review caching strategy
3. Monitor resource usage
4. Optimize code paths

### Memory Leaks
1. Monitor memory usage
2. Check for circular references
3. Review garbage collection
4. Implement proper cleanup

## Debugging Tools

### Logging
```javascript
// Enable debug logging
DEBUG=* npm start
```

### Monitoring
- Use New Relic for performance monitoring
- Implement Sentry for error tracking
- Set up logging aggregation

## Support

### Getting Help
1. Check documentation
2. Search existing issues
3. Contact support team
4. Submit bug report

### Reporting Issues
- Include error messages
- Provide reproduction steps
- Share environment details
- Attach relevant logs 