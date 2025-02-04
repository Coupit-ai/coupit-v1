package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.SpinConfigDto
import com.webxela.app.coupit.domain.model.SpinConfig

object SpinConfigDtoMapper {

    fun SpinConfigDto.toSpinConfig(): SpinConfig {
        return SpinConfig(
            session = this.data.session.toSpinConfigSession(),
            reward = this.data.rewards.map { it.toSpinConfigReward() }
        )
    }

    private fun SpinConfigDto.Data.Session.toSpinConfigSession(): SpinConfig.Session {
        return SpinConfig.Session(
            id = this.id,
            createdAt = this.createdAt,
            expiresAt = this.expiresAt,
            used = this.used
        )
    }

    private fun SpinConfigDto.Data.Reward.toSpinConfigReward(): SpinConfig.Reward {
        return SpinConfig.Reward(
            id = this.id,
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours
        )
    }
}