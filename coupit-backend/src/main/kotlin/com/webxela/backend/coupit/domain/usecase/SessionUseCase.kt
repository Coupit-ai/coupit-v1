package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.domain.repo.SessionRepo
import org.springframework.stereotype.Component
import java.util.*


@Component
class SessionUseCase(
    private val sessionRepo: SessionRepo
) {

    fun createSession(merchantId: String, transactionId: String): Session {
        return sessionRepo.createSession(merchantId, transactionId)
    }

    fun getSessionByTransactionId(transactionId: String, merchantId: String): Session? {
        return sessionRepo.getSession(transactionId, merchantId)
    }

    fun getSessionBySessionId(sessionId: UUID): Session? {
        return sessionRepo.getSessionBySessionId(sessionId)
    }

    fun markSessionAsUsed(sessionId: UUID): Boolean {
        return sessionRepo.markSessionAsUsed(sessionId)
    }

    fun deleteSession(transactionId: String, merchantId: String): Boolean {
        return sessionRepo.deleteSession(transactionId, merchantId)
    }

}