package com.webxela.app.coupit.presentation.features.reward.viewmodel

sealed interface RewardUiEvent {

    data class GetSpinResult(val spinId: String) : RewardUiEvent
}