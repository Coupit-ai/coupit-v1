package com.webxela.app.coupit

import androidx.compose.runtime.Composable
import com.webxela.app.coupit.navigation.NavDestinations
import com.webxela.app.coupit.navigation.RootNavHost
import com.webxela.app.coupit.theme.AppTheme

@Composable
internal fun App() = AppTheme {

    RootNavHost(startDestination = NavDestinations.Home)
}