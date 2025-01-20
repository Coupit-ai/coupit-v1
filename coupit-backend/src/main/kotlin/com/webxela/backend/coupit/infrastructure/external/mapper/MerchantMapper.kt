package com.webxela.backend.coupit.infrastructure.external.mapper

import com.squareup.square.models.Merchant
import com.webxela.backend.coupit.domain.model.OauthToken
import com.webxela.backend.coupit.domain.model.SquareMerchant

object MerchantMapper {

    fun Merchant.toSquareMerchant(oauthToken: OauthToken): SquareMerchant {
        return SquareMerchant(
            merchantId = this.id,
            status = this.status,
            createdAt = this.createdAt,
            businessName = this.businessName,
            country = this.country,
            currency = this.currency,
            languageCode = this.languageCode,
            mainLocationId = this.mainLocationId,
            oauthToken = oauthToken
        )
    }
}