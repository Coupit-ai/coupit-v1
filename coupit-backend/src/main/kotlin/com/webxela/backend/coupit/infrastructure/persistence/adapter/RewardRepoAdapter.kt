package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.repo.RewardRepository
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toOffer
import com.webxela.backend.coupit.infrastructure.persistence.repo.RewardJpaRepo
import org.springframework.stereotype.Component
import java.util.*

@Component
class RewardRepoAdapter(private val rewardJpaRepo: RewardJpaRepo): RewardRepository {

    override fun getAllOffers(merchantId: String): List<Reward> {
        return rewardJpaRepo.findAll().map { it.toOffer() }
    }

    override fun getOfferById(offerId: UUID): Reward? {
        return rewardJpaRepo.findByRewardId(offerId)?.toOffer()
    }

}