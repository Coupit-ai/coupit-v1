package com.webxela.app.coupit.presentation.features.profile.viewmodel

import com.webxela.app.coupit.domain.model.Merchant

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profileResponse: Merchant? = null,
    val logoutResponse: String? = null
)