package com.webxela.app.coupit.domain.repo

import com.webxela.app.coupit.core.domain.ApiResponse
import com.webxela.app.coupit.core.domain.DataError
import com.webxela.app.coupit.data.model.dto.AllRewardsDto
import com.webxela.app.coupit.domain.model.Reward

interface RewardRepo {

    suspend fun getAllRewards(): ApiResponse<List<Reward>, DataError.Remote>

    suspend fun createReward(reward: Reward): ApiResponse<Reward, DataError.Remote>
}