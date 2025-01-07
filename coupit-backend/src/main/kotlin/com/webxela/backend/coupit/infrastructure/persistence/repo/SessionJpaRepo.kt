package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SessionJpaRepo : JpaRepository<SessionEntity, Long> {

    fun findByTransactionIdAndMerchantId(transactionId: String, merchantId: String): SessionEntity?
    fun findBySessionId(sessionId: UUID): SessionEntity?

    @Modifying
    @Query(
        "UPDATE SessionEntity s SET s.used = true " +
                "WHERE s.sessionId = :sessionId AND s.used = false"
    )
    fun markSessionAsUsed(sessionId: UUID): Int
    fun deleteByTransactionIdAndMerchantId(transactionId: String, merchantId: String): Int

}