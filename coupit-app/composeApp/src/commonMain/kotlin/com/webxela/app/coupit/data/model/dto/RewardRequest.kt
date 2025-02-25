package com.webxela.app.coupit.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RewardRequest(
    val description: String,
    val probability: Double,
    val title: String,
    val validityHours: Int,
    val discountCode: String
)