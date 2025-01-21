package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity

object SessionEntityMapper {

    fun SessionEntity.toSession(): SpinSession {
        return SpinSession(
            id = this.id,
            sessionId = this.sessionId,
            merchantId = this.merchantId,
            paymentId = this.paymentId,
            createdAt = this.createdAt,
            expiresAt = this.expiresAt,
            used = this.used,
        )
    }
}

