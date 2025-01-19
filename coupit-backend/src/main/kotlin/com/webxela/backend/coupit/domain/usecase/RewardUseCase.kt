package com.webxela.backend.coupit.domain.usecase

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.repo.RewardRepository
import org.springframework.stereotype.Component
import java.util.UUID


@Component
class RewardUseCase(private val rewardRepository: RewardRepository) {

    fun getAllOffers(merchantId: String): List<Reward> {
        return rewardRepository.getAllOffers(merchantId)
    }

    fun getOfferById(offerId: UUID): Reward? {
        return rewardRepository.getOfferById(offerId)
    }

}