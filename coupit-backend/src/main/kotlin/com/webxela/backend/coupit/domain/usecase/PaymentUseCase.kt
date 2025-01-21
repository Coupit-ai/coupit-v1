package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.SquarePayment
import com.webxela.backend.coupit.domain.repo.PaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentUseCase(private val paymentRepository: PaymentRepository) {

    fun savePayment(payment: SquarePayment): SquarePayment {
        return paymentRepository.savePayment(payment)
    }

    fun retrievePayment(paymentId: String): SquarePayment? {
        return paymentRepository.retrievePayment(paymentId)
    }
}