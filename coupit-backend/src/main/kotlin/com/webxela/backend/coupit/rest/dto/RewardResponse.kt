package com.webxela.backend.coupit.rest.dto

import java.util.*

data class RewardResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val probability: Double,
    val validityHours: Int,
    val discountCode: String
)