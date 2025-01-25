package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toRewardEntity
import com.webxela.backend.coupit.infra.persistence.repo.RewardJpaRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class RewardRepoAdapter(private val rewardJpaRepo: RewardJpaRepo) {

    @Transactional(readOnly = true)
    fun getAllRewards(merchantId: String): List<Reward> {
        return rewardJpaRepo.findAll().map { it.toReward() }
    }

    @Transactional(readOnly = true)
    fun getReward(rewardId: UUID): Reward? {
        return rewardJpaRepo.findById(rewardId).map { it.toReward() }.orElse(null)
    }

    @Transactional(readOnly = true)
    fun getRewardsByMerchantId(merchantId: String): List<Reward> {
        val rewards = rewardJpaRepo.findAll()
            .filter { it.merchant.id == merchantId }
            .map { it.toReward() }
        rewardJpaRepo.flush()
        return rewards
    }

    @Transactional
    fun createNewReward(reward: Reward): Reward {
        return rewardJpaRepo.saveAndFlush(reward.toRewardEntity()).toReward()
    }

}