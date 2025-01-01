package com.webxela.backend.coupit.infrastructure.persistence.mapper

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.infrastructure.persistence.entity.OfferEntity

object OfferEntityMapper {

    fun OfferEntity.toOffer(): Offer {
        return Offer(
            id = this.id,
            title = this.title,
            description = this.description
        )
    }

}