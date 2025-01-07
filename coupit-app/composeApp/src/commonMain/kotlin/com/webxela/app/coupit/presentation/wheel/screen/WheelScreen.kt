package com.webxela.app.coupit.presentation.wheel.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.presentation.wheel.viewmodel.WheelUiEvent
import com.webxela.app.coupit.presentation.wheel.viewmodel.WheelUiState
import com.webxela.app.coupit.presentation.wheel.viewmodel.WheelViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun WheelScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: WheelViewModel = koinViewModel()
) {

    val uiState by viewModel.wheelUiState.collectAsStateWithLifecycle()
    WheelScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent
    )

}


@Composable
private fun WheelScreen(
    modifier: Modifier = Modifier,
    uiState: WheelUiState,
    uiEvent: (WheelUiEvent) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().background(
            MaterialTheme.colorScheme.background
        )
    ) {

        if (uiState.isLoading) CircularProgressIndicator()
        if (uiState.errorMessage != null) Text(uiState.errorMessage)
        if (uiState.sessionResponse != null) {
            val response = uiState.sessionResponse
            Text(
                text = response.toString(),
                modifier = modifier
            )
        }
    }
}