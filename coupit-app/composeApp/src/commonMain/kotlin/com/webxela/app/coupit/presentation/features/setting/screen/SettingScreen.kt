package com.webxela.app.coupit.presentation.features.setting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webxela.app.coupit.presentation.component.ScreenPlaceholder
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar

@Composable
fun SettingScreenRoot(modifier: Modifier = Modifier) {
    SettingScreen(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreen(modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            SecondaryTopAppBar(title = "Settings")
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
                icon = Icons.Default.Settings,
                title = "Merchant Settings",
                description = "Will be available soon"
            )
        }
    }
}