# API Documentation

## Base URL

```
https://api.coupit.com/v1
```

## Authentication

All API requests require authentication using JWT tokens.

### Headers

```
Authorization: Bearer <token>
Content-Type: application/json
```

## Endpoints

### Authentication

#### Login
```
POST /auth/login
```

Request Body:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "jwt_token",
  "user": {
    "id": "user_id",
    "email": "user@example.com",
    "name": "User Name"
  }
}
```

### Users

#### Get User Profile
```
GET /users/profile
```

Response:
```json
{
  "id": "user_id",
  "email": "user@example.com",
  "name": "User Name",
  "createdAt": "2023-01-01T00:00:00Z"
}
```

#### Update User Profile
```
PUT /users/profile
```

Request Body:
```json
{
  "name": "New Name",
  "email": "new@example.com"
}
```

### Transactions

#### Get Transactions
```
GET /transactions
```

Query Parameters:
- page (number)
- limit (number)
- startDate (date)
- endDate (date)

Response:
```json
{
  "data": [
    {
      "id": "transaction_id",
      "amount": 100.00,
      "type": "credit",
      "date": "2023-01-01T00:00:00Z"
    }
  ],
  "pagination": {
    "total": 100,
    "page": 1,
    "limit": 10
  }
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid input",
  "message": "Detailed error message"
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "Resource not found"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal Server Error",
  "message": "Something went wrong"
}
```

## Rate Limiting

- 100 requests per minute per IP
- 1000 requests per hour per user

## Versioning

API version is included in the URL path (e.g., `/v1/`). Breaking changes will result in a new version number. 