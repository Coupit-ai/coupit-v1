# Square OAuth Redirect Flow Guide

## Overview
After a merchant successfully connects their Square account through OAuth, they should be redirected to the appropriate page based on their account status and setup progress.

## Redirect Flow

### 1. Successful Connection
After successful Square OAuth:
```typescript
// Redirect to merchant dashboard if account exists
if (merchantExists) {
    redirect('/dashboard');
} else {
    // Redirect to initial setup if new merchant
    redirect('/setup');
}
```

### 2. Initial Setup Flow
New merchants should be guided through:
1. Business profile completion
2. Reward configuration
3. Location selection
4. Payment settings

### 3. Error Handling
Handle common OAuth errors with appropriate redirects:
```typescript
switch (error.type) {
    case 'ACCESS_DENIED':
        redirect('/connect/square?error=access_denied');
        break;
    case 'INVALID_STATE':
        redirect('/connect/square?error=invalid_state');
        break;
    case 'EXPIRED_TOKEN':
        redirect('/connect/square?error=expired');
        break;
    default:
        redirect('/connect/square?error=unknown');
}
```

## User Experience

### New Merchants
1. Redirect to `/setup`
2. Show welcome message
3. Guide through:
   - Business details
   - Reward creation
   - Location setup
   - Payment configuration

### Existing Merchants
1. Redirect to `/dashboard`
2. Show:
   - Active rewards
   - Recent transactions
   - Customer statistics
   - Quick actions

## Implementation Example

```typescript
// OAuth Callback Handler
async function handleSquareCallback(req, res) {
    try {
        const { code, state } = req.query;
        
        // Verify state to prevent CSRF
        if (!isValidState(state)) {
            return res.redirect('/connect/square?error=invalid_state');
        }

        // Exchange code for access token
        const tokens = await exchangeCodeForTokens(code);
        
        // Get merchant info from Square
        const merchant = await getMerchantInfo(tokens.access_token);
        
        // Check if merchant exists in our system
        const existingMerchant = await findMerchant(merchant.id);
        
        if (existingMerchant) {
            // Update tokens and redirect to dashboard
            await updateMerchantTokens(merchant.id, tokens);
            return res.redirect('/dashboard');
        } else {
            // Create new merchant and redirect to setup
            await createMerchant(merchant, tokens);
            return res.redirect('/setup');
        }
    } catch (error) {
        console.error('OAuth callback error:', error);
        return res.redirect('/connect/square?error=unknown');
    }
}
```

## Required Pages

### 1. Setup Page (`/setup`)
- Business information form
- Reward creation interface
- Location selection
- Payment settings
- Progress indicator

### 2. Dashboard (`/dashboard`)
- Overview of rewards
- Transaction history
- Customer statistics
- Quick actions
- Settings access

### 3. Error Pages
- Access denied
- Invalid state
- Expired token
- Unknown error

## Best Practices

1. **State Validation**
   - Always validate the state parameter
   - Prevent CSRF attacks
   - Maintain session security

2. **Error Handling**
   - Clear error messages
   - Recovery options
   - Logging for debugging

3. **User Experience**
   - Clear progress indicators
   - Helpful error messages
   - Easy recovery paths

4. **Security**
   - Secure token storage
   - HTTPS only
   - Session management

## Testing

Test the following scenarios:
1. Successful new merchant flow
2. Successful existing merchant flow
3. Access denied
4. Invalid state
5. Expired token
6. Network errors
7. Invalid permissions

## Monitoring

Monitor these metrics:
1. OAuth success rate
2. Setup completion rate
3. Error frequency
4. Redirect timing
5. User drop-off points

## Support

If merchants encounter issues:
1. Clear error messages
2. Support contact information
3. Troubleshooting guides
4. Re-authentication options 