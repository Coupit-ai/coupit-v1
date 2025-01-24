package com.webxela.backend.coupit.api.dto

import java.time.Instant

data class SpinResponse(
    val id: String,
    val merchantId: String, // not required
    val sessionId: String, // not required
    val timeStamp: Instant, // created at
    val offer: RewardResponse,
    val qrCode: String,
    val expiresAt: Instant,
    val claimed: Boolean
)
