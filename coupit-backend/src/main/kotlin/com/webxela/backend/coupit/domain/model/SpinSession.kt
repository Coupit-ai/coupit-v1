package com.webxela.backend.coupit.domain.model

import com.webxela.backend.coupit.domain.enum.SessionState
import java.time.Instant
import java.util.UUID

data class SpinSession(
    val id: UUID? = null,
    val payment: SquarePayment,
    val createdAt: Instant? = null,
    val merchant: SquareMerchant,
    val expiresAt: Instant? = null,
    val sessionState: SessionState? = null,
    val spin: SpinResult? = null
)