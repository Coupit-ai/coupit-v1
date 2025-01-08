package com.webxela.app.coupit.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SpinResultDto(
    val message: String,
    val statusCode: Int,
    val timeStamp: String,
    val data: Data
) {
    @Serializable
    data class Data(
        val claimed: Boolean,
        val expiresAt: String,
        val merchantId: String,
        val qrCode: String,
        val sessionId: String,
        val spinId: String,
        val timeStamp: String,
        val offer: Offer
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