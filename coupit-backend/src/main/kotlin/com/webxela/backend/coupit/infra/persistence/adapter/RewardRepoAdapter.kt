package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.enum.RewardState
import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toReward
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toRewardEntity
import com.webxela.backend.coupit.infra.persistence.repo.RewardJpaRepo
import com.webxela.backend.coupit.infra.persistence.repo.SpinJpaRepo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bouncycastle.asn1.x500.style.RFC4519Style.description
import org.bouncycastle.asn1.x500.style.RFC4519Style.title
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class RewardRepoAdapter(
    private val rewardJpaRepo: RewardJpaRepo,
    private val spinJpaRepo: SpinJpaRepo
) {

    companion object {
        val logger: Logger = LogManager.getLogger(RewardRepoAdapter::class.java)
    }

    @Transactional(readOnly = true)
    fun getAllRewards(merchantId: String): List<Reward> {
        val reward = rewardJpaRepo
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
        val spin = spinJpaRepo.findAllByRewardId(rewardId)
        if (spin.isEmpty()) {
            rewardJpaRepo.deleteById(rewardId)
        }
        rewardJpaRepo.saveAndFlush(newReward)
    }

    @Transactional(readOnly = false)
    fun updateReward(rewardId: UUID, reward: Reward): Reward {
        val prevReward = rewardJpaRepo.findById(rewardId).orElse(null)
        prevReward ?: throw IllegalArgumentException("Something went wrong")
        val spin = spinJpaRepo.findAllByRewardId(rewardId)
        val newReward = if (spin.isEmpty()) {
            prevReward.copy(
                title = reward.title,
                description = reward.description,
                probability = reward.probability,
                validityHours = reward.validityHours,
                discountCode = reward.discountCode
            )
        } else {
            rewardJpaRepo.save(reward.toRewardEntity())
            prevReward.copy(state = RewardState.UPDATED)
        }
        return rewardJpaRepo.saveAndFlush(newReward).toReward()
    }
}