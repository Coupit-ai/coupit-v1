package com.webxela.app.coupit.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations {

    @Serializable
    data object Home: NavDestinations

    @Serializable
    data class Wheel(val sessionId: String?): NavDestinations

    @Serializable
    data object Scanner: NavDestinations

    @Serializable
    data class Reward(val spinId: String): NavDestinations

    @Serializable
    data class Auth(
        val token: String?,
        val state: String?,
        val error: String?
    ): NavDestinations

    @Serializable
    data object Profile: NavDestinations
}