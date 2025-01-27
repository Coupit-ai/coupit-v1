package com.webxela.app.coupit.presentation.features.auth.viewmodel

import com.webxela.app.coupit.domain.model.Connection

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val connectionResponse: Connection? = null
)