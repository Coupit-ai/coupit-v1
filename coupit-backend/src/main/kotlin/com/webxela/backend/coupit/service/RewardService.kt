package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.rest.dto.RewardRequest
import com.webxela.backend.coupit.rest.dto.RewardResponse
import com.webxela.backend.coupit.rest.mappper.RewardDtoMapper.toReward
import com.webxela.backend.coupit.rest.mappper.RewardDtoMapper.toRewardResponse
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.RewardRepoAdapter
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID


@Service
class RewardService(
    private val utilityService: UtilityService,
    private val rewardRepo: RewardRepoAdapter,
    private val merchantRepo: MerchantRepoAdapter,
) {

    companion object {
        private val logger = LogManager.getLogger(RewardService::class.java)
    }

    @Transactional(readOnly = false)
    fun createReward(rewardRequest: RewardRequest): RewardResponse {
        val user = utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        val merchant = merchantRepo.getMerchant(user.username) ?: run {
            throw ApiError.ResourceNotFound("Something went wrong, Please login again")
        }
        try {
            val reward = rewardRepo.createReward(rewardRequest.toReward(merchant))
            return reward.toRewardResponse()
        } catch (ex: Exception) {
            logger.error("Error while creating reward", ex)
            throw ApiError.InternalError("Something went wrong while creating reward", ex)
        }
    }

    @Transactional(readOnly = true)
    fun getAllRewards(): List<RewardResponse> {
        val user = utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        try {
            val rewards = rewardRepo.getAllRewards(user.username)
            return rewards.map { it.toRewardResponse() }
        } catch (ex: Exception) {
            logger.error("Error while fetching rewards", ex)
            throw ApiError.InternalError("Something went wrong while fetching rewards", ex)
        }
    }

    @Transactional(readOnly = false)
    fun deleteReward(rewardId: UUID) {
        utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        rewardRepo.getReward(rewardId)
            ?: throw ApiError.ResourceNotFound("Invalid reward Id")

        try {
            rewardRepo.deleteReward(rewardId)
        } catch (ex: Exception) {
            logger.error("Error while deleting rewards", ex)
            throw ApiError.InternalError("Something went wrong while deleting rewards", ex)
        }
    }

    @Transactional(readOnly = false)
    fun updateReward(rewardRequest: RewardRequest): RewardResponse {
        val user = utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        val merchant = merchantRepo.getMerchant(user.username) ?: run {
            throw ApiError.ResourceNotFound("Something went wrong, Please login again")
        }

        rewardRequest.id?.let {
            rewardRepo.getReward(it) ?: throw ApiError.ResourceNotFound("Reward doesn't exist")
        } ?: throw ApiError.ResourceNotFound("Reward doesn't exist")

        try {
            val reward = rewardRepo.updateReward(rewardRequest.toReward(merchant))
            return reward.toRewardResponse()
        } catch (ex: Exception) {
            logger.error("Error while updating reward", ex)
            throw ApiError.InternalError("Something went wrong while updating reward", ex)
        }
    }

}