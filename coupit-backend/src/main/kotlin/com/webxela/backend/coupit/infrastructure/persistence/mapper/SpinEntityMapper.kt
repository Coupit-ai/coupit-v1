package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toRewardEntity

object SpinEntityMapper {

    fun SpinEntity.toSpinResult(): SpinResult {
        return SpinResult(
            id = this.id,
            spinId = this.spinId,
            merchantId = this.merchantId,
            createdAt = this.createdAt,
            reward = this.reward.toReward(),
            sessionId = this.sessionId,
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed
        )
    }

    fun SpinResult.toSpinEntity(): SpinEntity {
        return SpinEntity(
            merchantId = this.merchantId,
            reward = this.reward.toRewardEntity(),
            qrCode = this.qrCode,
            sessionId = this.sessionId
        )
    }

}