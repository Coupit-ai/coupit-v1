package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.SquarePayment

interface PaymentRepository {

    fun savePayment(payment: SquarePayment): SquarePayment
    fun retrievePayment(paymentId: String): SquarePayment?
}