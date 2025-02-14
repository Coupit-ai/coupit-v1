package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.UUID

data class SpinResult(
    val id: UUID? = null,
    val session: SpinSession,
    val createdAt: Instant? = null,
    val reward: Reward,
    val expiresAt: Instant,
    val claimed: Boolean? = null
)
