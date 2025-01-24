package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.model.SquarePayment
import com.webxela.backend.coupit.infra.persistence.mapper.PaymentEntityMapper.toPaymentEntity
import com.webxela.backend.coupit.infra.persistence.mapper.PaymentEntityMapper.toSquarePayment
import com.webxela.backend.coupit.infra.persistence.repo.PaymentJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class PaymentRepoAdapter(private val paymentJpaRepo: PaymentJpaRepo) {

    @Transactional
    fun savePayment(payment: SquarePayment): SquarePayment? {
        return paymentJpaRepo.saveAndFlush(payment.toPaymentEntity()).toSquarePayment()
    }

}