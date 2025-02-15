package com.webxela.app.coupit.presentation.features.home.viewmodel

import com.webxela.app.coupit.domain.model.Merchant

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val merchantResponse: Merchant? = null
)