package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.repo.RewardRepository
import com.webxela.backend.coupit.infrastructure.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infrastructure.persistence.repo.RewardJpaRepo
import org.springframework.stereotype.Component

@Component
class RewardRepoAdapter(private val rewardJpaRepo: RewardJpaRepo): RewardRepository {

    override fun getAllOffers(merchantId: String): List<Reward> {
        return rewardJpaRepo.findAll().map { it.toReward() }
    }

    override fun getOfferById(offerId: String): Reward? {
        return rewardJpaRepo.findByRewardId(offerId)?.toReward()
    }

}