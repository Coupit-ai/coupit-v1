package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infra.persistence.entity.SessionEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toSquareMerchant
import com.webxela.backend.coupit.infra.persistence.mapper.PaymentEntityMapper.toPaymentEntity
import com.webxela.backend.coupit.infra.persistence.mapper.PaymentEntityMapper.toSquarePayment
import com.webxela.backend.coupit.infra.persistence.mapper.SpinEntityMapper.toSpinResult

object SessionEntityMapper {

    fun SessionEntity.toSpinSession(): SpinSession {
        return SpinSession(
            id = this.id,
            createdAt = this.createdAt,
            expiresAt = this.expiresAt,
            sessionState = this.sessionState,
            spin = this.spin?.toSpinResult(),
            payment = this.payment.toSquarePayment(),
            merchant = this.merchant.toSquareMerchant()
        )
    }

    fun SpinSession.toSessionEntity(): SessionEntity {
        return SessionEntity(
            payment = this.payment.toPaymentEntity(),
            merchant = this.merchant.toMerchantEntity()
        )
    }
}

