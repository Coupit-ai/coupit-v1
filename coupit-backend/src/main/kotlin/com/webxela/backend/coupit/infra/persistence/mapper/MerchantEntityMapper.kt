package com.webxela.backend.coupit.infra.persistence.mapper

import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.infra.persistence.entity.MerchantEntity
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infra.persistence.mapper.SessionEntityMapper.toSpinSession

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
            createdAt = this.createdAt
        )
    }
}