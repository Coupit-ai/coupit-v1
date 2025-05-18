# Deployment Guide

## Prerequisites

- Docker and Docker Compose
- AWS CLI (for AWS deployment)
- Kubernetes CLI (for Kubernetes deployment)
- Access to deployment environments
- Required environment variables

## Deployment Environments

### Development
- URL: dev.coupit.com
- Branch: develop
- Auto-deploy on push

### Staging
- URL: staging.coupit.com
- Branch: staging
- Manual deployment

### Production
- URL: coupit.com
- Branch: main
- Manual deployment with approval

## Deployment Methods

### Docker Deployment

1. Build Docker images:
```bash
docker-compose build
```

2. Push to registry:
```bash
docker-compose push
```

3. Deploy:
```bash
docker-compose up -d
```

### Kubernetes Deployment

1. Update configuration:
```bash
kubectl apply -f k8s/
```

2. Deploy:
```bash
kubectl rollout restart deployment/coupit
```

### AWS Deployment

1. Configure AWS:
```bash
aws configure
```

2. Deploy using CloudFormation:
```bash
aws cloudformation deploy --template-file template.yaml --stack-name coupit
```

## Environment Configuration

### Required Environment Variables

```bash
# Database
DATABASE_URL=postgresql://user:password@host:5432/db

# Authentication
JWT_SECRET=your-secret-key
JWT_EXPIRATION=24h

# API
API_PORT=8000
API_URL=https://api.coupit.com

# Frontend
REACT_APP_API_URL=https://api.coupit.com
```

## Database Migrations

1. Create migration:
```bash
npm run migration:create
```

2. Run migrations:
```bash
npm run migration:run
```

3. Rollback if needed:
```bash
npm run migration:rollback
```

## Monitoring and Logging

- Use CloudWatch for AWS deployments
- Use Prometheus and Grafana for Kubernetes
- Set up error tracking with Sentry
- Configure log aggregation

## Backup and Recovery

### Database Backups
- Daily automated backups
- Point-in-time recovery
- Backup retention: 30 days

### Application Backups
- Configuration backups
- Media file backups
- Backup verification process

## Rollback Procedure

1. Identify the issue
2. Stop the deployment
3. Rollback to previous version
4. Verify functionality
5. Document the incident

## Post-Deployment Checklist

- [ ] Verify all services are running
- [ ] Check database connections
- [ ] Test critical functionality
- [ ] Monitor error rates
- [ ] Verify backups
- [ ] Update documentation 