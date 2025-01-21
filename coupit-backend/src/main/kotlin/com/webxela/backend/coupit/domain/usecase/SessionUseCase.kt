package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.domain.repo.SessionRepository
import org.springframework.stereotype.Component


@Component
class SessionUseCase(private val sessionRepository: SessionRepository) {

    fun createSession(merchantId: String, paymentId: String): SpinSession {
        return sessionRepository.createSession(merchantId, paymentId)
    }

    fun getSessionByPaymentId(paymentId: String, merchantId: String): SpinSession? {
        return sessionRepository.getSession(paymentId, merchantId)
    }

    fun getSessionBySessionId(sessionId: String): SpinSession? {
        return sessionRepository.getSessionBySessionId(sessionId)
    }

    fun markSessionAsUsed(sessionId: String): Boolean {
        return sessionRepository.markSessionAsUsed(sessionId)
    }

    fun deleteSession(paymentId: String, merchantId: String): Boolean {
        return sessionRepository.deleteSession(paymentId, merchantId)
    }

}