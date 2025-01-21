package com.webxela.backend.coupit.domain.model

import java.time.Instant

data class Reward(
    val id: Long? = null,
    val rewardId: String,
    val timeStamp: Instant,
    val title: String,
    val description: String
)
