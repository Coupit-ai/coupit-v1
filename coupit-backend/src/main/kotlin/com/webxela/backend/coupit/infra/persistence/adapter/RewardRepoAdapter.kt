package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.enum.RewardState
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
        val reward =  rewardJpaRepo
            .findAll()
            .filter { it.merchant.id == merchantId && it.state == RewardState.ACTIVE }
            .map { it.toReward() }
        rewardJpaRepo.flush()
        return reward
    }

    @Transactional(readOnly = true)
    fun getReward(rewardId: UUID): Reward? {
        return rewardJpaRepo.findById(rewardId).map { it.toReward() }.orElse(null)
    }

    @Transactional(readOnly = false)
    fun createReward(reward: Reward): Reward {
        return rewardJpaRepo.saveAndFlush(reward.toRewardEntity()).toReward()
    }

    @Transactional(readOnly = false)
    fun deleteReward(rewardId: UUID) {
        val reward = rewardJpaRepo.findById(rewardId).orElse(null)
        val newReward = reward.copy(state = RewardState.DELETED)
        rewardJpaRepo.saveAndFlush(newReward)
    }

    @Transactional(readOnly = false)
    fun updateReward(reward: Reward): Reward {
        return rewardJpaRepo.saveAndFlush(reward.toRewardEntity()).toReward()
    }
}