package com.webxela.app.coupit.presentation.features.home.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class HomeViewModel: ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
//        when (event) {
//            is HomeUiEvent.ConnectWithSquare -> connectWithSquare(event.state)
//        }
    }

}