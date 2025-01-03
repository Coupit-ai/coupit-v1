package com.webxela.backend.coupit.api.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.UUID

data class OfferResponse(
    @JsonProperty("offer_id")
    val offerId: UUID,
    @JsonProperty("timestamp")
    val timeStamp: Instant,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String
)
