package com.webxela.app.coupit.presentation.features.wheel.viewmodel

sealed interface WheelUiEvent {

    data class GetWheelConfig(val sessionId: String?) : WheelUiEvent

    data class PerformSpin(
        val merchantId: String,
        val sessionId: String
    ) : WheelUiEvent
}