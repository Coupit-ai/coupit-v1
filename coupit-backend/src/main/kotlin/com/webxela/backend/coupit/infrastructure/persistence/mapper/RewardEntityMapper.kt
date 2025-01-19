package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infrastructure.persistence.entity.RewardEntity

object RewardEntityMapper {

    fun RewardEntity.toOffer(): Reward {
        return Reward(
            id = this.id,
            rewardId = this.rewardId,
            timeStamp = this.timeStamp,
            title = this.title,
            description = this.description
        )
    }

    fun Reward.toOfferEntity(): RewardEntity {
        return RewardEntity(
            title = this.title,
            description = this.description
        )
    }

}