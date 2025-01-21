package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant

data class SpinResponse(
    val id: String,
    val merchantId: String,
    val sessionId: String,
    val timeStamp: Instant,
    val offer: RewardResponse,
    val qrCode: String,
    val expiresAt: Instant,
    val claimed: Boolean
)
