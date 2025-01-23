package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toRewardEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSessionEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSpinSession

object SpinEntityMapper {

    fun SpinEntity.toSpinResult(): SpinResult {
        return SpinResult(
            id = this.id,
            createdAt = this.createdAt,
            reward = this.reward.toReward(),
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            rewardState = this.rewardState,
            session = this.session.toSpinSession()
        )
    }

    fun SpinResult.toSpinEntity(): SpinEntity {
        return SpinEntity(
            reward = this.reward.toRewardEntity(),
            qrCode = this.qrCode,
            session = this.session.toSessionEntity()
        )
    }

}