package com.webxela.backend.coupit.api.mappper

import com.webxela.backend.coupit.api.dto.SpinConfigResponse
import com.webxela.backend.coupit.api.dto.SpinResponse
import com.webxela.backend.coupit.api.mappper.RewardDtoMapper.toRewardResponse
import com.webxela.backend.coupit.api.mappper.RewardDtoMapper.toSpinConfigSession
import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.model.SpinSession

object SpinDtoMapper {

    fun SpinResult.toSpinResponse(): SpinResponse {
        return SpinResponse(
            id = this.id!!,
            createdAt = this.createdAt!!,
            reward = this.reward.toRewardResponse(),
            qrCode = this.qrCode,
            expiresAt = this.expiresAt,
            claimed = this.claimed!!
        )
    }

    fun List<Reward>.toSpinConfig(session: SpinSession): SpinConfigResponse {
        return SpinConfigResponse(
            rewards = this.map { it.toRewardResponse() },
            session = session.toSpinConfigSession()
        )
    }

}