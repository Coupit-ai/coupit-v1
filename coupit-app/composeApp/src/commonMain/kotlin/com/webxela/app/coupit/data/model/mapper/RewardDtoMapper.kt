package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.AllRewardsDto
import com.webxela.app.coupit.data.model.dto.RewardDto
import com.webxela.app.coupit.data.model.dto.RewardRequest
import com.webxela.app.coupit.domain.model.Reward

object RewardDtoMapper {
    fun AllRewardsDto.toRewards(): List<Reward> {
        return this.data.map { reward ->
            Reward(
                id = reward.id,
                description = reward.description,
                probability = reward.probability,
                title = reward.title,
                validityHours = reward.validityHours,
                discountCode = reward.discountCode,
                createdAt = reward.createdAt,
            )
        }
    }

    fun RewardDto.toReward(): Reward {
        return Reward(
            id = this.data.id,
            description = this.data.description,
            probability = this.data.probability,
            title = this.data.title,
            validityHours = this.data.validityHours,
            discountCode = this.data.discountCode,
            createdAt = this.data.createdAt
        )
    }

    fun Reward.toRewardRequest(): RewardRequest {
        return RewardRequest(
            description = this.description,
            probability = this.probability,
            title = this.title,
            validityHours = this.validityHours,
            discountCode = this.discountCode
        )
    }

}