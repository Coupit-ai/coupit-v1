# Coupit Installation Guide

## System Requirements

### Backend
- JDK 17 or later
- Docker and Docker Compose
- PostgreSQL 14 or later
- 4GB RAM minimum
- 10GB free disk space

### Frontend
- Android Studio (latest version)
- Android SDK
- Gradle 8.12 or later
- 8GB RAM minimum

## Installation Steps

### 1. Clone the Repository
```bash
git clone [repository-url]
cd coupit
```

### 2. Backend Setup

#### Database Configuration
1. Navigate to backend directory:
```bash
cd coupit-backend
```

2. Create environment file:
```bash
cat > .env << EOL
POSTGRES_USER=coupit
POSTGRES_PASSWORD=coupit123
POSTGRES_DB=coupit
EOL
```

3. Start PostgreSQL and Adminer:
```bash
docker-compose up -d
```

4. Verify containers are running:
```bash
docker ps
```

#### Backend Application
1. Build and run the backend:
```bash
./gradlew bootRun
```

2. Verify the backend is running:
```bash
curl http://localhost:8080/health
```

### 3. Frontend Setup

#### Android App
1. Navigate to app directory:
```bash
cd coupit-app
```

2. Configure Android SDK:
```bash
echo "sdk.dir=/path/to/your/android/sdk" > local.properties
```

3. Build the app:
```bash
./gradlew :composeApp:installDebug
```

## Configuration

### Backend Configuration

#### Database
- Host: localhost
- Port: 5432
- Database: coupit
- Username: coupit
- Password: coupit123

#### API Configuration
- Port: 8080
- Base URL: http://localhost:8080

### Frontend Configuration

#### Android App
- API URL: http://localhost:8080
- Square Integration:
  - Application ID
  - Location ID

## Verification

### 1. Backend Health Check
```bash
curl http://localhost:8080/health
```

### 2. Database Connection
```bash
docker exec -it coupit-backend_postgres_1 psql -U coupit -d coupit
```

### 3. Mobile App
1. Install the app on your device
2. Launch the app
3. Verify connection to backend

## Troubleshooting

### Common Issues

1. **Database Connection**
   - Check PostgreSQL container status
   - Verify environment variables
   - Check network connectivity

2. **Backend Issues**
   - Check application logs
   - Verify port availability
   - Check Java version

3. **Mobile App Issues**
   - Check SDK configuration
   - Verify Gradle setup
   - Check network connectivity

### Logs

#### Backend Logs
```bash
cd coupit-backend
./gradlew bootRun --console=plain
```

#### Database Logs
```bash
docker logs coupit-backend_postgres_1
```

## Security Considerations

1. Change default passwords
2. Configure SSL/TLS
3. Set up proper firewall rules
4. Regular security updates

## Maintenance

### Regular Tasks
1. Database backups
2. Log rotation
3. Security updates
4. Performance monitoring

### Backup
```bash
# Database backup
docker exec -it coupit-backend_postgres_1 pg_dump -U coupit coupit > backup.sql
```

## Support

For additional help:
1. Check the troubleshooting guide
2. Review the API documentation
3. Contact the development team 