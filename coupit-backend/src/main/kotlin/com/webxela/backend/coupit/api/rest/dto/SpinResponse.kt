package com.webxela.backend.coupit.api.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.*

data class SpinResponse(
    @JsonProperty("spin_id")
    val spinId: UUID,
    @JsonProperty("merchant_id")
    val merchantId: String,
    @JsonProperty("session_id")
    val sessionId: UUID,
    @JsonProperty("timestamp")
    val timeStamp: Instant,
    @JsonProperty("offer")
    val offer: OfferResponse,
    @JsonProperty("qr_code")
    val qrCode: String,
    @JsonProperty("expires_at")
    val expiresAt: Instant,
    @JsonProperty("claimed")
    val claimed: Boolean
)
