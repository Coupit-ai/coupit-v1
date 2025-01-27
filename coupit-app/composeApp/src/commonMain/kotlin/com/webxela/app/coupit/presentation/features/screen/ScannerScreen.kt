package com.webxela.app.coupit.presentation.features.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.webxela.app.coupit.presentation.component.SecondaryTopAppBar

@Composable
fun ScannerScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {

    ScannerScreen(
        modifier = modifier,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun ScannerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            SecondaryTopAppBar(
                title = "Scan QR Code",
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text("Scanner will be here.")
        }
    }
}