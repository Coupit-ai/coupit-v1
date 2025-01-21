package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.domain.repo.SessionRepository
import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSession
import com.webxela.backend.coupit.infrastructure.persistence.repo.SessionJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component


@Component
class SessionRepoAdapter(private val sessionJpaRepo: SessionJpaRepo) : SessionRepository {

    @Transactional
    override fun createSession(merchantId: String, paymentId: String): SpinSession {
        val sessionEntity = SessionEntity(
            merchantId = merchantId,
            paymentId = paymentId
        )
        return sessionJpaRepo.save(sessionEntity).toSession()
    }

    override fun getSession(paymentId: String, merchantId: String): SpinSession? {
        return sessionJpaRepo.findByPaymentIdAndMerchantId(paymentId, merchantId)?.toSession()
    }

    @Transactional
    override fun markSessionAsUsed(sessionId: String): Boolean {
        return when (sessionJpaRepo.markSessionAsUsed(sessionId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as used")
        }
    }

    override fun getSessionBySessionId(sessionId: String): SpinSession? {
        return sessionJpaRepo.findBySessionId(sessionId)?.toSession()
    }

    @Transactional
    override fun deleteSession(paymentId: String, merchantId: String): Boolean {
        return when (sessionJpaRepo.deleteByPaymentIdAndMerchantId(paymentId, merchantId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as deleted")
        }
    }
}