package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.config.DotEnvConfig
import com.webxela.backend.coupit.rest.dto.SpinConfigResponse
import com.webxela.backend.coupit.rest.dto.SpinResponse
import com.webxela.backend.coupit.rest.mappper.SpinDtoMapper.toSpinConfig
import com.webxela.backend.coupit.rest.mappper.SpinDtoMapper.toSpinResponse
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.domain.exception.ApiResponse
import com.webxela.backend.coupit.domain.model.Reward
import com.webxela.backend.coupit.domain.model.SpinSession
import com.webxela.backend.coupit.infra.persistence.adapter.RewardRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.SessionRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.SpinRepoAdapter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID
import kotlin.random.Random

@Service
class SpinService(
    private val spinRepo: SpinRepoAdapter,
    private val sessionRepo: SessionRepoAdapter,
    private val utilityService: UtilityService,
    private val rewardRepo: RewardRepoAdapter,
    private val dotEnvConfig: DotEnvConfig
) {

    companion object {
        private val logger: Logger = LogManager.getLogger(SpinService::class.java)
    }

    @Transactional
    fun performSpin(sessionId: UUID): SpinResponse {

        val session = getSessionIfValid(sessionId)
        // Early return spin result if exists
        spinRepo.getSpinBySessionId(sessionId)?.let { spinResult ->
            logger.info("Early returning with Spin: ${spinResult.id}")
            return spinResult.toSpinResponse()
        }

        val rewards = rewardRepo.getRewardsByMerchantId(session.merchant.id)

        // Check if there is at least one reward available
        if (rewards.isEmpty()) {
            throw ApiError.ResourceNotFound(message = "No rewards available.")
        }

        val randomReward = getRandomReward(rewards)
            ?: throw ApiError.BadRequest(message = "Error while trying to get rewards for you.")

        try {

            val spinResult = spinRepo.saveSpinResult(
                reward = randomReward,
                expiresAt = Instant.now().plus(
                    randomReward.validityHours.toLong(),
                    ChronoUnit.HOURS
                ),
                session = session
            )


            sessionRepo.markSessionAsUsed(sessionId)
            return spinResult.toSpinResponse()

        } catch (ex: Exception) {
            logger.error("Error while saving spin result for session $sessionId", ex)
            throw ApiError.InternalError("Failed to perform spin action: ${ex.message}", ex)
        }
    }

    private fun getRandomReward(rewards: List<Reward>): Reward? {
        // Filter out rewards with invalid probabilities
        val validRewards = rewards.filter { it.probability > 0 }
        if (validRewards.isEmpty()) {
            logger.error("No valid rewards available.")
            return null
        }
        val totalProbability = validRewards.sumOf { it.probability }

        // Generate a random number within the range of total probability
        val randomValue = Random.nextDouble(totalProbability)
        var cumulativeProbability = 0.0

        // Select a reward based on the random value
        for (reward in validRewards) {
            cumulativeProbability += reward.probability
            if (randomValue <= cumulativeProbability) {
                return reward
            }
        }

        // This should never happen if probabilities are valid
        logger.error("No reward selected. Check input probabilities.")
        return null
    }

    @Transactional(readOnly = false)
    fun getSpinConfig(sessionId: UUID): SpinConfigResponse {

        val sessionData = getSessionIfValid(sessionId)
        val rewards = rewardRepo.getRewardsByMerchantId(sessionData.merchant.id)

        if (rewards.size < 2) {
            logger.error("There are less than 2 rewards configured.")
            throw ApiError.ResourceNotFound("There should be at least 2 rewards configured, configure rewards please")
        }
        return rewards.toSpinConfig(sessionData)

    }

    @Transactional(readOnly = false)
    fun getOrRedeemReward(spinId: UUID, redeem: Boolean): SpinResponse {

        val user = utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        val spinResult = spinRepo.getSpinById(spinId) ?: run {
            logger.error("Spin with id $spinId does not exist.")
            throw ApiError.ResourceNotFound("Reward is invalid or doesnt exist.")
        }

        if (user.username != spinResult.session.merchant.id)
            throw ApiError.Unauthorized("You are not authorized to perform this action.")

        if (redeem) {
            if (spinResult.expiresAt.isBefore(Instant.now()) || spinResult.claimed!!) {
                throw ApiError.ExpiredException("Reward has been already claimed or expired.")
            }
        }

        try {

            if (redeem) spinRepo.markSpinAsClaimed(spinId)
            return spinResult.toSpinResponse()

        } catch (ex: Exception) {
            logger.error("Error while redeeming reward", ex)
            throw ApiError.InternalError("Something went wrong while redeeming reward.", ex)
        }
    }

    private fun getSessionIfValid(sessionId: UUID): SpinSession {
        // Session should exist
        val sessionData = sessionRepo.getSession(sessionId)
            ?: throw ApiError.ResourceNotFound(message = "Session with session_id $sessionId does not exist.")

        // Check if session is expired
        if (sessionData.expiresAt!!.isBefore(Instant.now()) || sessionData.used!!) {
            throw ApiError.ExpiredException("Session has been already used or expired")
        }

        // Check if the request is authorized
        if (utilityService.getCurrentLoginUser()?.username != sessionData.merchant.id) {
            throw ApiError.BadRequest("You are not authorized to access this session.")
        }
        return sessionData
    }

}