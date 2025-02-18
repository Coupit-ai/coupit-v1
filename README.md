# Coupit

## Project Overview
Coupit is a customer incentive platform designed to enhance the shopping experience by rewarding customers through a gamified system. The platform integrates with POS (Point of Sale) systems to allow customers to spin a digital wheel after making a purchase, providing them with random rewards. These rewards are then stored digitally in the customer's wallet for future redemption.

## Technology Stack

### Frontend (Mobile Application)
- **KMP (Kotlin Multiplatform Mobile)**: For shared business logic.
- **CMP (Compose Multiplatform)**: For creating a consistent, cross-platform UI experience on both Android and iOS.

### Backend
- **Spring Boot**: Framework for building robust backend services.
- **Kotlin & Java**: Primary languages for backend development.
- **PostgreSQL**: Relational database management system for reliable data storage.
- **JWT Token System**: Secure token-based authentication mechanism ensuring API and data security.
- **REST APIs**: Communication between frontend and backend services.
- **Random Reward Generation on Server**: The reward is generated on the server side to prevent manipulation and ensure fair, secure distribution of rewards.

### Analytics & Dashboard
- **Full Analytics Dashboard**: A complete working dashboard for store owners featuring real-time analytics.
- **Detailed Purchase Stats**: Comprehensive statistics on store purchases, user engagement, and reward redemptions.

## System Flow

1. **Customer Purchase:**
   - A customer completes a purchase at a participating retail store.

2. **Spin Wheel Activation:**
   - After the payment is processed, a digital spin wheel is activated on the POS system.
   - The customer is offered the chance to spin the wheel.

3. **Server-Side Reward Determination:**
   - Once the wheel is spun, the reward is determined on the server using a secure random generation algorithm to ensure fairness and security.
   - The determined reward is then stored in the backend associated with the transaction.

4. **QR Code Generation:**
   - A unique QR code is generated for the reward.
   - The QR code is displayed on the POS system for the customer to scan.

5. **Reward Redemption:**
   - The customer scans the QR code using their iPhone.
   - Upon scanning, they are prompted to save the reward as a digital pass in their Apple Wallet.
   - The QR code system incorporates a limited validity period to mitigate misuse.

6. **Future Purchase & Reward Usage:**
   - When the customer returns to the store, they can redeem the stored reward during subsequent purchases.

## Security Considerations
- **Secure API Calls:** All API requests are authenticated using JWT tokens, ensuring that all interactions are secure and encrypted.
- **JWT Token-Based Authentication:** Ensures secure and stateless authentication for both customers and store operators.
- **QR Code Expiry:** Each QR code generated for rewards has a built-in expiry to prevent fraud and misuse.
- **Server-Side Reward Generation:** All random reward selections are handled on the server, reducing potential client-side tampering and ensuring fairness.

## Scalability & Deployment
- **Cloud-Hosted Backend:** Enables high availability and scalability to support a growing user base.
- **CI/CD Pipelines:** For continuous integration and automated deployment, ensuring rapid and stable releases.
- **Load Balancing:** Deployed to ensure smooth performance during high traffic periods.

## Dashboard & Analytics
- **Comprehensive Dashboard:** A full-featured dashboard provides real-time insights into store purchases, reward redemptions, and customer engagement.
- **Detailed Analytics:** Enables store owners to track trends, measure campaign effectiveness, and optimize promotional strategies based on live data.


