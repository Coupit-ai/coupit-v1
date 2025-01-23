package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SquarePayment
import com.webxela.backend.coupit.infrastructure.persistence.entity.PaymentEntity

object PaymentEntityMapper {

    fun PaymentEntity.toSquarePayment(): SquarePayment {
        return SquarePayment(
            id = this.id,
            merchantId = this.merchantId,
            amount = this.amount,
            currency = this.currency,
            createdAt = this.createdAt,
            locationId = this.locationId,
            orderId = this.orderId
        )
    }

    fun SquarePayment.toPaymentEntity(): PaymentEntity {
        return PaymentEntity(
            id = this.id,
            merchantId = this.merchantId,
            amount = this.amount,
            currency = this.currency,
            createdAt = this.createdAt,
            locationId = this.locationId,
            orderId = this.orderId
        )
    }
}