package com.webxela.app.coupit.presentation.features.reward.viewmodel

import com.webxela.app.coupit.domain.model.Reward

sealed interface RewardUiEvent {

    data class GetSpinResult(val spinId: String) : RewardUiEvent
    data object GetAllRewards: RewardUiEvent
    data class CreateReward(val reward: Reward): RewardUiEvent
    data class DeleteReward(val rewardId: String): RewardUiEvent
    data object ClearErrorMessage: RewardUiEvent
    data class UpdateReward(val rewardId: String, val reward: Reward): RewardUiEvent
}