package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.infrastructure.persistence.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentJpaRepo: JpaRepository<PaymentEntity, Long> {

    fun findByPaymentId(paymentId: String): PaymentEntity?
}