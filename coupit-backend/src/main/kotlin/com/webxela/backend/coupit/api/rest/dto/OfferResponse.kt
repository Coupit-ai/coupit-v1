package com.webxela.backend.coupit.api.rest.dto

import java.time.Instant
import java.util.UUID

data class OfferResponse(
    val offerId: UUID,
    val timeStamp: Instant,
    val title: String,
    val description: String
)
