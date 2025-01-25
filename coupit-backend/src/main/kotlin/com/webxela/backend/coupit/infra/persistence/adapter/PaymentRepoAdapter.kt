package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.model.SquarePayment
import com.webxela.backend.coupit.infra.persistence.mapper.PaymentEntityMapper.toPaymentEntity
import com.webxela.backend.coupit.infra.persistence.mapper.PaymentEntityMapper.toSquarePayment
import com.webxela.backend.coupit.infra.persistence.repo.PaymentJpaRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentRepoAdapter(private val paymentJpaRepo: PaymentJpaRepo) {

    @Transactional(readOnly = false)
    fun savePayment(payment: SquarePayment): SquarePayment? {
        return paymentJpaRepo.saveAndFlush(payment.toPaymentEntity()).toSquarePayment()
    }

}