package com.webxela.app.coupit.presentation.wheel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import com.webxela.app.coupit.domain.usecase.SessionUseCase
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WheelViewModel(
    private val sessionUseCase: SessionUseCase
) : ViewModel() {

    private val _wheelUiState = MutableStateFlow(WheelUiState())
    val wheelUiState = _wheelUiState.asStateFlow()

    init { createSession("12345678", "0987654321") }

    fun onEvent(event: WheelUiEvent) {
        when (event) {
            is WheelUiEvent.CreateSession -> createSession(event.merchantId, event.transactionId)
            is WheelUiEvent.PerformSpin -> TODO()
        }
    }

    private fun createSession(
        merchantId: String,
        transactionId: String
    ) = viewModelScope.launch {

        _wheelUiState.update { it.copy(isLoading = true) }

        sessionUseCase.createSession(merchantId, transactionId)
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

}