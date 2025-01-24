package com.webxela.backend.coupit.infra.persistence.repo

import com.webxela.backend.coupit.infra.persistence.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentJpaRepo: JpaRepository<PaymentEntity, String>