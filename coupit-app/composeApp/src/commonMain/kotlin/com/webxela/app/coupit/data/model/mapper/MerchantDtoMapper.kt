package com.webxela.app.coupit.data.model.mapper

import com.webxela.app.coupit.data.model.dto.MerchantDto
import com.webxela.app.coupit.domain.model.Merchant

object MerchantDtoMapper {
    fun MerchantDto.toMerchant(): Merchant {
        return Merchant(
            id = this.data.id,
            businessName = this.data.businessName,
            country = this.data.country,
            createdAt = this.data.createdAt,
            currency = this.data.currency,
            languageCode = this.data.languageCode,
            mainLocationId = this.data.mainLocationId,
            status = this.data.status
        )
    }
}