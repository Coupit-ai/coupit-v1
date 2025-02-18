package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.SpinResultDto
import com.webxela.app.coupit.domain.model.SpinResult

object SpinResultDtoMapper {

    fun SpinResultDto.toSpinResult(): SpinResult {
        return SpinResult(
            id = this.data.id,
            createdAt = this.data.createdAt,
            expiresAt = this.data.expiresAt,
            claimed = this.data.claimed,
            reward = this.data.reward.toSpinReward()
        )
    }

    private fun SpinResultDto.Data.Reward.toSpinReward(): SpinResult.Reward {
        return SpinResult.Reward(
            id = this.id,
            description = this.description,
            probability = this.probability,
            title = this.title,
            validityHours = this.validityHours
        )
    }
}