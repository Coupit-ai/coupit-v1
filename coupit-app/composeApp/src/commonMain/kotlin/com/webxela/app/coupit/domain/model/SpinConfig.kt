package com.webxela.app.coupit.domain.model

data class SpinConfig(
    val session: Session,
    val rewards: List<Reward>
) {
    data class Session(
        val id: String,
        val createdAt: String,
        val expiresAt: String,
        val used: Boolean
    )
    data class Reward(
        val id: String,
        val description: String,
        val probability: Double,
        val title: String,
        val validityHours: Int
    )
}