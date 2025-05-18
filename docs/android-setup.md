# Android Setup Guide

## Prerequisites

1. Android Studio (latest version)
2. JDK 17 or later
3. Android SDK
4. Gradle 8.12 or later

## Environment Setup

### 1. Android SDK Configuration

Create or update `local.properties` in the `coupit-app` directory:
```bash
sdk.dir=/path/to/your/android/sdk
```

### 2. Build Configuration

The project uses Gradle for building. Key configuration files:
- `build.gradle.kts` - Project-level build configuration
- `composeApp/build.gradle.kts` - App-level build configuration

## Building the App

### Debug Build
```bash
cd coupit-app
./gradlew :composeApp:installDebug
```

### Release Build
```bash
cd coupit-app
./gradlew :composeApp:assembleRelease
```

## Common Build Issues

### 1. SDK Location Not Found
Error: "SDK location not found. Define location with sdk.dir in the local.properties file"

Solution:
1. Create `local.properties` in the project root
2. Add SDK path:
```properties
sdk.dir=/path/to/your/android/sdk
```

### 2. Gradle Task Not Found
Error: "Task ':composeApp:androidDebug' not found"

Solution:
1. List available tasks:
```bash
./gradlew tasks
```
2. Use the correct task name:
```bash
./gradlew :composeApp:installDebug
```

### 3. Kotlin/Native Target Issues
Warning: "The following Kotlin/Native targets cannot be built on this machine and are disabled: iosArm64, iosSimulatorArm64, iosX64"

Solution:
Add to `gradle.properties`:
```properties
kotlin.native.ignoreDisabledTargets=true
```

## Development Setup

### 1. Clone the Repository
```bash
git clone [repository-url]
cd coupit-app
```

### 2. Open in Android Studio
1. Launch Android Studio
2. Select "Open an existing project"
3. Navigate to the `coupit-app` directory
4. Click "OK"

### 3. Sync Project
1. Click "Sync Project with Gradle Files"
2. Wait for the sync to complete

## Running the App

### 1. Using Android Studio
1. Select your target device/emulator
2. Click "Run" (green play button)

### 2. Using Command Line
```bash
./gradlew :composeApp:installDebug
```

## Testing

### 1. Unit Tests
```bash
./gradlew :composeApp:test
```

### 2. Instrumented Tests
```bash
./gradlew :composeApp:connectedAndroidTest
```

## Debugging

### 1. Logcat
```bash
adb logcat
```

### 2. Debug Configuration
1. Set breakpoints in Android Studio
2. Run in debug mode
3. Use the debug toolbar

## Dependencies

Key dependencies in `composeApp/build.gradle.kts`:
```kotlin
dependencies {
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.compose.runtime:runtime:1.5.0")
    // ... other dependencies
}
```

## Configuration

### 1. API Configuration
Update `local.properties`:
```properties
api.base.url=http://your-backend-url:8080
```

### 2. Square Integration
1. Add Square credentials to `local.properties`:
```properties
square.application.id=your-app-id
square.location.id=your-location-id
```

## Troubleshooting

### 1. Build Issues
- Clean project: `./gradlew clean`
- Invalidate caches in Android Studio
- Update Gradle version

### 2. Runtime Issues
- Check logcat for errors
- Verify API configuration
- Check network connectivity

### 3. Performance Issues
- Enable R8 optimization
- Use release builds for testing
- Profile the app using Android Studio

## Support

For additional help:
1. Check the troubleshooting guide
2. Review the API documentation
3. Contact the development team 