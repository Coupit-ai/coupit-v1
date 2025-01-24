package com.webxela.backend.coupit.infra.persistence.repo

import com.webxela.backend.coupit.infra.persistence.entity.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SessionJpaRepo : JpaRepository<SessionEntity, UUID> {

    fun findByPaymentId(paymentId: String): SessionEntity?
}