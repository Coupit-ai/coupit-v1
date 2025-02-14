package com.webxela.backend.coupit.rest.mappper

import com.webxela.backend.coupit.rest.dto.auth.MerchantResponse
import com.webxela.backend.coupit.domain.model.SquareMerchant

object MerchantMapper {

    fun SquareMerchant.toMerchantResponse(): MerchantResponse {
        return MerchantResponse(
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