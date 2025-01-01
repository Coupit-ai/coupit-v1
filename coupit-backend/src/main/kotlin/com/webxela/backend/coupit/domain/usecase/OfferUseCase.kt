package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.repo.OfferRepo
import org.springframework.stereotype.Component


@Component
class OfferUseCase(
    private val offerRepo: OfferRepo
) {

    fun getAllOffers(merchantId: String): List<Offer> {
        return offerRepo.getAllOffers(merchantId)
    }

}