package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infrastructure.persistence.entity.RewardEntity

object RewardEntityMapper {

    fun RewardEntity.toReward(): Reward {
        return Reward(
            id = this.id,
            rewardId = this.rewardId,
            timeStamp = this.createdAt,
            title = this.title,
            description = this.description
        )
    }

    fun Reward.toRewardEntity(): RewardEntity {
        return RewardEntity(
            title = this.title,
            description = this.description
        )
    }

}