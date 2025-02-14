package com.webxela.backend.coupit.rest.mappper

import com.webxela.backend.coupit.rest.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.domain.model.SquarePayment

object PaymentDtoMapper {
    fun PaymentWebhookRequest.toPayment(merchant: SquareMerchant): SquarePayment {
        return SquarePayment(
            id = this.data.objectX.payment.id,
            merchant = merchant,
            createdAt = this.data.objectX.payment.createdAt,
            amount = this.data.objectX.payment.totalMoney?.amount,
            currency = this.data.objectX.payment.totalMoney?.currency,
            locationId = this.data.objectX.payment.locationId,
            orderId = this.data.objectX.payment.orderId
        )
    }
}