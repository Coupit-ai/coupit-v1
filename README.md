# Coupit

## Project Overview
Coupit is a customer incentive platform designed to enhance the shopping experience by rewarding customers through a gamified system. The platform integrates with POS (Point of Sale) systems to allow customers to spin a digital wheel after making a purchase, providing them with random rewards. These rewards are then stored digitally in the customer's wallet for future redemption.

## Technology Stack
### Frontend (Mobile Application):
- **KMP (Kotlin Multiplatform Mobile)** for shared business logic.
- **CMP (Compose Multiplatform)** for a cross-platform UI experience on both Android and iOS.

### Backend:
- **Spring Boot** framework for backend services.
- **Kotlin & Java** for backend development.
- **REST APIs** for communication between the frontend and backend.
- **Database:** PostgreSQL Db.

## System Flow
1. **Customer Purchase:**
   - A customer completes a purchase at a participating retail store.
   
2. **Spin Wheel Activation:**
   - After payment is processed, a digital spin wheel appears on the POS system.
   - The customer is given the option to spin the wheel.
   
3. **Reward Determination:**
   - The wheel stops on a randomly selected reward.
   - The reward details are stored in the backend and associated with the transaction.
   
4. **QR Code Generation:**
   - A QR code is generated for the reward.
   - The QR code is displayed on the POS screen for the customer to scan.
   
5. **Reward Redemption:**
   - The customer scans the QR code using their iPhone.
   - They are prompted to save the reward as a digital pass in their Apple Wallet.
   
6. **Future Purchase & Reward Usage:**
   - When the customer returns to the store, they can redeem their stored reward during their next purchase.

## Reward Types
- **Buy One Get One Free (BOGO)**
- **20% Off on Any Purchase**
- **15% Off When Buying Two Items**
- **Other promotional discounts as configured by store owners**

## Security Considerations
- **Secure API Calls:** All API requests between the frontend and backend are authenticated and encrypted.
- **Token-based Authentication:** Secure authentication for both customers and store operators.
- **QR Code Expiry:** QR codes for rewards have a limited validity period to prevent misuse.

## Scalability & Deployment
- **Cloud-hosted backend** for high availability and scalability.
- **CI/CD pipelines** for continuous integration and deployment.
- **Load balancing** to ensure smooth performance even under high traffic.

## Future Enhancements
- **Android Wallet Integration** for Android users.
- **Loyalty Points System** in addition to the spin wheel rewards.
- **Data Analytics Dashboard** for merchants to track reward redemptions and customer engagement.



