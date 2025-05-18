# Coupit Testing Guide

## Prerequisites

Before starting the testing process, ensure you have:
- A Square Developer account
- Android device with Coupit app installed
- Python 3.x installed (for mock payment testing)
- Git installed
- Access to the Coupit repository

## Testing Steps

### 1. Square Developer Account Setup

1. Go to [Square Developer Dashboard](https://developer.squareup.com/apps)
2. Click "New Application"
3. Fill in the application details:
   - Application Name: "Coupit Test"
   - Description: "Testing Coupit integration"
4. Save the application
5. Note down your:
   - Application ID
   - Location ID
   - Access Token

### 2. Coupit App Setup

1. Open the Coupit app on your Android device
2. Click "Login with Square"
3. Enter your Square Developer credentials
4. Verify successful login
5. Note down your Merchant ID (displayed in the app)

### 3. Rewards Configuration

1. In the Coupit app, navigate to the Rewards section
2. Click "Add New Reward"
3. Create at least two rewards with the following details:
   - Title (e.g., "10% Discount", "Free Item")
   - Description
   - Probability (percentage)
   - Validity hours
   - Discount code
4. Save each reward
5. Verify rewards are listed in the Rewards section

### 4. Backend Setup

1. Clone the repository:
```bash
git clone [repository-url]
cd coupit
```

2. Switch to backend branch:
```bash
git checkout backend
```

3. Navigate to backend directory:
```bash
cd coupit-backend
```

4. Start the backend services:
```bash
# Start PostgreSQL and Adminer
docker-compose up -d

# Start the backend application
./gradlew bootRun
```

5. Verify backend is running:
```bash
curl http://localhost:8080/health
```

### 5. Mock Payment Testing

1. Navigate to the mock payment script:
```bash
cd coupit-backend
```

2. Locate `mock-payment.py`

3. Run the script:
```bash
python3 mock-payment.py
```

4. When prompted, enter your Merchant ID (from step 2)

5. Verify the payment simulation:
   - Check backend logs for payment confirmation
   - Verify FCM notification received on device

### 6. Spin Wheel Testing

1. After successful payment:
   - Verify spin wheel UI appears
   - Check wheel animations
   - Verify reward segments match configured rewards

2. Test spin functionality:
   - Click spin button
   - Verify wheel rotation
   - Check reward selection animation

### 7. Reward Redemption

1. After spin completes:
   - Verify reward screen appears
   - Check QR code generation
   - Verify reward details match selected reward

2. Test QR code:
   - Open camera app
   - Scan QR code
   - Verify Apple Wallet prompt
   - Confirm reward addition to wallet

### 8. Error Testing

1. Test invalid scenarios:
   - Payment with invalid amount
   - Spin without payment
   - Invalid merchant ID
   - Network disconnection

2. Verify error handling:
   - Appropriate error messages
   - Graceful recovery
   - User feedback

## Verification Checklist

- [ ] Square Developer account created
- [ ] Coupit app login successful
- [ ] At least two rewards configured
- [ ] Backend services running
- [ ] Mock payment successful
- [ ] Spin wheel functional
- [ ] QR code generated
- [ ] Reward added to Apple Wallet
- [ ] Error handling verified

## Common Issues and Solutions

### 1. Payment Issues
- **Issue**: Payment not processing
- **Solution**: Verify Square credentials and network connection

### 2. Spin Wheel Issues
- **Issue**: Wheel not appearing
- **Solution**: Check backend logs for payment confirmation

### 3. QR Code Issues
- **Issue**: QR code not scanning
- **Solution**: Ensure proper lighting and camera focus

### 4. Backend Issues
- **Issue**: Service not starting
- **Solution**: Check Docker containers and database connection

## Logging and Debugging

### Backend Logs
```bash
cd coupit-backend
./gradlew bootRun --console=plain
```

### Database Logs
```bash
docker logs coupit-backend_postgres_1
```

### Mobile App Logs
```bash
adb logcat | grep "coupit"
```

## Support

For additional help:
1. Check the troubleshooting guide
2. Review the API documentation
3. Contact the development team 