package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.SquareMerchant
import com.webxela.backend.coupit.infrastructure.persistence.entity.MerchantEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OauthEntityMapper.toOauthEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OauthEntityMapper.toSquareOauth

object MerchantEntityMapper {

    fun MerchantEntity.toSquareMerchant(): SquareMerchant {
        return SquareMerchant(
            id = this.id,
            merchantId = this.merchantId,
            country = this.country,
            businessName = this.businessName,
            languageCode = this.languageCode,
            currency = this.currency,
            status = this.status,
            mainLocationId = this.mainLocationId,
            createdAt = this.createdAt,
            oauthToken = this.oauthEntity.toSquareOauth()
        )
    }

    fun SquareMerchant.toMerchantEntity(): MerchantEntity {
        return MerchantEntity(
            id = this.id,
            merchantId = this.merchantId,
            country = this.country,
            businessName = this.businessName,
            languageCode = this.languageCode,
            currency = this.currency,
            status = this.status,
            mainLocationId = this.mainLocationId,
            createdAt = this.createdAt,
            oauthEntity = this.oauthToken.toOauthEntity()
        )
    }
}