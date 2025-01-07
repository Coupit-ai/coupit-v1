package com.webxela.app.coupit.data.model.dto

import kotlinx.serialization.Serializable


@Serializable
data class SessionRequest(
    val merchantId: String,
    val transactionId: String
)