package com.webxela.app.coupit.presentation.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import com.webxela.app.coupit.domain.usecase.SquareUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val squareUseCase: SquareUseCase) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.GetProfileInfo -> getProfileInfo()
            is ProfileUiEvent.ResetErrorMessage -> _profileUiState.update { it.copy(errorMessage = null) }
            is ProfileUiEvent.Logout -> logout()
        }
    }

    private fun getProfileInfo() = viewModelScope.launch {
        _profileUiState.update { it.copy(isLoading = true) }
        squareUseCase.getLoggedInUser()
            .onSuccess { merchant ->
                _profileUiState.update {
                    it.copy(
                        profileResponse = merchant,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _profileUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

    private fun logout() = viewModelScope.launch {
        _profileUiState.update { it.copy(isLoading = true) }
        squareUseCase.logout()
            .onSuccess { response ->
                _profileUiState.update {
                    it.copy(
                        logoutResponse = response,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _profileUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

}