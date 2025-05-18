# iOS Development Setup

## Prerequisites

### Hardware Requirements
- Mac computer (required for iOS development)
- iOS device (optional, for testing)
- Apple Developer Account

### Software Requirements
- macOS 12.0 or later
- Xcode 14.0 or later
- CocoaPods
- Node.js and npm
- Watchman (recommended)

## Installation Steps

### 1. Install Xcode
1. Download Xcode from the Mac App Store
2. Install Command Line Tools:
```bash
xcode-select --install
```

### 2. Install CocoaPods
```bash
sudo gem install cocoapods
```

### 3. Install Node.js and npm
```bash
# Using Homebrew
brew install node
```

### 4. Install Watchman
```bash
brew install watchman
```

## Project Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-organization/coupit.git
cd coupit/coupit-app
```

### 2. Install Dependencies
```bash
npm install
cd ios
pod install
```

### 3. Configure Environment
1. Copy `.env.example` to `.env`
2. Update the following variables:
   - API_URL
   - APP_ID
   - OTHER_ENV_VARIABLES

### 4. Open Xcode Project
```bash
open Coupit.xcworkspace
```

## Configuration

### 1. Signing & Capabilities
1. Select your target in Xcode
2. Go to "Signing & Capabilities"
3. Select your team
4. Enable required capabilities:
   - Push Notifications
   - Background Modes
   - Keychain Sharing

### 2. Info.plist Configuration
Add the following keys:
```xml
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsArbitraryLoads</key>
    <true/>
</dict>
<key>NSCameraUsageDescription</key>
<string>Camera access is required for profile pictures</string>
```

## Running the App

### Simulator
1. Select a simulator from Xcode
2. Click the Run button (⌘R)
3. Wait for the build to complete

### Physical Device
1. Connect your iOS device
2. Select your device from Xcode
3. Click the Run button (⌘R)
4. Trust the developer certificate on your device

## Debugging

### React Native Debugger
```bash
npm install -g react-native-debugger
react-native-debugger
```

### Xcode Debugging
- Use Xcode's debugger
- Set breakpoints
- View console logs
- Inspect memory usage

## Common Issues

### Build Errors
- Clean build folder: `⌘⇧K`
- Clean build: `⌘K`
- Reset simulator: `⌘⇧K`

### Pod Installation Issues
```bash
cd ios
pod deintegrate
pod cache clean --all
pod install
```

### Certificate Issues
1. Open Keychain Access
2. Delete expired certificates
3. Download new certificates from Apple Developer Portal

## Testing

### Unit Tests
```bash
npm test
```

### UI Tests
1. Open Xcode
2. Select test target
3. Run tests (⌘U)

## Deployment

### App Store Preparation
1. Update version number
2. Update build number
3. Create archive
4. Submit to App Store

### Beta Testing
1. Configure TestFlight
2. Upload build
3. Invite testers
4. Monitor feedback 