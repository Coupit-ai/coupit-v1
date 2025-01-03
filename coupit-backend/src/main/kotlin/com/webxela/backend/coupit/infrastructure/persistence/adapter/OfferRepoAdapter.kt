package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.domain.repo.OfferRepo
import com.webxela.backend.coupit.infrastructure.persistence.mapper.OfferEntityMapper.toOffer
import com.webxela.backend.coupit.infrastructure.persistence.repo.OfferJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*

@Component
@Transactional
class OfferRepoAdapter(
    private val offerJpaRepo: OfferJpaRepo
): OfferRepo {

    override fun getAllOffers(merchantId: String): List<Offer> {
        return offerJpaRepo.findAll().map { it.toOffer() }
    }

    override fun getOfferById(offerId: UUID): Offer? {
        return offerJpaRepo.findOfferByOfferId(offerId)?.toOffer()
    }

}