package com.webxela.app.coupit.presentation.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.presentation.features.dashboard.screen.DashboardScreenRoot
import com.webxela.app.coupit.presentation.features.home.component.DrawerFooter
import com.webxela.app.coupit.presentation.features.home.component.DrawerHeader
import com.webxela.app.coupit.presentation.features.home.viewmodel.DrawerItem
import com.webxela.app.coupit.presentation.features.home.viewmodel.DrawerItemType
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiEvent
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiState
import com.webxela.app.coupit.presentation.features.home.viewmodel.drawerItems
import com.webxela.app.coupit.presentation.features.reward.screen.RewardManagerScreenRoot
import com.webxela.app.coupit.presentation.features.setting.screen.SettingScreenRoot
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content

@Composable
fun DrawerHomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    uiEvent: (HomeUiEvent) -> Unit,
    onNavigateToScanner: () -> Unit
) {

    var selectedItem by remember { mutableStateOf(drawerItems[1]) }
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val coroutineScope = rememberCoroutineScope()

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            PermanentDrawerSheet {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DrawerHeader(uiState.isLoading) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                    DrawerNavigation(
                        items = drawerItems,
                        selectedItem = selectedItem,
                        onItemSelect = { selectedItem = it },
                        modifier = Modifier.weight(1f)
                    )
                    DrawerFooter(uiState.merchantResponse)
                }
            }
        }
    ) {
        when (selectedItem.type) {
            DrawerItemType.DASHBOARD -> DashboardScreenRoot { onNavigateToScanner() }
            DrawerItemType.REWARD_MANAGER -> RewardManagerScreenRoot()
            DrawerItemType.SETTING -> SettingScreenRoot()
        }
    }
}

@Composable
private fun DrawerNavigation(
    items: List<DrawerItem>,
    selectedItem: DrawerItem,
    onItemSelect: (DrawerItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            NavigationDrawerItem(
                shape = RoundedCornerShape(12.dp),
                icon = { Icon(item.icon, item.title) },
                label = { Text(item.title) },
                selected = selectedItem == item,
                onClick = { onItemSelect(item) },
                modifier = Modifier.height(50.dp)
            )
        }
    }
}