package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.*

data class SpinResult(
    val id: Long,
    val spinId: UUID,
    val merchantId: String,
    val sessionId: UUID,
    val timeStamp: Instant,
    val offer: Offer,
    val qrCode: String,
    val expiresAt: Instant,
    val claimed: Boolean
)
