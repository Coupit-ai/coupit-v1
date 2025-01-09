package com.webxela.app.coupit.presentation.reward.viewmodel

sealed interface RewardUiEvent {

    data class GetSpinResult(val spinId: String) : RewardUiEvent
}