package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant
import java.util.UUID

data class RewardResponse(
    val id: UUID,
    val timeStamp: Instant,
    val title: String,
    val description: String
)
