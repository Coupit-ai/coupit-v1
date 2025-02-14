package com.webxela.backend.coupit.rest.dto

import java.time.Instant
import java.util.UUID

data class SpinConfigResponse(
    val session: Session,
    val rewards: List<RewardResponse>
) {
    data class Session(
        val id: UUID,
        val createdAt: Instant,
        val expiresAt: Instant,
        val used: Boolean
    )
}