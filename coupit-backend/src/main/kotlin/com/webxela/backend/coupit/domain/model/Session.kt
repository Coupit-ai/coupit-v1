package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.*

data class Session(
    val id: Long,
    val sessionId: UUID,
    val merchantId: String,
    val transactionId: String,
    val expiresAt: Instant,
    val used: Boolean
)