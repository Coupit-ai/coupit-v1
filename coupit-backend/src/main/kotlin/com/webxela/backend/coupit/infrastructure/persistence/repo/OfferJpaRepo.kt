package com.webxela.backend.coupit.infrastructure.persistence.repo

import com.webxela.backend.coupit.domain.model.Offer
import com.webxela.backend.coupit.infrastructure.persistence.entity.OfferEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OfferJpaRepo : JpaRepository<OfferEntity, Long> {

    fun findOfferByOfferId(offerId: UUID): OfferEntity?
}