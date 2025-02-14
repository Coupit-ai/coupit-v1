package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.rest.dto.RewardRequest
import com.webxela.backend.coupit.rest.dto.RewardResponse
import com.webxela.backend.coupit.rest.mappper.RewardDtoMapper.toReward
import com.webxela.backend.coupit.rest.mappper.RewardDtoMapper.toRewardResponse
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.RewardRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.SpinRepoAdapter
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class RewardService(
    private val utilityService: UtilityService,
    private val rewardRepo: RewardRepoAdapter,
    private val merchantRepo: MerchantRepoAdapter,
    private val spinRepo: SpinRepoAdapter
) {

    companion object {
        private val logger = LogManager.getLogger(RewardService::class.java)
    }

    @Transactional(readOnly = false)
    fun createNewRewards(rewardRequest: List<RewardRequest>): List<RewardResponse> {
        val user = utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        val merchant = merchantRepo.getMerchant(user.username) ?: run {
            throw ApiError.ResourceNotFound("Something went wrong, Please login again")
        }
        try {
            val reward = rewardRepo.createRewardInBatch(rewardRequest.map { it.toReward(merchant) })
            return reward.map { it.toRewardResponse() }
        } catch (ex: Exception) {
            logger.error("Error while creating reward", ex)
            throw ApiError.InternalError("Something went wrong while creating reward", ex)
        }
    }

}