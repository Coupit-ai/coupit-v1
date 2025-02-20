package com.webxela.app.coupit.presentation.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.domain.usecase.DataStoreUseCase
import com.webxela.app.coupit.domain.usecase.SquareUseCase
import dev.theolm.rinku.Rinku
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(private val squareUseCase: SquareUseCase) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.GetMerchantInfo -> getMerchantInfo()
        }
    }

    private fun getMerchantInfo() = viewModelScope.launch {
        _homeUiState.update { it.copy(isLoading = true) }
        squareUseCase.getJwtToken() ?: run {
            Logger.e("User is not loggedIn, starting login flow")
            Rinku.handleDeepLink("${AppConstant.DEEPLINK_URL}/oauth")
            _homeUiState.update { it.copy(isLoading = false) }
        }
        squareUseCase.getLoggedInUser()
            .onSuccess { merchant ->
                _homeUiState.update {
                    it.copy(
                        merchantResponse = merchant,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _homeUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

}