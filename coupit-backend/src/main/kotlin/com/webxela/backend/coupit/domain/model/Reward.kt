package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.UUID

data class Reward(
    val id: Long,
    val rewardId: UUID,
    val timeStamp: Instant,
    val title: String,
    val description: String
)
