package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OfferEntityMapper.toOffer
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OfferEntityMapper.toOfferEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSession
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSessionEntity

object SpinEntityMapper {

    fun SpinEntity.toSpinResult(): SpinResult {
        return SpinResult(
            id = this.id,
            spinId = this.spinId,
            merchantId = this.merchantId,
            timeStamp = this.timeStamp,
            offer = this.offer.toOffer(),
            sessionId = this.sessionId,
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed
        )
    }

    fun SpinResult.toSpinEntity(): SpinEntity {
        return SpinEntity(
            merchantId = this.merchantId,
            offer = this.offer.toOfferEntity(),
            qrCode = this.qrCode,
            sessionId = this.sessionId
        )
    }

}