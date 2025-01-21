package com.webxela.backend.coupit.domain.model

import java.time.Instant

data class SpinResult(
    val id: Long? = null,
    val spinId: String,
    val merchantId: String,
    val sessionId: String,
    val createdAt: Instant,
    val reward: Reward,
    val qrCode: String,
    val expiresAt: Instant,
    val claimed: Boolean
)
