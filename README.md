# Coupit

Coupit is a full-stack rewards and payment platform integrating Square authentication, a mobile app, and a backend with PostgreSQL. This repository contains all code and documentation for backend, frontend (Android/iOS), and supporting scripts.

---

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Backend Setup](#backend-setup)
- [Frontend (Mobile App) Setup](#frontend-mobile-app-setup)
- [Testing](#testing)
- [Documentation](#documentation)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- Square OAuth authentication
- Rewards management and spin wheel
- Payment processing (mock and real)
- QR code and Apple Wallet integration
- Admin panel for rewards
- PostgreSQL database with Docker
- Android/iOS mobile app (Kotlin Multiplatform)

---

## Architecture

- **Backend:** Spring Boot (Kotlin), PostgreSQL, Docker, Adminer
- **Frontend:** Android/iOS app (Kotlin Multiplatform, Jetpack Compose)
- **Communication:** REST API
- **Docs:** Markdown files in `/docs`

See [`docs/architecture.md`](docs/architecture.md) for a detailed diagram and explanation.

---

## System Requirements

- **Backend:**
  - JDK 17+
  - Docker & Docker Compose
  - PostgreSQL 14+
  - 4GB RAM, 10GB disk
- **Frontend:**
  - Android Studio (latest)
  - Android SDK
  - Gradle 8.12+
  - 8GB RAM

---

## Installation

See [`docs/installation.md`](docs/installation.md) for full details.

Quick steps:
```bash
git clone <repo-url>
cd coupit
```

---

## Backend Setup

1. **Database & Adminer:**
   ```bash
   cd coupit-backend
   cat > .env << EOL
POSTGRES_USER=coupit
POSTGRES_PASSWORD=coupit123
POSTGRES_DB=coupit
EOL
   docker-compose up -d
   ```
2. **Run Backend:**
   ```bash
   ./gradlew bootRun
   ```
3. **Health Check:**
   ```bash
   curl http://localhost:8080/health
   ```

---

## Frontend (Mobile App) Setup

1. **Android:**
   ```bash
   cd coupit-app
   echo "sdk.dir=/path/to/your/android/sdk" > local.properties
   ./gradlew :composeApp:installDebug
   ```
2. **iOS:**
   See [`docs/ios-setup.md`](docs/ios-setup.md)

---

## Testing

See [`docs/testing.md`](docs/testing.md) for full end-to-end testing instructions, including:
- Square account setup
- App login
- Rewards creation
- Mock payment
- Spin wheel and QR code

---

## Documentation

- [API Reference](docs/api.md)
- [Architecture](docs/architecture.md)
- [Android Setup](docs/android-setup.md)
- [iOS Setup](docs/ios-setup.md)
- [Deployment](docs/deployment.md)
- [Security](docs/security.md)
- [Troubleshooting](docs/troubleshooting.md)
- [GitHub SSH & Push Guide](docs/github-ssh-push.md)

---

## Troubleshooting

See [`docs/troubleshooting.md`](docs/troubleshooting.md) for solutions to common issues (build, database, authentication, etc).

---

## Contributing

See [`docs/contributing.md`](docs/contributing.md) for guidelines.

---

## License

[MIT](LICENSE)


