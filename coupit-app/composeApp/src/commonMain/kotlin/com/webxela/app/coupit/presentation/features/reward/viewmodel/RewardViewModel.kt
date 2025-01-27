package com.webxela.app.coupit.presentation.features.reward.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import com.webxela.app.coupit.domain.usecase.SpinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RewardViewModel(private val spinUseCase: SpinUseCase) : ViewModel() {

    private val _rewardUiState = MutableStateFlow(RewardUiState())
    val rewardUiState = _rewardUiState.asStateFlow()

    fun onEvent(event: RewardUiEvent) {
        when (event) {
            is RewardUiEvent.GetSpinResult -> getSpinResult(event.spinId)
        }
    }

    private fun getSpinResult(spinId: String) = viewModelScope.launch {

        _rewardUiState.update { it.copy(isLoading = true) }
        spinUseCase.getSpinResult(spinId)
            .onSuccess { spinResult ->
                _rewardUiState.update {
                    it.copy(
                        spinResponse = spinResult,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _rewardUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

}