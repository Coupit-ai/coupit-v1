package com.webxela.app.coupit.presentation.features.auth.viewmodel

sealed interface AuthUiEvent {
    data class ConnectWithSquare(val state: String): AuthUiEvent
}