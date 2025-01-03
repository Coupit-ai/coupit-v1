package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.repo.OfferRepo
import org.springframework.stereotype.Component
import java.util.UUID


@Component
class OfferUseCase(
    private val offerRepo: OfferRepo
) {

    fun getAllOffers(merchantId: String): List<Offer> {
        return offerRepo.getAllOffers(merchantId)
    }

    fun getOfferById(offerId: UUID): Offer? {
        return offerRepo.getOfferById(offerId)
    }

}