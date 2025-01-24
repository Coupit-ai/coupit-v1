package com.webxela.backend.coupit.api.dto

import com.webxela.backend.coupit.domain.enum.SessionState
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
        val sessionState: SessionState
    )
}