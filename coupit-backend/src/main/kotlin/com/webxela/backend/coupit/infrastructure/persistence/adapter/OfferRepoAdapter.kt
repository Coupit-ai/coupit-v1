package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.repo.OfferRepo
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OfferEntityMapper.toOffer
import com.webxela.backend.coupit.infrastructure.persistence.repo.OfferJpaRepo
import org.springframework.stereotype.Component

@Component
class OfferRepoAdapter(
    private val offerJpaRepo: OfferJpaRepo
): OfferRepo {

    override fun getAllOffers(merchantId: String): List<Offer> {
        return offerJpaRepo.findAll().map { it.toOffer() }
    }

}