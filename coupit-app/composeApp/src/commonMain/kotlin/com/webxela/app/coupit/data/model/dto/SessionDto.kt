package com.webxela.app.coupit.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    val message: String,
    val statusCode: Int,
    val timeStamp: String,
    val error: String? = null,
    val data: Data? = null
) {
    @Serializable
    data class Data(
        val expiresAt: String,
        val merchantId: String,
        val sessionId: String,
        val timeStamp: String,
        val transactionId: String,
        val used: Boolean = false,
        val offers: List<Offer>
    ) {
        @Serializable
        data class Offer(
            val description: String,
            val offerId: String,
            val timeStamp: String,
            val title: String
        )
    }
}