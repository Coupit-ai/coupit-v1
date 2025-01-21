package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.PaymentWebhookRequest
import com.webxela.backend.coupit.domain.model.SquarePayment

object PaymentMapper {
    fun PaymentWebhookRequest.toPayment(): SquarePayment {
        return SquarePayment(
            merchantId = this.merchantId,
            createdAt = this.data.objectX.payment.createdAt,
            amount = this.data.objectX.payment.totalMoney?.amount,
            currency = this.data.objectX.payment.totalMoney?.currency,
            paymentId = this.data.objectX.payment.id,
            locationId = this.data.objectX.payment.locationId,
            orderId = this.data.objectX.payment.orderId
        )
    }
}