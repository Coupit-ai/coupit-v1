package com.webxela.app.coupit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.presentation.navigation.NavDestinations
import com.webxela.app.coupit.presentation.navigation.RootNavHost
import com.webxela.app.coupit.presentation.theme.AppTheme
import dev.theolm.rinku.DeepLink
import dev.theolm.rinku.compose.ext.DeepLinkListener

@Composable
internal fun App() = AppTheme {
    var deepLink by remember { mutableStateOf<DeepLink?>(null) }
    DeepLinkListener { deepLink = it }

    RootNavHost(startDestination = NavDestinations.Home)
}