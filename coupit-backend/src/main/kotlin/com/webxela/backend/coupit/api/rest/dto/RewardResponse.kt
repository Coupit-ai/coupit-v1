package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant

data class RewardResponse(
    val id: String,
    val timeStamp: Instant,
    val title: String,
    val description: String
)
