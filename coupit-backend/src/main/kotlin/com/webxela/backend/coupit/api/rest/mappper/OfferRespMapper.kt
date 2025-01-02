package com.webxela.backend.coupit.api.rest.model.mapper

import com.webxela.backend.coupit.api.rest.dto.OfferResponse
import com.webxela.backend.coupit.domain.model.Offer

object OfferRespMapper {

    fun Offer.toOfferResponse(): OfferResponse {
        return OfferResponse(
            id = this.id,
            description = this.description,
            title = this.title
        )
    }

}