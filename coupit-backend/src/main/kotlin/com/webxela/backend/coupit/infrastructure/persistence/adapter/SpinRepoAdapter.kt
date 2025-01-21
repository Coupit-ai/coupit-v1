package com.webxela.backend.coupit.infrastructure.persistence.adapter

import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.repo.SpinRepository
import com.webxela.backend.coupit.infrastructure.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infrastructure.persistence.mapper.SpinEntityMapper.toSpinResult
import com.webxela.backend.coupit.infrastructure.persistence.repo.RewardJpaRepo
import com.webxela.backend.coupit.infrastructure.persistence.repo.SpinJpaRepo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class SpinRepoAdapter(
    private val spinJpaRepo: SpinJpaRepo,
    private val rewardJpaRepo: RewardJpaRepo
) : SpinRepository {

    override fun getSpinResultBySpinId(spinId: String): SpinResult? {
        return spinJpaRepo.findSpinEntityBySpinId(spinId)?.toSpinResult()
    }

    override fun getSpinResultBySessionId(sessionId: String): SpinResult? {
        return spinJpaRepo.findSpinEntityBySessionId(sessionId)?.toSpinResult()
    }

    @Transactional
    override fun saveSpinResult(merchantId: String, rewardId: String, qrCode: String, sessionId: String): SpinResult {
        val offer = rewardJpaRepo.findByRewardId(rewardId)
            ?: throw IllegalStateException("Offer not found in database")

        val spinEntity = SpinEntity(
            merchantId = merchantId,
            reward = offer,
            qrCode = qrCode,
            sessionId = sessionId
        )

        return spinJpaRepo.save(spinEntity).toSpinResult()
    }

    @Transactional
    override fun markSpinAsClaimed(spinId: String): Boolean {
        return when (spinJpaRepo.markSpinAsClaimed(spinId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows marked as claimed")
        }
    }

    @Transactional
    override fun deleteSpinResult(sessionId: String): Boolean {
        return when(spinJpaRepo.deleteBySessionId(sessionId)) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Unexpected state: Multiple rows are deleted")
        }
    }
}