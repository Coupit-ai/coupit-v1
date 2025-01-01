package com.webxela.backend.coupit.api.rest.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SessionRequest(
    @JsonProperty("merchant_id")
    val merchantId: String,
    @JsonProperty("transaction_id")
    val transactionId: String
)