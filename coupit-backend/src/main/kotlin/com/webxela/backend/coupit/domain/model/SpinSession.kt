package com.webxela.backend.coupit.domain.model

import com.webxela.backend.coupit.common.enum.SessionState
import java.time.Instant

data class SpinSession(
    val id: String,
    val payment: SquarePayment,
    val createdAt: Instant,
    val merchant: SquareMerchant,
    val expiresAt: Instant,
    val sessionState: SessionState,
    val spin: SpinResult?
)