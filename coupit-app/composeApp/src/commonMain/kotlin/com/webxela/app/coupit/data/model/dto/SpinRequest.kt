package com.webxela.app.coupit.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SpinRequest(
    val merchantId: String,
    val sessionId: String
)