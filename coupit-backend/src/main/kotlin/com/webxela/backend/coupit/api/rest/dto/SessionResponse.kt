package com.webxela.backend.coupit.api.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.webxela.backend.coupit.api.rest.dto.OfferResponse
import java.time.Instant
import java.util.*

data class SessionResponse(
    val sessionId: UUID,
    @JsonProperty("merchant_id")
    val merchantId: String,
    @JsonProperty("transaction_id")
    val transactionId: String,
    @JsonProperty("expires_at")
    val expiresAt: Instant,
    @JsonProperty("used")
    val used: Boolean,
    @JsonProperty("offers")
    val offers: List<OfferResponse>
)