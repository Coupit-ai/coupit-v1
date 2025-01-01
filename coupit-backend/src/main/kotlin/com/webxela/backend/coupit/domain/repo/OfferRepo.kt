package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.Offer

interface OfferRepo {

    fun getAllOffers(merchantId: String): List<Offer>

}