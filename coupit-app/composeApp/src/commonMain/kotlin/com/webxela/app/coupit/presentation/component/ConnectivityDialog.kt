package com.webxela.app.coupit.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.jordond.connectivity.Connectivity
import dev.jordond.connectivity.compose.ConnectivityState

@Composable
fun ConnectivityDialog(
    modifier: Modifier = Modifier,
    connectivityState: ConnectivityState,
    retryClicked: () -> Unit
) {

    var showConnectivitySheet by remember { mutableStateOf(false) }

    LaunchedEffect(connectivityState.status) {
        showConnectivitySheet = when (connectivityState.status) {
            is Connectivity.Status.Connected -> false
            else -> true
        }
    }

    if (showConnectivitySheet) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = { /* Prevent dismiss */ },
            icon = {
                Icon(
                    imageVector = Icons.Default.WifiOff,
                    contentDescription = "No Internet Connection",
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = "Connection Lost!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "There seems to be a problem with your internet connection. Please check your connection and try again.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {}) {
                        Text("Try Again")
                    }
                }
            },
            modifier = modifier.widthIn(max = 400.dp)
        )
    }

}