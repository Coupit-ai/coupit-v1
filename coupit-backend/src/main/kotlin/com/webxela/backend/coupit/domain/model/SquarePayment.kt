package com.webxela.backend.coupit.domain.model

data class SquarePayment(
    val id: String,
    val merchantId: String,
    val createdAt: String?,
    val amount: String?,
    val currency: String?,
    val locationId: String?,
    val orderId: String?
)