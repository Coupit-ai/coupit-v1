package com.webxela.app.coupit.data.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.core.domain.map
import com.webxela.app.coupit.data.model.mapper.RewardDtoMapper.toReward
import com.webxela.app.coupit.data.model.mapper.RewardDtoMapper.toRewardRequest
import com.webxela.app.coupit.data.model.mapper.RewardDtoMapper.toRewards
import com.webxela.app.coupit.data.remote.RewardManager
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.domain.repo.RewardRepo

class RewardRepoImpl(private val rewardManager: RewardManager): RewardRepo {

    override suspend fun getAllRewards(): ApiResponse<List<Reward>, DataError.Remote> {
        return rewardManager.getAllRewards().map { it.toRewards() }
    }

    override suspend fun createReward(reward: Reward): ApiResponse<Reward, DataError.Remote> {
        return rewardManager.createReward(reward.toRewardRequest()).map { it.toReward() }
    }

    override suspend fun deleteReward(rewardId: String): ApiResponse<String, DataError.Remote> {
        val response = rewardManager.deleteReward(rewardId)
        return response.map { it.data.message }
    }

    override suspend fun updateReward(
        rewardId: String,
        reward: Reward
    ): ApiResponse<Reward, DataError.Remote> {
        return rewardManager.updateReward(rewardId, reward.toRewardRequest()).map { it.toReward() }
    }

}