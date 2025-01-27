package com.webxela.app.coupit.presentation.features.wheel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import com.webxela.app.coupit.domain.usecase.SessionUseCase
import com.webxela.app.coupit.domain.usecase.SpinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WheelViewModel(
    private val sessionUseCase: SessionUseCase,
    private val spinUseCase: SpinUseCase
) : ViewModel() {

    private val _wheelUiState = MutableStateFlow(WheelUiState())
    val wheelUiState = _wheelUiState.asStateFlow()

    fun onEvent(event: WheelUiEvent) {
        when (event) {
            is WheelUiEvent.GetWheelConfig -> getWheelConfig(event.sessionId)
            is WheelUiEvent.PerformSpin -> performSpin(event.merchantId, event.sessionId)
        }
    }

    private fun getWheelConfig(sessionId: String) = viewModelScope.launch {

        _wheelUiState.update { it.copy(isLoading = true) }
        sessionUseCase.createSession("merchantId", "transactionId")
            .onSuccess { session ->
                _wheelUiState.update {
                    it.copy(
                        sessionResponse = session,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _wheelUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }

    }

    private fun performSpin(
        merchantId: String,
        sessionId: String
    ) = viewModelScope.launch {

        _wheelUiState.update { it.copy(isLoading = true) }
        spinUseCase.performSpin(merchantId, sessionId)
            .onSuccess { spinResult ->
                _wheelUiState.update {
                    it.copy(
                        spinResponse = spinResult,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _wheelUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

}