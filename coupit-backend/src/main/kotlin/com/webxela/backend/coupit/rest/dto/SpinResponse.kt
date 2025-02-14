package com.webxela.backend.coupit.rest.dto

import java.time.Instant
import java.util.UUID

data class SpinResponse(
    val id: UUID,
    val createdAt: Instant,
    val reward: RewardResponse,
    val expiresAt: Instant,
    val claimed: Boolean
)
