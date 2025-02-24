package com.webxela.app.coupit

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.webxela.app.coupit.core.presentation.navigation.ErrorHandler
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.presentation.component.ConnectivityDialog
import com.webxela.app.coupit.presentation.navigation.NavDestinations
import com.webxela.app.coupit.presentation.navigation.RootNavHost
import com.webxela.app.coupit.presentation.theme.AppTheme
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.compose.rememberConnectivityState
import kotlinx.coroutines.launch

@Composable
internal fun App() {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var shouldRecompose by remember { mutableStateOf(false) }

    val state = rememberConnectivityState { autoStart = true }

    LaunchedEffect(state.status) {
        if (state.status is Connectivity.Status.Connected) {
            shouldRecompose = !shouldRecompose
        }
    }

    CompositionLocalProvider(LocalErrorHandler provides ErrorHandler { message ->
        coroutineScope.launch { snackBarHostState.showSnackbar(message) }
    }) {
        AppTheme {
            key(shouldRecompose) {
                ConnectivityDialog(
                    connectivityState = state,
                    retryClicked = { state.forceCheck() }
                )
                Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
                    RootNavHost(startDestination = NavDestinations.Home)
                }
            }
        }
    }
}