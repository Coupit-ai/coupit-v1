package com.webxela.app.coupit.presentation.wheel.viewmodel

import com.webxela.app.coupit.domain.model.Session
import com.webxela.app.coupit.domain.model.SpinResult

data class WheelUiState(
    val sessionResponse: Session? = null,
    val spinResult: SpinResult? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)