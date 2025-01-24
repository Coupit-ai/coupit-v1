package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.SquarePayment
import com.webxela.backend.coupit.infra.persistence.entity.PaymentEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toSquareMerchant

object PaymentEntityMapper {

    fun PaymentEntity.toSquarePayment(): SquarePayment {
        return SquarePayment(
            id = this.id,
            merchant = this.merchant.toSquareMerchant(),
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
            merchant = this.merchant.toMerchantEntity(),
            amount = this.amount,
            currency = this.currency,
            createdAt = this.createdAt,
            locationId = this.locationId,
            orderId = this.orderId
        )
    }
}