package com.webxela.app.coupit.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.webxela.app.coupit.presentation.home.screen.HomeScreenRoot
import com.webxela.app.coupit.presentation.wheel.screen.WheelScreenRoot

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    startDestination: NavDestinations,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(tween(500)) { it } },
        exitTransition = { scaleOut(targetScale = .8f) + fadeOut() },
        popEnterTransition = { slideInHorizontally(tween(500)) { -it } },
        popExitTransition = { scaleOut(targetScale = 1.2f) + fadeOut() },
        modifier = modifier.fillMaxSize()
    ) {

        composable<NavDestinations.Home> {
            HomeScreenRoot(
                onNavigateToWheelScreen = { merchantId, transactionId ->
                    navController.navigate(NavDestinations.Wheel(merchantId, transactionId))
                }
            )
        }

        composable<NavDestinations.Wheel> { backstackEntry ->
            val wheel = backstackEntry.toRoute<NavDestinations.Wheel>()
            WheelScreenRoot(
                merchantId = wheel.merchantId,
                transactionId = wheel.transactionId
            )
        }

    }

}