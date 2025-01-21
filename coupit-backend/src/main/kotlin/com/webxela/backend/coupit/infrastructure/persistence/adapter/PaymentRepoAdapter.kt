package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.SquarePayment
import com.webxela.backend.coupit.domain.repo.PaymentRepository
import com.webxela.backend.coupit.infrastructure.persistence.mapper.PaymentEntityMapper.toPaymentEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.PaymentEntityMapper.toSquarePayment
import com.webxela.backend.coupit.infrastructure.persistence.repo.PaymentJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class PaymentRepoAdapter(private val paymentJpaRepo: PaymentJpaRepo): PaymentRepository {

    @Transactional
    override fun savePayment(payment: SquarePayment): SquarePayment {
        return paymentJpaRepo.save(payment.toPaymentEntity()).toSquarePayment()
    }

    override fun retrievePayment(paymentId: String): SquarePayment? {
        return paymentJpaRepo.findByPaymentId(paymentId)?.toSquarePayment()
    }

}