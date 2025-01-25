package com.webxela.backend.coupit.infra.persistence.adapter

import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.model.SpinResult
import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infra.persistence.entity.RewardEntity
import com.webxela.backend.coupit.infra.persistence.entity.SpinEntity
import com.webxela.backend.coupit.infra.persistence.mapper.RewardEntityMapper.toRewardEntity
import com.webxela.backend.coupit.infra.persistence.mapper.SessionEntityMapper.toSessionEntity
import com.webxela.backend.coupit.infra.persistence.mapper.SpinEntityMapper.toSpinEntity
import com.webxela.backend.coupit.infra.persistence.mapper.SpinEntityMapper.toSpinResult
import com.webxela.backend.coupit.infra.persistence.repo.RewardJpaRepo
import com.webxela.backend.coupit.infra.persistence.repo.SessionJpaRepo
import com.webxela.backend.coupit.infra.persistence.repo.SpinJpaRepo
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Component
class SpinRepoAdapter(
    private val spinJpaRepo: SpinJpaRepo,
    private val rewardJpaRepo: RewardJpaRepo,
    private val sessionJpaRepo: SessionJpaRepo
) {

    @Transactional(readOnly = true)
    fun getSpinById(spinId: UUID): SpinResult? {
        return spinJpaRepo.findById(spinId).map { it.toSpinResult() }.orElse(null)
    }

    @Transactional(readOnly = true)
    fun getSpinBySessionId(sessionId: UUID): SpinResult? {
        return spinJpaRepo.findAll().singleOrNull { it.session.id == sessionId }?.toSpinResult()
    }

    @Transactional(readOnly = true)
    fun saveSpinResult(
        reward: Reward,
        session: SpinSession,
        qrCode: String,
        expiresAt: Instant
    ): SpinResult {
        val managedReward = rewardJpaRepo.findById(reward.id!!).orElseThrow {
            ApiError.InternalError("Cannot find reward with id ${reward.id}")
        }
        val managedSession = sessionJpaRepo.findById(session.id!!).orElseThrow {
            ApiError.InternalError("Cannot find session with id ${session.id}")
        }
        val spinResult = SpinEntity(
            reward = managedReward,
            session = managedSession,
            qrCode = qrCode,
            expiresAt = expiresAt
        )
        return spinJpaRepo.save(spinResult).toSpinResult()
    }

    @Transactional(readOnly = false)
    fun markSpinAsClaimed(spinId: UUID) {
        val spin = spinJpaRepo.findById(spinId).orElseThrow {
            ApiError.ResourceNotFound("Spin with id $spinId not found.")
        }
        val updatedSpin = spin.copy(claimed = true)
        spinJpaRepo.save(updatedSpin)
    }

}