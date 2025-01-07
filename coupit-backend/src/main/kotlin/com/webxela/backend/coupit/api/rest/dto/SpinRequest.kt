package com.webxela.backend.coupit.api.rest.dto

import java.util.UUID

data class SpinRequest(
    val merchantId: String,
    val sessionId: UUID
)
