package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant
import java.util.*

data class SessionResponse(
    val id: UUID,
    val merchantId: String,
    val transactionId: String,
    val timeStamp: Instant?,
    val expiresAt: Instant,
    val used: Boolean,
    val offers: List<RewardResponse>
)