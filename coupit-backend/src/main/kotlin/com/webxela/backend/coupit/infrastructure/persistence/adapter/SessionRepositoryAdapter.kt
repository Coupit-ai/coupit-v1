package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.domain.repo.SessionRepository
import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSession
import com.webxela.backend.coupit.infrastructure.persistence.repo.SessionJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*


@Component
class SessionRepositoryAdapter(private val sessionJpaRepo: SessionJpaRepo) : SessionRepository {

    @Transactional
    override fun createSession(merchantId: String, transactionId: String): Session {
        val sessionEntity = SessionEntity(
            merchantId = merchantId,
            transactionId = transactionId
        )
        return sessionJpaRepo.save(sessionEntity).toSession()
    }

    override fun getSession(transactionId: String, merchantId: String): Session? {
        return sessionJpaRepo.findByTransactionIdAndMerchantId(transactionId, merchantId)?.toSession()
    }

    @Transactional
    override fun markSessionAsUsed(sessionId: UUID): Boolean {
        return when (sessionJpaRepo.markSessionAsUsed(sessionId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as used")
        }
    }

    override fun getSessionBySessionId(sessionId: UUID): Session? {
        return sessionJpaRepo.findBySessionId(sessionId)?.toSession()
    }

    @Transactional
    override fun deleteSession(transactionId: String, merchantId: String): Boolean {
        return when (sessionJpaRepo.deleteByTransactionIdAndMerchantId(transactionId, merchantId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as deleted")
        }
    }
}