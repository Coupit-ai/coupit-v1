package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.Reward
import java.util.*

interface RewardRepository {

    fun getAllOffers(merchantId: String): List<Reward>
    fun getOfferById(offerId: UUID): Reward?

}