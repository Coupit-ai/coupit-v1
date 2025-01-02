package com.webxela.backend.coupit.domain.model

import java.util.UUID

data class Offer(
    val id: Long,
    val offerId: UUID,
    val title: String,
    val description: String
)
