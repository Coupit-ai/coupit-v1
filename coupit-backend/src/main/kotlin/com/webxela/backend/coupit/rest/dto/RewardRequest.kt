package com.webxela.backend.coupit.rest.dto

data class RewardRequest(
    val title: String,
    val description: String,
    val probability: Double,
    val validityHours: Int,
    val discountCode: String
)
