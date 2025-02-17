package com.webxela.app.coupit.domain.model

data class Reward(
    val id: String?,
    val description: String,
    val probability: Double,
    val title: String,
    val validityHours: Int,
    val discountCode: String,
    val createdAt: String?
)