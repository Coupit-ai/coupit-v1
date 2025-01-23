package com.webxela.backend.coupit.domain.model

import com.webxela.backend.coupit.common.enum.RewardState
import java.time.Instant

data class SpinResult(
    val id: String,
    val session: SpinSession,
    val createdAt: Instant,
    val reward: Reward,
    val qrCode: String,
    val expiresAt: Instant,
    val rewardState: RewardState
)
