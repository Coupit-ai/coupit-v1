package com.webxela.app.coupit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import co.touchlab.kermit.Logger
import dev.theolm.rinku.DeepLink
import dev.theolm.rinku.compose.ext.DeepLinkListener

@Composable
fun HandleDeepLinks(navController: NavController) {

    var deeplink by remember { mutableStateOf<DeepLink?>(null) }
    DeepLinkListener { deeplink = it }

    LaunchedEffect(key1 = deeplink) {
        deeplink?.let { link ->
            handleDeepNavigation(link, navController)
        }
    }
}

private fun handleDeepNavigation(deepLink: DeepLink, navController: NavController) {
    val parameters = deepLink.parameters
    when (deepLink.encodedPath) {
        "/nav/wheel" -> {
            val sessionId = parameters["sessionId"]
            navController.navigate(NavDestinations.Wheel(sessionId)) {
                launchSingleTop = true
                popUpTo(NavDestinations.Home) {
                    inclusive = false
                }
            }
        }

        "/oauth" -> {
            val token = parameters["token"]
            val state = parameters["state"]
            val error = parameters["error"]
            navController.navigate(
                NavDestinations.Auth(token, state, error)
            ) { launchSingleTop = true }
        }

        else -> Logger.e("Invalid deeplink")
    }
}