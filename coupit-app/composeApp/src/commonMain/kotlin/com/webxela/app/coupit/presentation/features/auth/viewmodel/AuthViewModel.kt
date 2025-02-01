package com.webxela.app.coupit.presentation.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.core.domain.onError
import com.webxela.app.coupit.core.domain.onSuccess
import com.webxela.app.coupit.core.presentation.toErrorMessage
import com.webxela.app.coupit.core.utils.AppConstant
import com.webxela.app.coupit.domain.usecase.DataStoreUseCase
import com.webxela.app.coupit.domain.usecase.SquareUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthViewModel(
    private val squareUseCase: SquareUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState = _authUiState.asStateFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.ConnectWithSquare -> connectWithSquare()
            is AuthUiEvent.ResetErrorMessage -> _authUiState.update { it.copy(errorMessage = null) }
            is AuthUiEvent.ResetConnectionResp -> _authUiState.update { it.copy(connectionResponse = null) }
            is AuthUiEvent.HandleOauthDeeplink -> handleOauthDeeplink(
                event.token,
                event.state,
                event.error
            )
        }
    }

    private fun handleOauthDeeplink(
        token: String?, state: String?, error: String?
    ) = viewModelScope.launch {
            if (token != null && state != null) {
                _authUiState.update { it.copy(isLoading = true) }
                val oauthState = dataStoreUseCase.getStringFromVault(
                    AppConstant.SECURE_OAUTH_STATE
                )
                Logger.i("Previous oauth state: $oauthState")
                if (oauthState == state) {
                    val response = dataStoreUseCase.saveStringInVault(
                        key = AppConstant.SECURE_JWT_STORE,
                        value = token
                    )
                    _authUiState.update {
                        it.copy(
                            isLoading = false,
                            oauthFlowResponse = response
                        )
                    }
                } else {
                    Logger.e("Invalid login attempt")
                    _authUiState.update {
                        it.copy(
                            errorMessage = "Invalid login attempt",
                            isLoading = false
                        )
                    }
                }
            } else if (error != null) {
                Logger.e("Error during oauth callback: $error")
                _authUiState.update {
                    it.copy(
                        errorMessage = error,
                        isLoading = false
                    )
                }
            }
        }

    private fun connectWithSquare() = viewModelScope.launch {
        _authUiState.update { it.copy(isLoading = true) }
        @OptIn(ExperimentalUuidApi::class)
        val oauthState = Uuid.random().toString()
        squareUseCase.connectWithSquare(oauthState)
            .onSuccess { connection ->
                dataStoreUseCase.saveStringInVault(
                    key = AppConstant.SECURE_OAUTH_STATE,
                    value = oauthState
                )
                _authUiState.update {
                    it.copy(
                        connectionResponse = connection,
//                        isLoading = false
                    )
                }
            }
            .onError { error ->
                _authUiState.update {
                    it.copy(
                        errorMessage = error.toErrorMessage(),
                        isLoading = false
                    )
                }
            }
    }

}