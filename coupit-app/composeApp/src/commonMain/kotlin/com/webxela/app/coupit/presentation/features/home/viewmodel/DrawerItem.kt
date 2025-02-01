package com.webxela.app.coupit.presentation.features.home.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val icon: ImageVector,
    val title: String,
    val type: DrawerItemType
)

enum class DrawerItemType {
    DASHBOARD,
    REWARD_MANAGER,
    SETTING
}

val drawerItems = listOf(
    DrawerItem(
        icon = Icons.Default.SpaceDashboard,
        title = "Dashboard",
        type = DrawerItemType.DASHBOARD,
    ),
    DrawerItem(
        icon = Icons.Default.LocalOffer,
        title = "Manage Rewards",
        type = DrawerItemType.REWARD_MANAGER
    ),
    DrawerItem(
        icon = Icons.Default.Settings,
        title = "Settings",
        type = DrawerItemType.SETTING
    )
)