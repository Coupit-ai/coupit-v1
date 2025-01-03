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
            timeStamp = this.timeStamp,
            expiresAt = this.expiresAt,
            used = this.used,
        )
    }

    fun Session.toSessionEntity(): SessionEntity {
        return SessionEntity(
            merchantId = this.merchantId,
            transactionId = this.transactionId,
        )
    }
}

