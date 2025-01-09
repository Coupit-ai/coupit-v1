package com.webxela.app.coupit.presentation.reward.viewmodel

import com.webxela.app.coupit.domain.model.Session
import com.webxela.app.coupit.domain.model.SpinResult

data class RewardUiState(
    val spinResponse: SpinResult? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)