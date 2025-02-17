package com.webxela.app.coupit.presentation.features.reward.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.domain.usecase.RewardUseCase
import com.webxela.app.coupit.domain.usecase.SpinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RewardViewModel(
    private val spinUseCase: SpinUseCase,
    private val rewardUseCase: RewardUseCase
) : ViewModel() {

    private val _rewardUiState = MutableStateFlow(RewardUiState())
    val rewardUiState = _rewardUiState.asStateFlow()

    private var loadingJob: kotlinx.coroutines.Job? = null

    fun onEvent(event: RewardUiEvent) {
        when (event) {
            is RewardUiEvent.GetSpinResult -> getSpinResult(event.spinId)
            is RewardUiEvent.CreateReward -> createReward(event.reward)
            is RewardUiEvent.GetAllRewards -> getAllRewards()
            is RewardUiEvent.DeleteReward -> deleteReward(event.rewardId)
        }
    }

    private fun deleteReward(rewardId: String) = viewModelScope.launch {
        setLoading(true)
        rewardUseCase.deleteReward(rewardId)
            .onSuccess { deleteResp ->
                _rewardUiState.update {
                    it.copy(rewardDeleteResponse = deleteResp)
                }
                setLoading(false)
            }
            .onError { error ->
                _rewardUiState.update {
                    it.copy(errorMessage = error.toErrorMessage())
                }
                setLoading(false)
            }
    }

    private fun createReward(reward: Reward) = viewModelScope.launch {
        setLoading(true)
        rewardUseCase.createReward(reward)
            .onSuccess { rewardResp ->
                _rewardUiState.update {
                    it.copy(rewardResponse = rewardResp)
                }
                setLoading(false)
            }
            .onError { error ->
                _rewardUiState.update {
                    it.copy(errorMessage = error.toErrorMessage())
                }
                setLoading(false)
            }
    }

    private fun getAllRewards() = viewModelScope.launch {
        setLoading(true)
        rewardUseCase.getAllRewards()
            .onSuccess { allRewards ->
                _rewardUiState.update {
                    it.copy(allRewardResponse = allRewards)
                }
                setLoading(false)
            }
            .onError { error ->
                _rewardUiState.update {
                    it.copy(errorMessage = error.toErrorMessage())
                }
                setLoading(false)
            }
    }

    private fun getSpinResult(spinId: String) = viewModelScope.launch {
        setLoading(true)
        spinUseCase.getSpinResult(spinId)
            .onSuccess { spinResult ->
                _rewardUiState.update {
                    it.copy(spinResponse = spinResult)
                }
                setLoading(false)
            }
            .onError { error ->
                _rewardUiState.update {
                    it.copy(errorMessage = error.toErrorMessage())
                }
                setLoading(false)
            }
    }

    private fun setLoading(isLoading: Boolean) {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            kotlinx.coroutines.delay(300)
            _rewardUiState.update { it.copy(isLoading = isLoading) }
        }
    }
}