package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infrastructure.persistence.entity.RewardEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.MerchantEntityMapper.toSquareMerchant

object RewardEntityMapper {

    fun RewardEntity.toReward(): Reward {
        return Reward(
            id = this.id,
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours,
            merchant = this.merchant.toSquareMerchant()
        )
    }

    fun Reward.toRewardEntity(): RewardEntity {
        return RewardEntity(
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours,
            merchant = this.merchant.toMerchantEntity()
        )
    }

}