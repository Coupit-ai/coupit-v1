package com.webxela.app.coupit.presentation.wheel.viewmodel

sealed interface WheelUiEvent {

    data class CreateSession(
        val merchantId: String,
        val transactionId: String
    ) : WheelUiEvent

    data class PerformSpin(
        val merchantId: String,
        val sessionId: String
    ) : WheelUiEvent
}