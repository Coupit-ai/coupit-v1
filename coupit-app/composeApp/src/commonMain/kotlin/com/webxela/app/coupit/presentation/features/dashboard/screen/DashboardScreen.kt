package com.webxela.app.coupit.presentation.features.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webxela.app.coupit.presentation.component.ScreenPlaceholder
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar

@Composable
fun DashboardScreenRoot(
    modifier: Modifier = Modifier,
//    onNavigateToScannerScreen: () -> Unit
) {
    DashboardScreen(
        modifier = modifier,
//        onNavigateToScannerScreen = onNavigateToScannerScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreen(
    modifier: Modifier = Modifier,
//    onNavigateToScannerScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            SecondaryTopAppBar(
                title = "Dashboard",
//                actions = {
//                    IconButton(onClick = onNavigateToScannerScreen) {
//                        Icon(
//                            imageVector = Icons.Default.QrCodeScanner,
//                            contentDescription = "Scanner"
//                        )
//                    }
//                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScreenPlaceholder(
                icon = Icons.Default.SpaceDashboard,
                title = "Dashboard",
                description = "Will be available soon"
            )
        }
    }
}