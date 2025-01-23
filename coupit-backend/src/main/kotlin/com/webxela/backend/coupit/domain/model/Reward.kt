package com.webxela.backend.coupit.domain.model

data class Reward(
    val id: String,
    val merchant: SquareMerchant,
    val title: String,
    val description: String,
    val probability: Double,
    val validityHours: Int
)
