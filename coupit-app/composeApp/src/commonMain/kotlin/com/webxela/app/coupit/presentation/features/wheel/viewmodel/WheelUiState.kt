package com.webxela.app.coupit.presentation.features.wheel.viewmodel

import com.webxela.app.coupit.domain.model.SpinConfig
import com.webxela.app.coupit.domain.model.SpinResult

data class WheelUiState(
    val spinConfigResponse: SpinConfig? = null,
    val spinResponse: SpinResult? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)