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
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.webxela.app.coupit.presentation.features.auth.screen.RootAuthScreen
import com.webxela.app.coupit.presentation.features.dashboard.screen.DashboardScreenRoot
import com.webxela.app.coupit.presentation.features.home.screen.HomeScreenRoot
import com.webxela.app.coupit.presentation.features.reward.screen.RewardScreenRoot
import com.webxela.app.coupit.presentation.features.scanner.screen.ScannerScreenRoot
import com.webxela.app.coupit.presentation.features.wheel.screen.WheelScreenRoot

@Composable
fun RootNavHost(
    modifier: Modifier = Modifier,
    startDestination: NavDestinations,
) {

    val navController = rememberNavController()
    HandleDeepLinks(navController)

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
            HomeScreenRoot {
                navController.navigate(NavDestinations.Scanner)
            }
        }

        dialog<NavDestinations.Auth>(
            dialogProperties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) { backStackEntry ->
            val auth = backStackEntry.toRoute<NavDestinations.Auth>()
            RootAuthScreen(
                token = auth.token,
                state = auth.state,
                error = auth.error,
                onNavigateToHomeScreen = {
                    navController.navigate(NavDestinations.Home) {
                        launchSingleTop = true
                        popUpTo(NavDestinations.Home) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<NavDestinations.Wheel> { backstackEntry ->
            val wheel = backstackEntry.toRoute<NavDestinations.Wheel>()
            WheelScreenRoot(
                sessionId = wheel.sessionId,
                navigateBack = { navController.popBackStack() },
                navigateToRewardScreen = { spinId ->
                    navController.navigate(NavDestinations.Reward(spinId)) {
                        launchSingleTop = true
                        popUpTo(NavDestinations.Home) {
                            inclusive = false
                        }
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
        ) {
            ScannerScreenRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRewardScreen = { spinId ->
                    navController.navigate(NavDestinations.Reward(spinId)) {
                        launchSingleTop = true
                        popUpTo(NavDestinations.Home) {
                            inclusive = false
                        }
                    }
                }
            )
        }

    }

}