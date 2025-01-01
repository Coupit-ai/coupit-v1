package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity

object SessionEntityMapper {
    fun SessionEntity.toSession(): Session {
        return Session(
            id = this.id,
            sessionId = this.sessionId,
            merchantId = this.merchantId,
            transactionId = this.transactionId,
            expiresAt = this.expiresAt,
            used = this.used,
        )
    }
}

