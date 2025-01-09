package com.webxela.app.coupit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations {

    @Serializable
    data object Home: NavDestinations

    @Serializable
    data class Wheel(
        val merchantId: String,
        val transactionId: String
    ): NavDestinations

    @Serializable
    data object Scanner: NavDestinations

    @Serializable
    data object Reward: NavDestinations
}