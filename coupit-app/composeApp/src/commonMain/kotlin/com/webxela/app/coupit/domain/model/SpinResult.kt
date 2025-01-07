package com.webxela.app.coupit.domain.model


data class SpinResult(
    val message: String,
    val statusCode: Int,
    val timeStamp: String,
    val error: String?,
    val data: Data?
) {
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
        data class Offer(
            val description: String,
            val offerId: String,
            val timeStamp: String,
            val title: String
        )
    }
}