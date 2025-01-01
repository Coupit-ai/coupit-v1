package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Session
import com.webxela.backend.coupit.domain.repo.SessionRepo
import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSession
import com.webxela.backend.coupit.infrastructure.persistence.repo.SessionJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*


@Component
@Transactional
class SessionRepoAdapter(
    private val sessionJpaRepo: SessionJpaRepo
) : SessionRepo {

    override fun createSession(merchantId: String, transactionId: String): Session {
        val sessionEntity = SessionEntity (
            merchantId = merchantId,
            transactionId = transactionId
        )
        return sessionJpaRepo.save(sessionEntity).toSession()
    }

    override fun getSessionByTransactionId(transactionId: String): Session? {
        return sessionJpaRepo.findByTransactionId(transactionId)?.toSession()
    }

    override fun markSessionAsUsed(sessionId: UUID): Int {
        return sessionJpaRepo.markSessionAsUsed(sessionId)
    }

    override fun getSessionBySessionId(sessionId: UUID): Session? {
        return sessionJpaRepo.findBySessionId(sessionId)?.toSession()
    }

    override fun deleteSessionByTransactionId(transactionId: String): Boolean {
        return sessionJpaRepo.deleteByTransactionId(transactionId) > 0
    }
}