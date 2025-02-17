package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.UUID

data class Reward(
    val id: UUID? = null,
    val merchant: SquareMerchant,
    val title: String,
    val description: String,
    val probability: Double,
    val validityHours: Int,
    val discountCode: String,
    val createdAt: Instant? = null
)
