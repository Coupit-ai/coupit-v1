package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant

data class SessionResponse(
    val id: String,
    val merchantId: String,
    val paymentId: String,
    val createdAt: Instant?,
    val expiresAt: Instant,
    val used: Boolean,
    val offers: List<RewardResponse>
)