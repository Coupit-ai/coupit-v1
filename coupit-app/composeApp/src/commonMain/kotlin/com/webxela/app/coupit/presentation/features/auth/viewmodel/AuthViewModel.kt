package com.webxela.app.coupit.presentation.features.auth.viewmodel

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

class AuthViewModel(private val squareUseCase: SquareUseCase): ViewModel() {

    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState = _authUiState.asStateFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.ConnectWithSquare -> connectWithSquare(event.state)
        }
    }

    private fun connectWithSquare(state: String) = viewModelScope.launch {

        _authUiState.update { it.copy(isLoading = true) }
        squareUseCase.connectWithSquare(state)
            .onSuccess { connection ->
                _authUiState.update {
                    it.copy(
                        connectionResponse = connection,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _authUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

}