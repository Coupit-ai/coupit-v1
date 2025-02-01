package com.webxela.app.coupit.presentation.features.auth.viewmodel

sealed interface AuthUiEvent {

    data object ResetErrorMessage: AuthUiEvent

    data object ResetConnectionResp: AuthUiEvent

    data object ConnectWithSquare: AuthUiEvent

    data class HandleAuthCallback(
        val token: String?,
        val state: String?,
        val error: String?
    ): AuthUiEvent
}