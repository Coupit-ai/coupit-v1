package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infra.persistence.mapper.SessionEntityMapper.toSessionEntity
import com.webxela.backend.coupit.infra.persistence.mapper.SessionEntityMapper.toSpinSession
import com.webxela.backend.coupit.infra.persistence.repo.SessionJpaRepo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class SessionRepoAdapter(private val sessionJpaRepo: SessionJpaRepo) {

    companion object {
        val logger: Logger = LogManager.getLogger(SessionRepoAdapter::class.java)
    }

    @Transactional
    fun createSession(session: SpinSession): SpinSession? {
        return sessionJpaRepo.save(session.toSessionEntity()).toSpinSession()
    }

    @Transactional(readOnly = true)
    fun getSession(sessionId: UUID): SpinSession? {
        return sessionJpaRepo.findById(sessionId).map { it.toSpinSession() }.orElse(null)
    }

    @Transactional(readOnly = true)
    fun getSessionByPaymentId(paymentId: String): SpinSession? {
        val session = sessionJpaRepo.findByPaymentId(paymentId)?.toSpinSession()
        sessionJpaRepo.flush()
        return session
    }

}