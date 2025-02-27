package com.webxela.app.coupit.presentation.features.home.screen

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.core.utils.getWindowSizeClass
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiEvent
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiState
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToScanner: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onNavigateToScanner = onNavigateToScanner,
        onNavigateToProfile = onNavigateToProfile
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    uiEvent: (HomeUiEvent) -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToProfile: () -> Unit
) {

    val errorHandler = LocalErrorHandler.current
    val windowSizeClass = getWindowSizeClass()

    LaunchedEffect(Unit) {
        uiEvent(HomeUiEvent.GetMerchantInfo)
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            errorHandler.showError(it)
        }
    }

    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> BottomNavHomeScreen(
            modifier = modifier,
            uiState = uiState,
            uiEvent = uiEvent,
            onNavigateToScanner = onNavigateToScanner
        )

        else -> DrawerHomeScreen(
            modifier = modifier,
            uiState = uiState,
            uiEvent = uiEvent,
            onNavigateToScanner = onNavigateToScanner,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}