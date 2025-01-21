package com.webxela.backend.coupit.api.rest.dto


data class SessionRequest(
    val merchantId: String,
    val paymentId: String
)