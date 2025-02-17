package com.webxela.app.coupit.presentation.features.reward.viewmodel

import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.domain.model.SpinResult

data class RewardUiState(
    val spinResponse: SpinResult? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val allRewardResponse: List<Reward> = emptyList(),
    val rewardResponse: Reward? = null,
    val rewardDeleteResponse: String? = null
)