package com.webxela.app.coupit.presentation.features.home.viewmodel

sealed interface HomeUiEvent {
    data object GetMerchantInfo : HomeUiEvent
}