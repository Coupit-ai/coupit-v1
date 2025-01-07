package com.webxela.app.coupit.domain.model

data class Session(
    val message: String,
    val statusCode: Int,
    val timeStamp: String,
    val error: String?,
    val data: Data?
) {
    data class Data(
        val expiresAt: String,
        val merchantId: String,
        val sessionId: String,
        val timeStamp: String,
        val transactionId: String,
        val used: Boolean = false,
        val offers: List<Offer>
    ) {
        data class Offer(
            val description: String,
            val offerId: String,
            val timeStamp: String,
            val title: String
        )
    }
}