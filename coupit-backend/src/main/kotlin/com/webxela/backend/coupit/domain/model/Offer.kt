package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.UUID

data class Offer(
    val id: Long,
    val offerId: UUID,
    val timeStamp: Instant,
    val title: String,
    val description: String
)
