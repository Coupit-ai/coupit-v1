package com.webxela.backend.coupit.api.dto

data class RewardRequest(
    val title: String,
    val description: String,
    val probability: Double,
    val validityHours: Int
)
