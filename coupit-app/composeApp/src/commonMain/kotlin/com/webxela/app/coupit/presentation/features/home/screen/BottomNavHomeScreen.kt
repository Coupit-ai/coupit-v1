package com.webxela.app.coupit.presentation.features.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.presentation.features.dashboard.screen.DashboardScreenRoot
import com.webxela.app.coupit.presentation.features.home.viewmodel.DrawerItemType
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiEvent
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiState
import com.webxela.app.coupit.presentation.features.home.viewmodel.drawerItems
import com.webxela.app.coupit.presentation.features.reward.screen.RewardManagerScreenRoot
import com.webxela.app.coupit.presentation.features.setting.screen.SettingScreenRoot


@Composable
fun BottomNavHomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    uiEvent: (HomeUiEvent) -> Unit,
    onNavigateToScanner: () -> Unit
) {

    var selectedItem by remember { mutableStateOf(drawerItems[0]) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                drawerItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = item == selectedItem,
                        onClick = { selectedItem = item }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            when (selectedItem.type) {
                DrawerItemType.DASHBOARD -> DashboardScreenRoot { onNavigateToScanner() }
                DrawerItemType.REWARD_MANAGER -> RewardManagerScreenRoot()
                DrawerItemType.SETTING -> SettingScreenRoot()
            }
        }
    }
}