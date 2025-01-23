package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.infrastructure.persistence.entity.MerchantEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toRewardEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSessionEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SessionEntityMapper.toSpinSession

object MerchantEntityMapper {

    fun MerchantEntity.toSquareMerchant(): SquareMerchant {
        return SquareMerchant(
            id = this.id,
            country = this.country,
            businessName = this.businessName,
            languageCode = this.languageCode,
            currency = this.currency,
            status = this.status,
            mainLocationId = this.mainLocationId,
            createdAt = this.createdAt,
            sessions = this.sessions.map { it.toSpinSession() },
            rewards = this.rewards.map { it.toReward() },
        )
    }

    fun SquareMerchant.toMerchantEntity(): MerchantEntity {
        return MerchantEntity(
            id = this.id,
            country = this.country,
            businessName = this.businessName,
            languageCode = this.languageCode,
            currency = this.currency,
            status = this.status,
            mainLocationId = this.mainLocationId,
            createdAt = this.createdAt,
            sessions = this.sessions.map { it.toSessionEntity() }.toMutableSet(),
            rewards = this.rewards.map { it.toRewardEntity() }.toMutableSet()
        )
    }
}