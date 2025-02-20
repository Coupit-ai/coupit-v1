package com.webxela.app.coupit.presentation.features.wheel.viewmodel

sealed interface WheelUiEvent {

    data class GetWheelConfig(val sessionId: String?) : WheelUiEvent

    data class PerformSpin(val sessionId: String) : WheelUiEvent

    data object ClearErrorMessage : WheelUiEvent
}