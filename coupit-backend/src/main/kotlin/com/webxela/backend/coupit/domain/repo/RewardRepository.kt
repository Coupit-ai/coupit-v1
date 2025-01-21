package com.webxela.backend.coupit.domain.repo

import com.webxela.backend.coupit.domain.model.Reward

interface RewardRepository {

    fun getAllOffers(merchantId: String): List<Reward>
    fun getOfferById(offerId: String): Reward?

}