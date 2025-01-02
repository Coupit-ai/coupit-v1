package com.webxela.backend.coupit.api.rest.mappper

import com.webxela.backend.coupit.api.rest.dto.OfferResponse
import com.webxela.backend.coupit.domain.model.Offer

object OfferRespMapper {

    fun Offer.toOfferResponse(): OfferResponse {
        return OfferResponse(
            offerId = this.offerId,
            description = this.description,
            title = this.title
        )
    }

}