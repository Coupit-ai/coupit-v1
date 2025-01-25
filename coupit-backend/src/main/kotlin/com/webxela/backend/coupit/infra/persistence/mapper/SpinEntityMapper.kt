package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.infra.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toRewardEntity
import com.webxela.backend.coupit.infra.persistence.mapper.SessionEntityMapper.toSessionEntity
import com.webxela.backend.coupit.infra.persistence.mapper.SessionEntityMapper.toSpinSession

object SpinEntityMapper {

    fun SpinEntity.toSpinResult(): SpinResult {
        return SpinResult(
            id = this.id,
            createdAt = this.createdAt,
            reward = this.reward.toReward(),
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed,
            session = this.session.toSpinSession()
        )
    }

    fun SpinResult.toSpinEntity(): SpinEntity {
        return SpinEntity(
            id = this.id,
            reward = this.reward.toRewardEntity(),
            qrCode = this.qrCode,
            session = this.session.toSessionEntity(),
            expiresAt = this.expiresAt
        )
    }

}