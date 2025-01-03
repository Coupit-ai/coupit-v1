package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.Offer
import java.util.*

interface OfferRepo {

    fun getAllOffers(merchantId: String): List<Offer>
    fun getOfferById(offerId: UUID): Offer?

}