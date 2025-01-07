package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant
import java.util.*

data class SpinResponse(
    val spinId: UUID,
    val merchantId: String,
    val sessionId: UUID,
    val timeStamp: Instant,
    val offer: OfferResponse,
    val qrCode: String,
    val expiresAt: Instant,
    val claimed: Boolean
)
