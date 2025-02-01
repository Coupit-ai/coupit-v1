package com.webxela.app.coupit.presentation.features.home.screen

import androidx.compose.foundation.layout.width
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.webxela.app.coupit.core.presentation.navigation.LocalErrorHandler
import com.webxela.app.coupit.presentation.features.dashboard.screen.DashboardScreenRoot
import com.webxela.app.coupit.presentation.features.home.component.MerchantDrawer
import com.webxela.app.coupit.presentation.features.home.viewmodel.DrawerItemType
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiEvent
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeUiState
import com.webxela.app.coupit.presentation.features.home.viewmodel.HomeViewModel
import com.webxela.app.coupit.presentation.features.home.viewmodel.drawerItems
import com.webxela.app.coupit.presentation.features.reward.screen.RewardManagerScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToScanner: () -> Unit
) {

    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = viewModel::onEvent,
        onNavigateToScanner = onNavigateToScanner
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    uiEvent: (HomeUiEvent) -> Unit,
    onNavigateToScanner: () -> Unit
) {

    val errorHandler = LocalErrorHandler.current
    var selectedItem by remember { mutableStateOf(drawerItems[0]) }

    LaunchedEffect(key1 = Unit) {
        uiEvent(HomeUiEvent.CheckIfUserIsLoggedIn)
    }

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            errorHandler.showError(uiState.errorMessage)
        }
    }

    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = modifier.width(300.dp)
            ) {
                MerchantDrawer(
                    drawerItems = drawerItems,
                    selectedItem = selectedItem,
                    onItemSelect = { selectedItem = it }
                )
            }
        }
    ) {
        when (selectedItem.type) {
            DrawerItemType.DASHBOARD -> DashboardScreenRoot { onNavigateToScanner() }
            DrawerItemType.REWARD_MANAGER -> RewardManagerScreenRoot()
            DrawerItemType.SETTING -> Unit
        }
    }

}