package com.webxela.app.coupit.presentation.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.presentation.component.HomeTopBar
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiEvent
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiState
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeViewModel
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import dev.theolm.rinku.Rinku
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToScannerScreen: () -> Unit
) {

    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onNavigateToScannerScreen = onNavigateToScannerScreen
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    uiEvent: (HomeUiEvent) -> Unit,
    onNavigateToScannerScreen: () -> Unit
) {

    val errorHandler = LocalErrorHandler.current

    var sessionString by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            HomeTopBar(
                title = "Coupit",
                onNavigateToScannerScreen = onNavigateToScannerScreen
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            Text("Home Screen")

            Spacer(modifier.height(16.dp))

            OutlinedTextField(
                value = sessionString,
                onValueChange = { sessionString = it },
                singleLine = true,
                placeholder = {
                    Text("Please enter a valid session id")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier.height(16.dp))

            Button(
                onClick = {
                    Rinku.handleDeepLink("coupit://callback/oauth")
                }
            ) {
                Text("Start Test")
            }

            Button(
                onClick = {
                    errorHandler.showError("I am the error message")
                }
            ) {
                Text("Show Error")
            }
        }
    }
}