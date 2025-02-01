package com.webxela.app.coupit

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.webxela.app.coupit.core.presentation.navigation.ErrorHandler
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.presentation.navigation.NavDestinations
import com.webxela.app.coupit.presentation.navigation.RootNavHost
import com.webxela.app.coupit.presentation.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
internal fun App() {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(LocalErrorHandler provides ErrorHandler { message ->
        coroutineScope.launch { snackBarHostState.showSnackbar(message) }
    }) {
        AppTheme {
            Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) {
                RootNavHost(startDestination = NavDestinations.Home)
            }
        }
    }
}