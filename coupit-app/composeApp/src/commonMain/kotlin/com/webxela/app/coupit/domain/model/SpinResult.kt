package com.webxela.app.coupit.domain.model


data class SpinResult(
    val id: String,
    val claimed: Boolean,
    val createdAt: String,
    val expiresAt: String,
    val qrCode: String,
    val reward: Reward
) {
    data class Reward(
        val id: String,
        val description: String,
        val probability: Double,
        val title: String,
        val validityHours: Int
    )
}