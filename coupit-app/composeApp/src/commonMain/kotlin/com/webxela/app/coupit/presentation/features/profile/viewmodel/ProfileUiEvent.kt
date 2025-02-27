package com.webxela.app.coupit.presentation.features.profile.viewmodel

sealed interface ProfileUiEvent {
    data object GetProfileInfo : ProfileUiEvent
    data object ResetErrorMessage : ProfileUiEvent
    data object Logout : ProfileUiEvent
}