package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toOffer
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toOfferEntity

object SpinEntityMapper {

    fun SpinEntity.toSpinResult(): SpinResult {
        return SpinResult(
            id = this.id,
            spinId = this.spinId,
            merchantId = this.merchantId,
            timeStamp = this.timeStamp,
            reward = this.offer.toOffer(),
            sessionId = this.sessionId,
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed
        )
    }

    fun SpinResult.toSpinEntity(): SpinEntity {
        return SpinEntity(
            merchantId = this.merchantId,
            offer = this.reward.toOfferEntity(),
            qrCode = this.qrCode,
            sessionId = this.sessionId
        )
    }

}