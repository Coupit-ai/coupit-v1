package com.webxela.app.coupit.domain.usecase

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.domain.repo.RewardRepo

class RewardUseCase(private val rewardRepo: RewardRepo) {

    suspend fun getAllRewards(): ApiResponse<List<Reward>, DataError.Remote> {
        return rewardRepo.getAllRewards()
    }

    suspend fun createReward(reward: Reward): ApiResponse<Reward, DataError.Remote> {
        return rewardRepo.createReward(reward)
    }

    suspend fun deleteReward(rewardId: String): ApiResponse<String, DataError.Remote> {
        return rewardRepo.deleteReward(rewardId)
    }

    suspend fun updateReward(rewardId: String, reward: Reward): ApiResponse<Reward, DataError.Remote> {
        return rewardRepo.updateReward(rewardId, reward)
    }
}