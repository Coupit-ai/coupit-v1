package com.webxela.backend.coupit.domain.model

import java.time.Instant
import java.util.*

data class SpinSession(
    val id: UUID? = null,
    val payment: SquarePayment,
    val createdAt: Instant? = null,
    val merchant: SquareMerchant,
    val expiresAt: Instant? = null,
    val used: Boolean? = null
)