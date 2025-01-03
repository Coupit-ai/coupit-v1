package com.webxela.backend.coupit.api.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class SpinRequest(
    @JsonProperty("merchant_id")
    val merchantId: String,
    @JsonProperty("session_id")
    val sessionId: UUID
)
