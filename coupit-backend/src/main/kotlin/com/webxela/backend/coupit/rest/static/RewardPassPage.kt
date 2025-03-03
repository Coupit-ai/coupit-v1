package com.webxela.backend.coupit.rest.static

fun getRewardPassPage(passkitUrl: String): String {
    val html = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <title>Your Coupit Reward</title>
            <style>
                :root {
                    --primary-color: #0066cc;
                    --text-primary: #111827;
                    --text-secondary: #4b5563;
                    --background: #ffffff;
                    --card-bg: #f9f9fb;
                    --accent: #0074e4;
                }
                
                body {
                    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                    margin: 0;
                    padding: 0;
                    background: linear-gradient(135deg, #f5f7fa 0%, #e9ebee 100%);
                    min-height: 100vh;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    color: var(--text-primary);
                }
                
                .card {
                    width: 90%;
                    max-width: 360px;
                    background-color: var(--background);
                    border-radius: 20px;
                    box-shadow: 0 12px 28px rgba(0,0,0,0.1), 0 2px 8px rgba(0,0,0,0.05);
                    overflow: hidden;
                    margin: 20px 0;
                }
                
                .card-header {
                    background: linear-gradient(120deg, var(--primary-color), #0052a5);
                    padding: 24px 0;
                    text-align: center;
                    position: relative;
                    overflow: hidden;
                }
                
                .card-header::after {
                    content: '';
                    position: absolute;
                    bottom: -10px;
                    left: 0;
                    right: 0;
                    height: 20px;
                    background-color: var(--background);
                    border-radius: 50% 50% 0 0;
                }
                
                .logo {
                    display: inline-flex;
                    justify-content: center;
                    align-items: center;
                    width: 80px;
                    height: 80px;
                    background-color: white;
                    border-radius: 50%;
                    padding: 15px;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                    z-index: 2;
                    position: relative;
                }
                
                .logo img {
                    height: 45px;
                    width: auto;
                    filter: drop-shadow(0 2px 4px rgba(0,0,0,0.1));
                }
                
                .content {
                    padding: 32px 24px;
                    text-align: center;
                }
                
                h1 {
                    color: var(--text-primary);
                    margin: 0 0 16px;
                    font-size: 26px;
                    font-weight: 700;
                    letter-spacing: -0.4px;
                }
                
                p {
                    color: var(--text-secondary);
                    font-size: 17px;
                    line-height: 1.6;
                    margin-bottom: 32px;
                    padding: 0 12px;
                }
                
                .apple-wallet-button {
                    background-color: black;
                    color: white;
                    border: none;
                    padding: 16px 28px;
                    border-radius: 14px;
                    font-size: 16px;
                    font-weight: 600;
                    cursor: pointer;
                    display: inline-flex;
                    align-items: center;
                    box-shadow: 0 4px 14px rgba(0,0,0,0.15);
                    transition: all 0.3s ease;
                    -webkit-tap-highlight-color: transparent;
                }
                
                .apple-wallet-button:active {
                    transform: translateY(2px);
                    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                }
                
                .apple-wallet-button img {
                    margin-right: 12px;
                    height: 22px;
                    filter: brightness(1.1);
                }
                
                .footer {
                    margin-top: 40px;
                    font-size: 14px;
                    color: var(--text-secondary);
                    font-weight: 500;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }
                
                .footer::before,
                .footer::after {
                    content: '';
                    height: 1px;
                    width: 40px;
                    background-color: #e5e7eb;
                    margin: 0 12px;
                }
                
                .benefits {
                    display: flex;
                    justify-content: center;
                    gap: 20px;
                    margin: 24px 0;
                }
                
                .benefit {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    gap: 6px;
                }
                
                .benefit-icon {
                    width: 32px;
                    height: 32px;
                    background-color: #f0f7ff;
                    border-radius: 50%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    color: var(--primary-color);
                    font-weight: bold;
                    font-size: 16px;
                }
                
                .benefit-text {
                    font-size: 12px;
                    color: var(--text-secondary);
                }
            </style>
        </head>
        <body>
            <div class="card">
                <div class="card-header">
                    <div class="logo">
                        <img src="https://img.icons8.com/?size=100&id=dsJNsQLtyqOz&format=png&color=000000" alt="Reward Pass">
                    </div>
                </div>
                <div class="content">
                    <h1>Your Reward Pass</h1>
                    <p>Add your pass to Apple Wallet for instant access to exclusive rewards and benefits.</p>
                    
                    <div class="benefits">
                        <div class="benefit">
                            <div class="benefit-icon">%</div>
                            <div class="benefit-text">Discounts</div>
                        </div>
                        <div class="benefit">
                            <div class="benefit-icon">★</div>
                            <div class="benefit-text">Points</div>
                        </div>
                        <div class="benefit">
                            <div class="benefit-icon">⚡</div>
                            <div class="benefit-text">Fast Access</div>
                        </div>
                    </div>
                    
                    <button class="apple-wallet-button" onclick="window.location.href='${passkitUrl}'">
                        <img src="https://img.icons8.com/?size=100&id=30840&format=png&color=FFFFFF" alt="Apple Wallet">
                        Add to Apple Wallet
                    </button>
                    
                    <div class="footer">
                        © 2025 Coupit. All rights reserved. 
                    </div>
                </div>
            </div>
        </body>
        </html>
    """.trimIndent()
    return html
}