package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SessionJpaRepo : JpaRepository<SessionEntity, Long> {

    fun findByPaymentIdAndMerchantId(paymentId: String, merchantId: String): SessionEntity?
    fun findBySessionId(sessionId: String): SessionEntity?

    @Modifying
    @Query(
        """UPDATE SessionEntity s SET s.used = true 
            WHERE s.sessionId = :sessionId AND s.used = false"""
    )
    fun markSessionAsUsed(sessionId: String): Int
    fun deleteByPaymentIdAndMerchantId(paymentId: String, merchantId: String): Int

}