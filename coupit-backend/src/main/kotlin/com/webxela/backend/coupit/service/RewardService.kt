package com.webxela.backend.coupit.service

import com.webxela.backend.coupit.api.dto.RewardRequest
import com.webxela.backend.coupit.api.dto.RewardResponse
import com.webxela.backend.coupit.api.mappper.RewardDtoMapper.toReward
import com.webxela.backend.coupit.api.mappper.RewardDtoMapper.toRewardResponse
import com.webxela.backend.coupit.domain.exception.ApiError
import com.webxela.backend.coupit.infra.persistence.adapter.MerchantRepoAdapter
import com.webxela.backend.coupit.infra.persistence.adapter.RewardRepoAdapter
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class RewardService(
    private val utilityService: UtilityService,
    private val rewardRepo: RewardRepoAdapter,
    private val merchantRepo: MerchantRepoAdapter
) {

    companion object {
        private val logger = LogManager.getLogger(RewardService::class.java)
    }

    @Transactional
    fun createNewReward(rewardRequest: RewardRequest): RewardResponse {
        val user = utilityService.getCurrentLoginUser()
            ?: throw ApiError.Unauthorized("You are not authorized to perform this action.")

        val merchant = merchantRepo.getMerchant(user.username) ?: run {
            throw ApiError.ResourceNotFound("Something went wrong, Please login again")
        }
        try {
            merchant.rewards?.add(rewardRequest.toReward(merchant))
            val reward = rewardRepo.createNewReward(rewardRequest.toReward(merchant))
            println(merchant.rewards)
            return reward.toRewardResponse()
        } catch (ex: Exception) {
            logger.error("Error while creating reward", ex)
            throw ApiError.InternalError("Something went wrong while creating reward", ex)
        }
    }


}