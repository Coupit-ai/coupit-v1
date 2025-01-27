package com.webxela.app.coupit.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.webxela.app.coupit.presentation.features.auth.screen.AuthScreen
import com.webxela.app.coupit.presentation.features.home.screen.HomeScreenRoot
import com.webxela.app.coupit.presentation.features.reward.screen.RewardScreenRoot
import com.webxela.app.coupit.presentation.features.screen.ScannerScreenRoot
import com.webxela.app.coupit.presentation.features.wheel.screen.WheelScreenRoot

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
        exitTransition = { scaleOut(targetScale = .5f) + fadeOut() },
        popEnterTransition = { slideInHorizontally(tween(500)) { -it } },
        popExitTransition = { scaleOut(targetScale = 1.5f) + fadeOut() },
        modifier = modifier.fillMaxSize()
    ) {

        composable<NavDestinations.Home> {
            AuthScreen()
//            HomeScreenRoot(
//                onNavigateToWheelScreen = { sessionId ->
//                    navController.navigate(NavDestinations.Wheel(sessionId))
//                },
//                onNavigateToScannerScreen = {
//                    navController.navigate(NavDestinations.Scanner)
//                }
//            )
        }

        composable<NavDestinations.Wheel> { backstackEntry ->
            val wheel = backstackEntry.toRoute<NavDestinations.Wheel>()
            WheelScreenRoot(
                sessionId = wheel.sessionId,
                navigateToRewardScreen = { spinId ->
                    navController.navigate(NavDestinations.Reward(spinId)) {
                        popUpTo(NavDestinations.Home) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<NavDestinations.Reward> {
            val reward = it.toRoute<NavDestinations.Reward>()
            RewardScreenRoot(
                spinId = reward.spinId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<NavDestinations.Scanner>(
            enterTransition = { slideInVertically(tween(500)) { -it } },
            popExitTransition = { slideOutVertically(tween(500)) { -it } },
        ) {
            ScannerScreenRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }

}