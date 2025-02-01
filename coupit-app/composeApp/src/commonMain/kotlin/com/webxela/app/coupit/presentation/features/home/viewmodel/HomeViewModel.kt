package com.webxela.app.coupit.presentation.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.domain.usecase.DataStoreUseCase
import com.webxela.app.coupit.domain.usecase.SquareUseCase
import dev.theolm.rinku.Rinku
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val squareUseCase: SquareUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when(event) {
            is HomeUiEvent.CheckIfUserIsLoggedIn -> checkIfUserIsLoggedIn()
        }
    }

    private fun checkIfUserIsLoggedIn() = viewModelScope.launch {
        _homeUiState.update { it.copy(isLoading = true) }
        dataStoreUseCase.getStringFromVault(AppConstant.SECURE_JWT_STORE)?.let { token ->
            val isExpired = squareUseCase.checkIfJwtExpired(token)
            if (isExpired) {
                Logger.e("JWT token is invalid or expired, starting login flow")
                dataStoreUseCase.deleteObject(AppConstant.SECURE_JWT_STORE)
                Rinku.handleDeepLink("${AppConstant.DEEPLINK_URL}/oauth")
                _homeUiState.update { it.copy(isLoading = false) }
            } else {
                Logger.i("User is already logged in")
                _homeUiState.update { it.copy(isLoading = false) }
            }
        } ?: run {
            Logger.e("Token not found, starting login flow")
            Rinku.handleDeepLink("${AppConstant.DEEPLINK_URL}/oauth")
            _homeUiState.update { it.copy(isLoading = false) }
        }
    }

}