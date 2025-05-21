# Square Integration Video Submission Guide

This guide outlines the requirements and best practices for creating the video submission required by Square's Partner Quality team for the Coupit application.

## Video Requirements

### Technical Specifications
- **Resolution**: 1080p or higher
- **Format**: MP4 or MOV
- **Length**: 2-5 minutes total
  - Introduction: 30 seconds
  - OAuth Flow: 1-2 minutes
  - Integration Points: 1-2 minutes each
- **Audio**: Clear narration with no background noise
- **Hosting**: YouTube, Loom, or Vimeo (private link acceptable)

### Content Structure

#### 1. Introduction (30 seconds)
- Show Coupit's home page (coupit.ai)
- Narrate:
  ```
  "Coupit is a rewards and loyalty platform that integrates with Square to process payments and manage customer rewards. We use Square's OAuth for authentication and the Payments API for processing transactions. Our platform allows merchants to create and manage rewards that customers can earn through purchases."
  ```

#### 2. OAuth Flow (1-2 minutes)
- Start from coupit.ai homepage
- Show the "Connect with Square" button
- Demonstrate the OAuth permission screen
- Show successful connection confirmation
- Include:
  - Merchant login process
  - Square connection setup
  - Initial account configuration
  - Reward setup process

#### 3. Integration Points (1-2 minutes each)

##### Payment Processing
1. Show merchant dashboard
2. Demonstrate creating a new reward
3. Show customer purchase flow:
   - Payment processing
   - Spin wheel activation
   - Reward generation
   - QR code display
4. Show Square Dashboard transaction confirmation

##### Reward Management
1. Show reward creation interface
2. Demonstrate reward configuration:
   - Probability settings
   - Validity period
   - Reward types
3. Show reward redemption process
4. Display Square integration for reward tracking

## Recording Guidelines

### Do's
- Use high-quality screen recording software
- Record in a quiet environment
- Speak clearly and at a moderate pace
- Show actual application functionality
- Include error handling demonstrations
- Show both success and edge cases

### Don'ts
- Include marketing content
- Show real customer data
- Display actual credit card information
- Include unrelated features
- Use background music
- Show sensitive information

## Recording Tools

### Windows
- Windows Game Bar (Win + G)
- OBS Studio
- Camtasia

### Mac
- QuickTime Player
- ScreenFlow
- OBS Studio

### Browser-based
- Loom
- Vimeo Record
- Screencast-O-Matic

## Submission Process

1. Record the video following the structure above
2. Upload to your chosen platform (YouTube/Loom/Vimeo)
3. Set video to private/unlisted
4. Prepare the sharing link
5. Submit through Square Developer Console
6. Include any necessary passwords in the submission

## Example Script

```
[Introduction]
"Welcome to Coupit, a rewards and loyalty platform that integrates with Square. Our platform allows merchants to create engaging reward programs for their customers. We use Square's OAuth for secure authentication and the Payments API for processing transactions."

[OAuth Flow]
"Let me show you how merchants connect their Square account to Coupit. First, they'll click the 'Connect with Square' button on our homepage. This initiates the OAuth flow, where merchants grant Coupit permission to access their Square account. After successful connection, merchants can set up their reward program."

[Integration Points]
"Now, let's look at how Coupit integrates with Square for payments and rewards. When a customer makes a purchase, we process the payment through Square, then activate our spin wheel feature. The reward is generated and displayed as a QR code, which customers can scan to save to their digital wallet."

[Reward Management]
"Merchants can create and manage rewards through our dashboard. They can set probability rates, validity periods, and reward types. All reward redemptions are tracked and synchronized with Square's system for accurate reporting."
```

## Technical Setup for Recording

1. **Environment Preparation**
   - Clear browser cache
   - Log out of all accounts
   - Prepare test data
   - Set up demo environment

2. **Recording Setup**
   - Set screen resolution to 1920x1080
   - Close unnecessary applications
   - Test microphone
   - Prepare script

3. **Post-Recording**
   - Review video quality
   - Check audio clarity
   - Verify all steps are visible
   - Trim unnecessary parts

## Support

If you need assistance with the video submission process, contact:
- Square Developer Support
- Coupit Technical Team
- Square Partner Quality Team

Remember to keep the video focused on the Square integration points and avoid any marketing content or sensitive information. 