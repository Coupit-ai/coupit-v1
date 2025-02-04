package com.webxela.backend.coupit.api.dto

import java.time.Instant
import java.util.UUID

data class SpinResponse(
    val id: UUID,
    val createdAt: Instant,
    val reward: RewardResponse,
    val qrCode: String,
    val expiresAt: Instant,
    val claimed: Boolean
)
