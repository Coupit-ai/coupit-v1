package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infra.persistence.entity.RewardEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toMerchantEntity
import com.webxela.backend.coupit.infra.persistence.mapper.MerchantEntityMapper.toSquareMerchant

object RewardEntityMapper {

    fun RewardEntity.toReward(): Reward {
        return Reward(
            id = this.id,
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours,
            discountCode = this.discountCode,
            merchant = this.merchant.toSquareMerchant()
        )
    }

    fun Reward.toRewardEntity(): RewardEntity {
        return RewardEntity(
            id = this.id,
            title = this.title,
            description = this.description,
            probability = this.probability,
            validityHours = this.validityHours,
            discountCode = this.discountCode,
            merchant = this.merchant.toMerchantEntity()
        )
    }

}