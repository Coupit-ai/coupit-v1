package com.webxela.backend.coupit.domain.model

import com.webxela.backend.coupit.domain.enum.RewardState
import java.time.Instant
import java.util.UUID

data class SpinResult(
    val id: UUID? = null,
    val session: SpinSession,
    val createdAt: Instant,
    val reward: Reward,
    val qrCode: String,
    val expiresAt: Instant,
    val rewardState: RewardState
)
