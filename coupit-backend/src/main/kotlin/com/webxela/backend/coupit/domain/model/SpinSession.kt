package com.webxela.backend.coupit.domain.model

import java.time.Instant

data class SpinSession(
    val id: Long? = null,
    val createdAt: Instant,
    val sessionId: String,
    val merchantId: String,
    val paymentId: String,
    val expiresAt: Instant,
    val used: Boolean
)