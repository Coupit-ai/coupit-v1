package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.domain.repo.SessionRepository
import org.springframework.stereotype.Component
import java.util.*


@Component
class SessionUseCase(private val sessionRepository: SessionRepository) {

    fun createSession(merchantId: String, transactionId: String): Session {
        return sessionRepository.createSession(merchantId, transactionId)
    }

    fun getSessionByTransactionId(transactionId: String, merchantId: String): Session? {
        return sessionRepository.getSession(transactionId, merchantId)
    }

    fun getSessionBySessionId(sessionId: UUID): Session? {
        return sessionRepository.getSessionBySessionId(sessionId)
    }

    fun markSessionAsUsed(sessionId: UUID): Boolean {
        return sessionRepository.markSessionAsUsed(sessionId)
    }

    fun deleteSession(transactionId: String, merchantId: String): Boolean {
        return sessionRepository.deleteSession(transactionId, merchantId)
    }

}